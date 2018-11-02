package seedu.address.testutil;

import java.util.List;

import seedu.address.model.room.Room;
import seedu.address.model.room.UniqueRoomList;

/**
 * A utility class containing a list of {@code Room} objects to be used in tests.
 * Note: ALL rooms are now gotten directly from the TypicalConcierge. The reason for this is Concierge needs to
 * maintain synchronization between the room list and the checked-in guest list, which means any checked-in bookings
 * must be executed via the Concierge. See TypicalConcierge for more info.
 * TODO Incorporate expenses into rooms once WEI ZHENG has implemented it
 */
public class TypicalRooms {

    /**
     * Returns a {@code UniqueRoomList} with all rooms.
     * Note: All rooms do not have bookings or expenses.
     */
    public static UniqueRoomList getTypicalUniqueRoomListClean() {
        return new UniqueRoomList();
    }

    /**
     * Get the typical concierge's room list with preset bookings
     */
    public static List<Room> getTypicalUniqueRoomList() {
        return TypicalConcierge.getTypicalConcierge().getRoomList();
    }

}
