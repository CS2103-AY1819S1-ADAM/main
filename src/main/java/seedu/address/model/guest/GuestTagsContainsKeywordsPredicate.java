package seedu.address.model.guest;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Guest}'s {@code tags} exactly matches a single {@code tag} keyword argument.
 */
public class GuestTagsExactKeywordPredicate implements Predicate<Guest> {
    private final Set<Tag> keywords;

    public GuestTagsExactKeywordPredicate(Set<Tag> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Guest guest) {
        Set<Tag> guestTags = new HashSet<Tag>();
        guestTags.addAll(guest.getTags());

        boolean foundTag = false;

        for(Tag keywordTag : keywords){
            for (Tag guestTag : guestTags) {
                if (guestTag.tagName.equals(keywordTag)) {
                    foundTag = true;
                    break;
                }
            }
        }

        return !foundTag;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GuestTagsExactKeywordPredicate // instanceof handles nulls
                && keywords.equals(((GuestTagsExactKeywordPredicate) other).keywords)); // state check
    }
}
