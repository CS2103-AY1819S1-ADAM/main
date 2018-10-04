package seedu.address.model.room;

import java.util.List;

import seedu.address.model.person.Guest;

/**
 * Represents a Double Room in the hotel.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class DoubleRoom extends Room {
    public static final Capacity CAPACITY_DOUBLE_ROOM = new Capacity(2);

    /**
     * All parameters must be non-null.
     * Note: {@code occupant}, {@code expenses}, or {@code reservations} may be empty, but not null.
     */
    public DoubleRoom(RoomNumber roomNumber, List<Guest> occupant, Expenses expenses,
                      Reservations reservations) {
        super(roomNumber, CAPACITY_DOUBLE_ROOM, occupant, expenses, reservations);
    }
}
