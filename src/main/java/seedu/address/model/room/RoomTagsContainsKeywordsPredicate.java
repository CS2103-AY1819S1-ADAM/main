package seedu.address.model.room;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that any of a {@code Room}'s {@code tags} exactly matches {@code keyword} argument for tags.
 */
public class RoomTagsContainsKeywordsPredicate implements Predicate<Room> {
    private final List<String> keywords;

    public RoomTagsContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Room room) {
        List<Tag> roomTags = new LinkedList<>();
        roomTags.addAll(room.getTags());

        for (String keywordTag : keywords) {
            for (Tag roomTag : roomTags) {
                if (roomTag.tagName.equals(keywordTag)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RoomTagsContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((RoomTagsContainsKeywordsPredicate) other).keywords)); // state check
    }

    @Override
    public int hashCode() {
        return keywords.hashCode();
    }
}
