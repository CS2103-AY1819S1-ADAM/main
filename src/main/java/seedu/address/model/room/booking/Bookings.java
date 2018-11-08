package seedu.address.model.room.booking;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;

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

    private final SortedSet<Booking> sortedBookingsSet;

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
        return Collections.unmodifiableSortedSet(sortedBookingsSet);
    }

    /**
     * Gets the first booking in the set
     */
    public Booking getFirstBooking() {
        if (sortedBookingsSet.isEmpty()) {
            throw new NoBookingException();
        }
        return sortedBookingsSet.first();
    }

    /**
     * Returns an {@code Optional} of the first active booking of this room
     * Note 1: We return an optional here and not throw exception because only GUI elements (RoomCard and
     * RoomDetailedCard) use this method. It makes it difficult to read the GUI code if exceptions are handled there.
     * Note 2: There can be more than one active booking at a time.
     * For example, Booking A ends on 01/11/18 and Booking B starts on 01/11/18. This is allowed, to simulate a
     * turnover of rooms in the same day.
     */
    public Optional<Booking> getFirstActiveBooking() {
        return sortedBookingsSet.stream().filter(Booking::isActive).findFirst();
    }

    /**
     * Return the first booking that matches the given predicate.
     */
    public Booking getFirstBookingByPredicate(Predicate<Booking> predicate) {
        Optional<Booking> optionalBooking = sortedBookingsSet.stream()
                .filter(predicate)
                .findFirst();
        if (!optionalBooking.isPresent()) {
            throw new BookingNotFoundException();
        }
        return optionalBooking.get();
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
     * Returns true if the booking exists in this set of bookings
     */
    public boolean contains(Booking booking) {
        return sortedBookingsSet.contains(booking);
    }

    /**
     * Returns true if the given booking overlaps with any existing booking in the set
     */
    private boolean canAcceptBooking(Booking toCheck) {
        requireNonNull(toCheck);
        return sortedBookingsSet.stream().noneMatch(toCheck::isOverlapping);
    }

    /**
     * Returns true if the given booking overlaps with any existing booking in the set, excluding the one it replaces
     */
    private boolean canAcceptIfReplaceBooking(Booking toReplace, Booking toCheck) {
        requireAllNonNull(toReplace, toCheck);
        return sortedBookingsSet.stream().noneMatch(
            booking -> !booking.equals(toReplace) && booking.isOverlapping(toCheck));
    }

    /**
     * Returns true if {@code Bookings} contains at least one overlapping Booking.
     */
    private static boolean bookingsAreOverlapping(Set<Booking> bookings) {
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

    /**
     * Returns the short description of the active booking
     */
    public String toStringActiveBookingShortDescription() {
        return getFirstActiveBooking().map(Booking::toStringShortDescription).orElse("");
    }

    /**
     * Returns the full description of the active booking
     */
    public String toStringActiveBooking() {
        return getFirstActiveBooking().map(Booking::toString).orElse("");
    }

    /**
     * Returns the full description of all non-active bookings
     */
    public String toStringAllOtherBookings() {
        final StringBuilder builder = new StringBuilder();
        Bookings allOtherBookings = this;
        Optional<Booking> optionalActiveBooking = getFirstActiveBooking();
        if (optionalActiveBooking.isPresent()) {
            allOtherBookings = this.remove(optionalActiveBooking.get());
        }
        builder.append(allOtherBookings);
        return builder.toString();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        int index = 1;
        for (Booking booking : sortedBookingsSet) {
            builder.append(index).append(". ").append(booking);
            index++;
        }
        return builder.toString();
    }
}
