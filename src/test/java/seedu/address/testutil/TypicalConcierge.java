package seedu.address.testutil;

import seedu.address.model.Concierge;
import seedu.address.model.person.UniqueGuestList;
import seedu.address.model.room.UniqueRoomList;

/**
 * A utility class containing a {@code UniqueGuestList} and {@code UniqueRoomList} objects to be used in tests.
 */
public class TypicalConcierge {

    public static final UniqueGuestList GUEST_LIST = TypicalPersons.getTypicalUniqueGuestList();
    public static final UniqueRoomList ROOM_LIST = TypicalRooms.getTypicalUniqueRoomList();

    private TypicalConcierge() {} // prevents instantiation

    /**
     * Returns an {@code Concierge} with all the typical persons.
     */
    public static Concierge getTypicalConcierge() {
        Concierge ab = new Concierge();
        ab.setPersons(GUEST_LIST.asUnmodifiableObservableList());
        ab.setRooms(ROOM_LIST.asUnmodifiableObservableList());
        return ab;
    }
}
