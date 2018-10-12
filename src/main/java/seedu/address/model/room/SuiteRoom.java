package seedu.address.model.room;

import java.util.List;

import seedu.address.model.person.Guest;

/**
 * Represents a Suite Room in the hotel.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class SuiteRoom extends Room {
    public static final Capacity CAPACITY_SUITE_ROOM = new Capacity(5);

    /**
     * All parameters must be non-null.
     * Note: {@code occupant}, {@code expenses}, or {@code reservations} may be empty, but not null.
     */
    public SuiteRoom(RoomNumber roomNumber, List<Guest> occupant, Expenses expenses,
                     Reservations reservations) {
        super(roomNumber, CAPACITY_SUITE_ROOM, occupant, expenses, reservations);
    }
}
