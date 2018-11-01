package seedu.address.model.room;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that any of a {@code Room}'s {@code tags} exactly matches {@code keyword} argument for tags.
 */
public class RoomTagsExactKeywordsPredicate implements Predicate<Room> {
    private final List<String> keywords;

    public RoomTagsExactKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Room room) {
        boolean foundTag = false;
        List<Tag> roomTags = new LinkedList<>();
        roomTags.addAll(room.getTags());

        for (String keywordTag : keywords) {
            for (Tag roomTag : roomTags) {
                if (roomTag.tagName.equals(keywordTag)) {
                    foundTag = true;
                    break;
                }
            }
        }

        return foundTag;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RoomTagsExactKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((RoomTagsExactKeywordsPredicate) other).keywords)); // state check
    }
}
