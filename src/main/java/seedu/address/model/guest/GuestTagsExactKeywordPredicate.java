package seedu.address.model.guest;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Guest}'s {@code tags} exactly matches a single {@code tag} keyword argument.
 */
public class GuestTagsExactKeywordPredicate implements Predicate<Guest> {
    private final String keyword;

    public GuestTagsExactKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Guest guest) {
        List<Tag> guestTags = new LinkedList<Tag>();
        guestTags.addAll(guest.getTags());
        boolean foundTag = false;

        for (Tag tag : guestTags) {
            if (tag.tagName.equals(keyword)) {
                foundTag = true;
                break;
            }
        }

        if (!foundTag) {
            return false;
        }

        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GuestTagsExactKeywordPredicate // instanceof handles nulls
                && keyword.equals(((GuestTagsExactKeywordPredicate) other).keyword)); // state check
    }
}
