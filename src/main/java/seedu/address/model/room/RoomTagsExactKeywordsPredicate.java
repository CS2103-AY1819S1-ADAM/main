package seedu.address.model.room;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that any of a {@code Room}'s {@code tags} exactly matches {@code keyword} argument for tags.
 */
public class RoomTagsExactKeywordsPredicate implements Predicate<Room> {
    private final String keyword;

    public RoomTagsExactKeywordsPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Room room) {
        boolean foundTag = false;

        List<Tag> roomTags = new LinkedList<>();
        roomTags.addAll(room.getTags());

        for (Tag tag : roomTags) {
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
                || (other instanceof RoomTagsExactKeywordsPredicate // instanceof handles nulls
                && keyword.equals(((RoomTagsExactKeywordsPredicate) other).keyword)); // state check
    }
}
