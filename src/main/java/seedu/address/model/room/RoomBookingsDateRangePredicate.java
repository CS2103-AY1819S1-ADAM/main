package seedu.address.model.room;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.room.booking.Booking;
import seedu.address.model.room.booking.BookingPeriod;

/**
 * Tests that a {@code Room}'s {@code BookingPeriod} matches the range of the specified {@code BookingPeriod} argument.
 */
public class RoomBookingsDateRangePredicate implements Predicate<Room> {
    private final BookingPeriod bookingPeriod;

    public RoomBookingsDateRangePredicate(BookingPeriod bookingPeriod) {
        this.bookingPeriod = bookingPeriod;
    }

    @Override
    public boolean test(Room room) {
        Set<Booking> setRoomBooking = room.getBookings().getSortedBookingsSet();

        for (Booking booking : setRoomBooking) {
            if (!booking.getBookingPeriod().isOverlapping(bookingPeriod)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RoomBookingsDateRangePredicate // instanceof handles nulls
                && bookingPeriod.equals(((RoomBookingsDateRangePredicate) other).bookingPeriod)); // state check
    }

    @Override
    public int hashCode() {
        return bookingPeriod.hashCode();
    }
}
