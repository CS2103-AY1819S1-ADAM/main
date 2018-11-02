package seedu.address.model.room;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Tests that a {@code Room} currently has valid, or has no {@code Bookings}.
 */
public class RoomHasBookingsExactPredicate implements Predicate<Room> {
    private final boolean hasBookings;

    public RoomHasBookingsExactPredicate(boolean hasBookings) {
        this.hasBookings = hasBookings;
    }

    @Override
    public boolean test(Room room) {
        return room.getBookings().getSortedBookingsSet().isEmpty() == !hasBookings;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RoomHasBookingsExactPredicate // instanceof handles nulls
                && hasBookings == ((RoomHasBookingsExactPredicate) other).hasBookings); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(hasBookings);
    }
}
