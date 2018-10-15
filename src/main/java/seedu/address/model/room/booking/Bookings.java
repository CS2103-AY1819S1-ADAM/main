package seedu.address.model.room.booking;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.address.model.room.booking.exceptions.BookingNotFoundException;
import seedu.address.model.room.booking.exceptions.OverlappingBookingException;

/**
 * A list of Bookings that maintains non-overlapping property between its elements and does not allow nulls.
 * A Booking is considered non-overlapping by comparing using
 * {@code Booking#canAcceptBooking(Booking)}. As such, adding and updating of
 * Bookings uses Booking#canAcceptBooking(Booking) to ensure that the
 * Booking being added or updated does not overlap any existing ones in Bookings.
 * However, the removal of a Booking uses Booking#equals(Object) so
 * as to ensure that the Booking with exactly the same fields will be removed.
 *
 * A sorted list of bookings is also maintained to support viewing of bookings in chronological order.
 * Note: SortedList only provides a sorted view of the list. Any changes to the list must be made through the underlying
 * ObservableList.
 *
 * Supports a minimal set of list operations.
 *
 * @see Booking#isOverlapping(Booking)
 */
public class Bookings implements Iterable<Booking> {

    private final ObservableList<Booking> internalList = FXCollections.observableArrayList();
    private final SortedList<Booking> sortedList = new SortedList<>(internalList);

    /**
     * Initializes an empty bookings object
     */
    public Bookings() {}

    /**
     * Copies the internal list of the given bookings object
     */
    public Bookings(Bookings bookings) {
        this.internalList.setAll(bookings.internalList);
    }

    /**
     * Returns true if the given booking overlaps with any existing booking in the list
     */
    public boolean canAcceptBooking(Booking toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isOverlapping);
    }

    /**
     * Returns true if the given booking overlaps with any existing booking in the list, excluding the one it replaces
     */
    public boolean canAcceptIfReplaceBooking(Booking toReplace, Booking toCheck) {
        requireAllNonNull(toReplace, toCheck);
        for (Booking booking : internalList) {
            if (booking.equals(toReplace)) {
                continue;
            }
            if (booking.isOverlapping(toCheck)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if {@code Bookings} contains no overlapping Bookings.
     */
    private boolean bookingsAreNotOverlapping(List<Booking> bookings) {
        for (int i = 0; i < bookings.size() - 1; i++) {
            for (int j = i + 1; j < bookings.size(); j++) {
                if (bookings.get(i).isOverlapping(bookings.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns true if there are no bookings
     */
    public boolean isEmpty() {
        return internalList.isEmpty();
    }

    /**
     * Gets the first booking in the list
     */
    public Booking getFirstBooking() {
        if (isEmpty()) {
            throw new NullPointerException("There are no bookings.");
        }
        return sortedList.get(0);
    }

    /**
     * Adds a Booking to the list.
     * The Booking must not already exist in the list.
     */
    public void add(Booking toAdd) {
        requireNonNull(toAdd);
        if (canAcceptBooking(toAdd)) {
            throw new OverlappingBookingException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent Booking from the list.
     * The Booking must exist in the list.
     */
    public void remove(Booking toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new BookingNotFoundException();
        }
    }

    /**
     * Removes all expired bookings from the list.
     */
    public void clearExpiredBookings() {
        internalList.removeIf(Booking::isExpired);
    }

    /**
     * Replaces the Booking {@code target} in the list with {@code editedBooking}.
     * {@code target} must exist in the list.
     */
    public void setBooking(Booking target, Booking editedBooking) {
        requireAllNonNull(target, editedBooking);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new BookingNotFoundException();
        }

        if (!target.isSameBooking(editedBooking) && !canAcceptIfReplaceBooking(target, editedBooking)) {
            throw new OverlappingBookingException();
        }

        internalList.set(index, editedBooking);
    }


    public void setBookings(Bookings replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code Bookings}.
     * {@code Bookings} must not contain duplicate Bookings.
     */
    public void setBookings(List<Booking> bookings) {
        requireAllNonNull(bookings);
        if (!bookingsAreNotOverlapping(bookings)) {
            throw new OverlappingBookingException();
        }

        internalList.setAll(bookings);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Booking> asUnmodifiableSortedList() {
        return FXCollections.unmodifiableObservableList(sortedList);
    }

    @Override
    public Iterator<Booking> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Bookings // instanceof handles nulls
                        && internalList.equals(((Bookings) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        sortedList.forEach(builder::append);
        return builder.toString();
    }
}
