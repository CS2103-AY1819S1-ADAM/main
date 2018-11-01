package seedu.address.testutil;

import seedu.address.model.Concierge;
import seedu.address.model.guest.UniqueGuestList;
import seedu.address.model.room.UniqueRoomList;

/**
 * A utility class containing a {@code UniqueGuestList} and {@code UniqueRoomList} objects to be used in tests.
 */
public class TypicalConcierge {

    public static final UniqueGuestList GUEST_LIST = TypicalGuests.getTypicalUniqueGuestList();
    public static final UniqueRoomList ROOM_LIST = TypicalRooms.getTypicalUniqueRoomListClean();

    private TypicalConcierge() {} // prevents instantiation

    /**
     * Returns an {@code Concierge} with all the typical guests and rooms WITHOUT bookings.
     * Use @Before in tests to initialize bookings and expenses. However, if you use @Before, make sure to
     * do `model = new Model(model.getConcierge(), new UserPrefs())` at the end of the @Before block. This is to
     * ensure the model's versionedConcierge has an empty initial state list for all the tests to work.
     */
    public static Concierge getTypicalConciergeClean() {
        Concierge ab = new Concierge();
        ab.setGuests(GUEST_LIST.asUnmodifiableObservableList());
        ab.setRooms(ROOM_LIST.asUnmodifiableObservableList());
        return ab;
    }

    /**
     * Returns an {@code Concierge} with all the typical guests and rooms WITH preset bookings.
     * Note: We add the bookings and perform check-ins for those rooms that are supposed to have checked-in bookings
     * for testing here, because address book maintains a list of checked-in guests and the checkIn command is used to
     * both set the room in the UniqueRoomList AND add the guest to the checked-in guest list. This will ensure that
     * the checked-in guest list will always be in sync with the rooms' bookings.<br/>
     * <br/>
     * Room 001: last week - Yesterday<br/>
     * Room 002: Last week - Yesterday (checked-in)<br/>
     * Room 010: Yesterday - Today<br/>
     * Room 011: Today - Tomorrow<br/>
     * Room 012: Today - Tomorrow (checked-in)<br/>
     * Room 020: Today - Next week<br/>
     * Room 021: Tomorrow - Next week<br/>
     * Room 022: Last week - Yesterday, Yesterday - Today, Today - Tomorrow, Tomorrow - Next week<br/>
     * Room 030: Last week - Yesterday, Yesterday - Today, Today - Tomorrow (checked-in), Tomorrow - Next week<br/>
     * Room 031: No bookings
     */
    public static Concierge getTypicalConcierge() {
        Concierge ab = getTypicalConciergeClean();

        // Rooms with single bookings
        ab.addBooking(TypicalRoomNumbers.ROOM_NUMBER_001, TypicalBookings.LASTWEEK_YESTERDAY);
        ab.addRoomTags(TypicalRoomNumbers.ROOM_NUMBER_001, TypicalTags.ROOM_TAG_MAINTENANCE);

        /*  Special case of adding checked-in expired booking. Note Concierge does not allow checking-in of expired
        bookings, which is why a pre-checked-in booking is added and the checked-in guest is manually added via
        addCheckedInGuest. Contrast with the standard way below. */
        ab.addBooking(TypicalRoomNumbers.ROOM_NUMBER_002, TypicalBookings.LASTWEEK_YESTERDAY_CHECKED_IN);
        ab.addCheckedInGuest(TypicalBookings.LASTWEEK_YESTERDAY_CHECKED_IN.getGuest());
        ab.addRoomTags(TypicalRoomNumbers.ROOM_NUMBER_002, TypicalTags.ROOM_TAG_MAINTENANCE);

        ab.addBooking(TypicalRoomNumbers.ROOM_NUMBER_010, TypicalBookings.YESTERDAY_TODAY);
        ab.addBooking(TypicalRoomNumbers.ROOM_NUMBER_011, TypicalBookings.TODAY_TOMORROW);

        /* This is the standard way of checking in a room with a check-in-able booking. */
        ab.addBooking(TypicalRoomNumbers.ROOM_NUMBER_012, TypicalBookings.TODAY_TOMORROW);
        ab.checkInRoom(TypicalRoomNumbers.ROOM_NUMBER_012);

        ab.addBooking(TypicalRoomNumbers.ROOM_NUMBER_020, TypicalBookings.TODAY_NEXTWEEK);
        ab.addBooking(TypicalRoomNumbers.ROOM_NUMBER_021, TypicalBookings.TOMORROW_NEXTWEEK);

        // Rooms with multiple bookings
        TypicalBookings.getMultipleBookingsSet()
                .forEach(booking -> ab.addBooking(TypicalRoomNumbers.ROOM_NUMBER_022, booking));
        ab.addCheckedInGuest(TypicalBookings.LASTWEEK_YESTERDAY_CHECKED_IN.getGuest());

        // Room 031 will have no bookings. This comment is put here to inform you in case you come across Room 031 in
        // any of the tests.

        return ab;
    }
}
