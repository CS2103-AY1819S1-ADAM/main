package seedu.address.model.room.booking;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.model.room.booking.exceptions.BookingNotFoundException;
import seedu.address.model.room.booking.exceptions.NoBookingException;
import seedu.address.model.room.booking.exceptions.OverlappingBookingException;

/**
 * A sorted set of Bookings that maintains non-overlapping property between its elements and does not allow nulls.
 * A Booking is considered non-overlapping by comparing using
 * {@code Booking#canAcceptBooking(Booking)}. As such, adding and updating of
 * Bookings uses Booking#canAcceptBooking(Booking) to ensure that the
 * Booking being added or updated does not overlap any existing ones in Bookings.
 * However, the removal of a Booking uses Booking#equals(Object) so
 * as to ensure that the Booking with exactly the same fields will be removed.
 * *
 * Supports a minimal set of set operations.
 * Guarantees immutability
 *
 * @see Booking#isOverlapping(Booking)
 */
public class Bookings {

    public final SortedSet<Booking> sortedBookingsSet;

    /**
     * Constructor for empty bookings set
     */
    public Bookings() {
        this.sortedBookingsSet = new TreeSet<>();
    }

    /**
     * Constructor for creating a copy of a bookings
     */
    public Bookings(SortedSet<Booking> sortedBookingsSet) {
        requireAllNonNull(sortedBookingsSet);
        if (bookingsAreOverlapping(sortedBookingsSet)) {
            throw new OverlappingBookingException();
        }
        this.sortedBookingsSet = sortedBookingsSet;
    }

    //=========== Getters =============================================================

    public SortedSet<Booking> getSortedBookingsSet() {
        return sortedBookingsSet;
    }

    /**
     * Gets the first booking in the set
     */
    public Booking getFirstBooking() {
        if (isEmpty()) {
            throw new NoBookingException();
        }
        return sortedBookingsSet.first();
    }

    //=========== Operations =============================================================

    /**
     * Adds a Booking to a copy of the set.
     * The Booking must not already exist in the set.
     */
    public Bookings add(Booking toAdd) {
        requireNonNull(toAdd);
        if (!canAcceptBooking(toAdd)) {
            throw new OverlappingBookingException();
        }
        SortedSet<Booking> editedBookings = new TreeSet<>(this.sortedBookingsSet);
        editedBookings.add(toAdd);
        return new Bookings(editedBookings);
    }

    /**
     * Removes the equivalent Booking from a copy of the set.
     * The Booking must exist in the set.
     */
    public Bookings remove(Booking toRemove) {
        requireNonNull(toRemove);
        if (!sortedBookingsSet.contains(toRemove)) {
            throw new BookingNotFoundException();
        }
        SortedSet<Booking> editedBookings = new TreeSet<>(this.sortedBookingsSet);
        editedBookings.remove(toRemove);
        return new Bookings(editedBookings);
    }

    /**
     * Replaces the Booking {@code target} in a copy of the set with {@code editedBooking}.
     * {@code target} must exist in the set.
     */
    public Bookings updateBooking(Booking target, Booking editedBooking) {
        requireAllNonNull(target, editedBooking);

        if (!sortedBookingsSet.contains(target)) {
            throw new BookingNotFoundException();
        }

        if (!target.isSameBooking(editedBooking) && !canAcceptIfReplaceBooking(target, editedBooking)) {
            throw new OverlappingBookingException();
        }

        SortedSet<Booking> editedBookings = new TreeSet<>(this.sortedBookingsSet);
        editedBookings.remove(target);
        editedBookings.add(editedBooking);
        return new Bookings(editedBookings);
    }

    //=========== Boolean checkers =============================================================

    /**
     * Returns true if there are no bookings
     */
    public boolean isEmpty() {
        return sortedBookingsSet.isEmpty();
    }

    /**
     * Returns true if the given booking overlaps with any existing booking in the set
     */
    public boolean canAcceptBooking(Booking toCheck) {
        requireNonNull(toCheck);
        return sortedBookingsSet.stream().noneMatch(toCheck::isOverlapping);
    }

    /**
     * Returns true if the given booking overlaps with any existing booking in the set, excluding the one it replaces
     */
    public boolean canAcceptIfReplaceBooking(Booking toReplace, Booking toCheck) {
        requireAllNonNull(toReplace, toCheck);
        return sortedBookingsSet.stream().noneMatch(
            booking -> !booking.equals(toReplace) && booking.isOverlapping(toCheck));
    }

    /**
     * Returns true if {@code Bookings} contains at least one overlapping Booking.
     */
    public static boolean bookingsAreOverlapping(Set<Booking> bookings) {
        return bookings.stream().anyMatch(b1 ->
            bookings.stream().anyMatch(b2 -> !b1.equals(b2) && b1.isOverlapping(b2)));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Bookings // instanceof handles nulls
                        && sortedBookingsSet.equals(((Bookings) other).sortedBookingsSet));
    }

    @Override
    public int hashCode() {
        return sortedBookingsSet.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        sortedBookingsSet.forEach(builder::append);
        return builder.toString();
    }
}
