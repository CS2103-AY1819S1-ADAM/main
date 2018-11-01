package seedu.address.model.guest;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Guest}'s {@code tags} exactly matches a single {@code tag} keyword argument.
 */
public class GuestTagsContainsKeywordsPredicate implements Predicate<Guest> {
    private final List<String> keywords;

    public GuestTagsContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Guest guest) {
        List<Tag> guestTags = new LinkedList<>(guest.getTags());

        for (String keywordTag : keywords) {
            for (Tag guestTag : guestTags) {
                if (guestTag.tagName.equals(keywordTag)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GuestTagsContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((GuestTagsContainsKeywordsPredicate) other).keywords)); // state check

    }

    @Override
    public int hashCode() {
        return keywords.hashCode();
    }
}
