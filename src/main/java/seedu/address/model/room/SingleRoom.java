package seedu.address.model.room;

import java.util.List;

import seedu.address.model.person.Guest;

/**
 * Represents a Single Room in the hotel.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class SingleRoom extends Room {
    public static final Capacity CAPACITY_SINGLE_ROOM = new Capacity(1);

    /**
     * All parameters must be non-null.
     * Note: {@code occupant}, {@code expenses}, or {@code reservations} may be empty, but not null.
     */
    public SingleRoom(RoomNumber roomNumber, List<Guest> occupant, Expenses expenses,
                      Reservations reservations) {
        super(roomNumber, CAPACITY_SINGLE_ROOM, occupant, expenses, reservations);
    }
}
