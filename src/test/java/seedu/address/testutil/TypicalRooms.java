package seedu.address.testutil;

import java.util.Arrays;
import java.util.List;

import seedu.address.model.room.DoubleRoom;
import seedu.address.model.room.Room;
import seedu.address.model.room.SingleRoom;
import seedu.address.model.room.SuiteRoom;
import seedu.address.model.room.UniqueRoomList;

/**
 * A utility class containing a list of {@code Room} objects to be used in tests.
 */
public class TypicalRooms {

    private static final int CAPACITY_SINGLE_ROOM = SingleRoom.getCapacityValue();
    private static final int CAPACITY_DOUBLE_ROOM = DoubleRoom.getCapacityValue();
    private static final int CAPACITY_SUITE_ROOM = SuiteRoom.getCapacityValue();

    public static final Room SINGLE_001 = new RoomBuilder()
        .withRoomNumber(TypicalRoomNumbers.ROOM_NUMBER_001.toString()).withCapacity(CAPACITY_SINGLE_ROOM)
        .build();

    public static final Room DOUBLE_002 = new RoomBuilder()
        .withRoomNumber(TypicalRoomNumbers.ROOM_NUMBER_002.toString()).withCapacity(CAPACITY_DOUBLE_ROOM)
        .build();

    public static final Room SUITE_010 = new RoomBuilder()
        .withRoomNumber(TypicalRoomNumbers.ROOM_NUMBER_010.toString()).withCapacity(CAPACITY_SUITE_ROOM)
        .build();

    public static final Room SINGLE_011 = new RoomBuilder()
        .withRoomNumber(TypicalRoomNumbers.ROOM_NUMBER_011.toString()).withCapacity(CAPACITY_SINGLE_ROOM)
        .build();

    public static final Room DOUBLE_012 = new RoomBuilder()
        .withRoomNumber(TypicalRoomNumbers.ROOM_NUMBER_012.toString()).withCapacity(CAPACITY_DOUBLE_ROOM)
        .build();

    public static final Room SUITE_020 = new RoomBuilder()
        .withRoomNumber(TypicalRoomNumbers.ROOM_NUMBER_020.toString()).withCapacity(CAPACITY_SUITE_ROOM)
        .build();

    public static List<Room> getTypicalRooms() {
        return Arrays.asList(SINGLE_001, DOUBLE_002, SUITE_010, SINGLE_011, DOUBLE_012, SUITE_020);
    }

    /**
     * Returns a {@code UniqueRoomList} with all the typical rooms.
     * Note: All rooms do not have bookings or expenses. Use @Before in tests to initialize bookings and expenses.
     */
    public static UniqueRoomList getTypicalUniqueRoomList() {
        UniqueRoomList uniqueRoomList = new UniqueRoomList();
        uniqueRoomList.setRooms(getTypicalRooms());
        return uniqueRoomList;
    }
}
