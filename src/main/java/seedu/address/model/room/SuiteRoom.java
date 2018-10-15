package seedu.address.model.room;

import java.util.Set;

import seedu.address.model.expenses.Expenses;
import seedu.address.model.room.booking.Bookings;
import seedu.address.model.tag.Tag;

/**
 * Represents a Suite Room in the hotel.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class SuiteRoom extends Room {
    public static final Capacity CAPACITY_SUITE_ROOM = new Capacity(5);

    /**
     * All parameters must be non-null.
     */
    public SuiteRoom(RoomNumber roomNumber) {
        super(roomNumber, CAPACITY_SUITE_ROOM);
    }

    /**
     * All parameters must be non-null.
     */
    public SuiteRoom(RoomNumber roomNumber, Expenses expenses, Bookings bookings, Set<Tag> tags) {
        super(roomNumber, CAPACITY_SUITE_ROOM, expenses, bookings, tags);
    }

    public SuiteRoom(SuiteRoom suiteRoom) {
        super(suiteRoom);
    }

    public static int getCapacityValue() {
        return CAPACITY_SUITE_ROOM.getValue();
    }

    @Override
    public SuiteRoom cloneRoom() {
        return new SuiteRoom(this);
    }
}
