package seedu.address.testutil;

import seedu.address.model.Concierge;
import seedu.address.model.guest.UniqueGuestList;
import seedu.address.model.room.UniqueRoomList;

/**
 * A utility class containing a {@code UniqueGuestList} and {@code UniqueRoomList} objects to be used in tests.
 */
public class TypicalConcierge {

    public static final UniqueGuestList GUEST_LIST = TypicalGuests.getTypicalUniqueGuestList();
    public static final UniqueRoomList ROOM_LIST = TypicalRooms.getTypicalUniqueRoomList();
    public static final UniqueRoomList ROOM_LIST_WITH_BOOKINGS = TypicalRooms.getTypicalUniqueRoomListWithBookings();

    private TypicalConcierge() {} // prevents instantiation

    /**
     * Returns an {@code Concierge} with all the typical guests and rooms WITHOUT bookings.
     */
    public static Concierge getTypicalConcierge() {
        Concierge ab = new Concierge();
        ab.setGuests(GUEST_LIST.asUnmodifiableObservableList());
        ab.setRooms(ROOM_LIST.asUnmodifiableObservableList());
        return ab;
    }

    /**
     * Returns an {@code Concierge} with all the typical guests and rooms WITH preset bookings.
     */
    public static Concierge getTypicalConciergeWithRoomBookings() {
        Concierge ab = new Concierge();
        ab.setGuests(GUEST_LIST.asUnmodifiableObservableList());
        ab.setRooms(ROOM_LIST_WITH_BOOKINGS.asUnmodifiableObservableList());
        return ab;
    }
}
