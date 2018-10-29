package seedu.address.testutil;

import seedu.address.model.room.Capacity;
import seedu.address.model.room.Room;
import seedu.address.model.room.UniqueRoomList;

/**
 * A utility class containing a list of {@code Room} objects to be used in tests.
 */
public class TypicalRooms {

    public static final Room SINGLE_001 = new RoomBuilder()
        .withRoomNumber(TypicalRoomNumbers.ROOM_NUMBER_001.toString())
        .withCapacity(Capacity.SINGLE)
        .withBookings(TypicalBookings.getTypicalBookingsLastWeekYesterday())
        .build();

    public static final Room DOUBLE_002 = new RoomBuilder()
        .withRoomNumber(TypicalRoomNumbers.ROOM_NUMBER_002.toString())
        .withCapacity(Capacity.DOUBLE)
        .withBookings(TypicalBookings.getTypicalBookingsLastWeekYesterdayCheckedIn())
        .build();

    public static final Room SUITE_010 = new RoomBuilder()
        .withRoomNumber(TypicalRoomNumbers.ROOM_NUMBER_010.toString())
        .withCapacity(Capacity.SUITE)
        .withBookings(TypicalBookings.getTypicalBookingsYesterdayToday())
        .build();

    public static final Room SINGLE_011 = new RoomBuilder()
        .withRoomNumber(TypicalRoomNumbers.ROOM_NUMBER_011.toString())
        .withCapacity(Capacity.SINGLE)
        .withBookings(TypicalBookings.getTypicalBookingsTodayTomorrow())
        .build();

    public static final Room DOUBLE_012 = new RoomBuilder()
        .withRoomNumber(TypicalRoomNumbers.ROOM_NUMBER_012.toString())
        .withCapacity(Capacity.DOUBLE)
        .withBookings(TypicalBookings.getTypicalBookingsTodayTomorrowCheckedIn())
        .build();

    public static final Room SUITE_020 = new RoomBuilder()
        .withRoomNumber(TypicalRoomNumbers.ROOM_NUMBER_020.toString())
        .withCapacity(Capacity.SUITE)
        .withBookings(TypicalBookings.getTypicalBookingsTodayNextWeek())
        .build();

    public static final Room SINGLE_021 = new RoomBuilder()
        .withRoomNumber(TypicalRoomNumbers.ROOM_NUMBER_021.toString())
        .withCapacity(Capacity.SINGLE)
        .withBookings(TypicalBookings.getTypicalBookingsTomorrowNextWeek())
        .build();

    public static final Room DOUBLE_022 = new RoomBuilder()
        .withRoomNumber(TypicalRoomNumbers.ROOM_NUMBER_022.toString())
        .withCapacity(Capacity.DOUBLE)
        .withBookings(TypicalBookings.getMultipleBookingsSet1())
        .build();

    public static final Room SUITE_030 = new RoomBuilder()
        .withRoomNumber(TypicalRoomNumbers.ROOM_NUMBER_030.toString())
        .withCapacity(Capacity.SUITE)
        .withBookings(TypicalBookings.getMultipleBookingsSet2())
        .build();

    public static final Room SINGLE_031 = new RoomBuilder()
        .withRoomNumber(TypicalRoomNumbers.ROOM_NUMBER_031.toString())
        .withCapacity(Capacity.SINGLE)
        .build();

    /**
     * Returns a {@code UniqueRoomList} with all rooms.
     * Note: All rooms do not have bookings or expenses.
     * Use @Before in tests to initialize bookings and expenses. However, if you use @Before, make sure to
     * do `model = new Model(model.getConcierge(), new UserPrefs())` at the end of the @Before block. This is to
     * ensure the model's versionedConcierge has an empty initial state list for all the tests to work.
     */
    public static UniqueRoomList getTypicalUniqueRoomList() {
        return new UniqueRoomList();
    }

    /**
     * Returns a {@code UniqueRoomList} with rooms that are preset with bookings:
     * Room 001: last week - Yesterday
     * Room 002: Last week - Yesterday (checked-in)
     * Room 010: Yesterday - Today
     * Room 011: Today - Tomorrow
     * Room 012: Today - Tomorrow (checked-in)
     * Room 020: Today - Next week
     * Room 021: Tomorrow - Next week
     * Room 022: Last week - Yesterday, Yesterday - Today, Today - Tomorrow, Tomorrow - Next week
     * Room 030: Last week - Yesterday, Yesterday - Today, Today - Tomorrow (checked-in), Tomorrow - Next week
     * Room 031: No bookings
     */
    public static UniqueRoomList getTypicalUniqueRoomListWithBookings() {
        UniqueRoomList uniqueRoomList = new UniqueRoomList();
        uniqueRoomList.setRoom(uniqueRoomList.getRoom(TypicalRoomNumbers.ROOM_NUMBER_001), SINGLE_001);
        uniqueRoomList.setRoom(uniqueRoomList.getRoom(TypicalRoomNumbers.ROOM_NUMBER_002), DOUBLE_002);
        uniqueRoomList.setRoom(uniqueRoomList.getRoom(TypicalRoomNumbers.ROOM_NUMBER_010), SUITE_010);
        uniqueRoomList.setRoom(uniqueRoomList.getRoom(TypicalRoomNumbers.ROOM_NUMBER_011), SINGLE_011);
        uniqueRoomList.setRoom(uniqueRoomList.getRoom(TypicalRoomNumbers.ROOM_NUMBER_012), DOUBLE_012);
        uniqueRoomList.setRoom(uniqueRoomList.getRoom(TypicalRoomNumbers.ROOM_NUMBER_020), SUITE_020);
        uniqueRoomList.setRoom(uniqueRoomList.getRoom(TypicalRoomNumbers.ROOM_NUMBER_021), SINGLE_021);
        uniqueRoomList.setRoom(uniqueRoomList.getRoom(TypicalRoomNumbers.ROOM_NUMBER_022), DOUBLE_022);
        uniqueRoomList.setRoom(uniqueRoomList.getRoom(TypicalRoomNumbers.ROOM_NUMBER_030), SUITE_030);
        uniqueRoomList.setRoom(uniqueRoomList.getRoom(TypicalRoomNumbers.ROOM_NUMBER_031), SINGLE_031);
        return uniqueRoomList;
    }
}
