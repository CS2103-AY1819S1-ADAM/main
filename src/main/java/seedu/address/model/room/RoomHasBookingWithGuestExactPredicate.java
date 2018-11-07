package seedu.address.model.room;

import java.util.List;
import java.util.SortedSet;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.guest.Name;
import seedu.address.model.room.booking.Booking;

/**
 * Tests that a {@code Room}'s {@code BookingPeriod} matches the range of the specified {@code BookingPeriod} argument.
 */
public class RoomHasBookingWithGuestExactPredicate implements Predicate<Room> {
    private final List<Name> names;

    public RoomHasBookingWithGuestExactPredicate(List<Name> names) {
        this.names = names;
    }

    @Override
    public boolean test(Room room) {
        SortedSet<Booking> allBookings = room.bookings.getSortedBookingsSet();

        for (Booking booking : allBookings) {
            if (names.stream()
                    .anyMatch(name -> StringUtil.containsWordIgnoreCase(
                            booking.getGuest().getName().fullName, name.fullName))) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RoomHasBookingWithGuestExactPredicate // instanceof handles nulls
                && names.equals(((RoomHasBookingWithGuestExactPredicate) other).names)); // state check
    }

    @Override
    public int hashCode() {
        return names.hashCode();
    }
}
