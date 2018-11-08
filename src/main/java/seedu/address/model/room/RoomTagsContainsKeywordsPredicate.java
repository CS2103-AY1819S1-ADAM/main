package seedu.address.model.room;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that any of a {@code Room}'s {@code tags} exactly matches {@code keyword} argument for tags.
 */
public class RoomTagsContainsKeywordsPredicate implements Predicate<Room> {
    private final List<Tag> tags;

    public RoomTagsContainsKeywordsPredicate(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(Room room) {
        List<Tag> roomTags = new LinkedList<>(room.getTags());

        for (Tag tag : tags) {
            for (Tag roomTag : roomTags) {
                if (roomTag.tagName.equalsIgnoreCase(tag.tagName)) {
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
                && tags.equals(((RoomTagsContainsKeywordsPredicate) other).tags)); // state check
    }

    @Override
    public int hashCode() {
        return tags.hashCode();
    }
}
