package seedu.address.model.guest;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Guest}'s {@code tags} exactly matches a single {@code tag} keyword argument.
 */
public class GuestTagsContainsKeywordsPredicate implements Predicate<Guest> {
    private final List<Tag> tags;

    public GuestTagsContainsKeywordsPredicate(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(Guest guest) {
        List<Tag> guestTags = new LinkedList<>(guest.getTags());

        for (Tag tag : tags) {
            for (Tag guestTag : guestTags) {
                if (guestTag.tagName.equalsIgnoreCase(tag.tagName)) {
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
                && tags.equals(((GuestTagsContainsKeywordsPredicate) other).tags)); // state check
    }

    @Override
    public int hashCode() {
        return tags.hashCode();
    }
}
