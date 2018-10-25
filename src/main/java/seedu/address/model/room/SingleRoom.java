package seedu.address.model.room;

import java.util.Set;

import seedu.address.model.expenses.Expenses;
import seedu.address.model.room.booking.Bookings;
import seedu.address.model.tag.Tag;

/**
 * Represents a Single Room in the hotel.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class SingleRoom extends Room {
    public static final Capacity CAPACITY_SINGLE_ROOM = new Capacity(1);

    public SingleRoom(RoomNumber roomNumber) {
        super(roomNumber, CAPACITY_SINGLE_ROOM);
    }

    /**
     * All parameters must be non-null.
     */
    public SingleRoom(RoomNumber roomNumber, Expenses expenses, Bookings bookings, Set<Tag> tags) {
        super(roomNumber, CAPACITY_SINGLE_ROOM, expenses, bookings, tags);
    }

    public static int getCapacityValue() {
        return CAPACITY_SINGLE_ROOM.getValue();
    }

    @Override
    SingleRoom makeRoom(RoomNumber roomNumber, Expenses expenses, Bookings bookings, Set<Tag> tags) {
        return new SingleRoom(roomNumber, expenses, bookings, tags);
    }
}
