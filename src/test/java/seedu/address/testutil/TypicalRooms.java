package seedu.address.testutil;

import seedu.address.model.room.DoubleRoom;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomNumber;
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

    public static final Room SINGLE_023 = new RoomBuilder()
        .withRoomNumber(TypicalRoomNumbers.ROOM_NUMBER_023.toString()).withCapacity(CAPACITY_SINGLE_ROOM)
        .build();

    public static final Room DOUBLE_024 = new RoomBuilder()
        .withRoomNumber(TypicalRoomNumbers.ROOM_NUMBER_024.toString()).withCapacity(CAPACITY_DOUBLE_ROOM)
        .build();

    public static final Room SUITE_099 = new RoomBuilder()
        .withRoomNumber(TypicalRoomNumbers.ROOM_NUMBER_099.toString()).withCapacity(CAPACITY_SUITE_ROOM)
        .build();

    /**
     * Returns a {@code UniqueRoomList} with all the typical rooms.
     * Note: All rooms do not have bookings or expenses. Use @Before in tests to initialize bookings and expenses.
     */
    public static UniqueRoomList getTypicalUniqueRoomList() {
        return new UniqueRoomList(RoomNumber.MAX_ROOM_NUMBER);
    }
}
