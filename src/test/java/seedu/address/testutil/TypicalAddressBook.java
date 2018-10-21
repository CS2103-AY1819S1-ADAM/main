package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.guest.UniqueGuestList;
import seedu.address.model.room.UniqueRoomList;

/**
 * A utility class containing a {@code UniqueGuestList} and {@code UniqueRoomList} objects to be used in tests.
 */
public class TypicalAddressBook {

    public static final UniqueGuestList GUEST_LIST = TypicalGuests.getTypicalUniqueGuestList();
    public static final UniqueRoomList ROOM_LIST = TypicalRooms.getTypicalUniqueRoomList();

    private TypicalAddressBook() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical guests.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        ab.setGuests(GUEST_LIST.asUnmodifiableObservableList());
        ab.setRooms(ROOM_LIST.asUnmodifiableObservableList());
        return ab;
    }
}
