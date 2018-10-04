package seedu.address.testutil;

import java.util.List;

import seedu.address.model.room.Room;
import seedu.address.model.room.UniqueRoomList;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class containing a list of {@code Room} objects to be used in tests.
 */
public class TypicalRooms {
    /**
     * Returns an {@code UniqueRoomList} with all the typical rooms.
     */
    public static UniqueRoomList getTypicalUniqueRoomList() {
        UniqueRoomList rooms = new UniqueRoomList();
        rooms.setRooms(SampleDataUtil.getSampleRooms());
        return rooms;
    }

    public static List<Room> getTypicalRooms() {
        return SampleDataUtil.getSampleRooms();
    }
}
