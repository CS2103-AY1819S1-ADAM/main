package seedu.address.model.room;

import java.util.Set;

import seedu.address.model.expenses.Expenses;
import seedu.address.model.room.booking.Bookings;
import seedu.address.model.tag.Tag;

/**
 * Represents a Double Room in the hotel.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class DoubleRoom extends Room {
    public static final Capacity CAPACITY_DOUBLE_ROOM = new Capacity(2);

    public DoubleRoom(RoomNumber roomNumber) {
        super(roomNumber, CAPACITY_DOUBLE_ROOM);
    }

    /**
     * All parameters must be non-null.
     */
    public DoubleRoom(RoomNumber roomNumber, Expenses expenses, Bookings bookings, Set<Tag> tags) {
        super(roomNumber, CAPACITY_DOUBLE_ROOM, expenses, bookings, tags);
    }

    public static int getCapacityValue() {
        return CAPACITY_DOUBLE_ROOM.getValue();
    }

    @Override
    DoubleRoom makeRoom(RoomNumber roomNumber, Expenses expenses, Bookings bookings, Set<Tag> tags) {
        return new DoubleRoom(roomNumber, expenses, bookings, tags);
    }
}
