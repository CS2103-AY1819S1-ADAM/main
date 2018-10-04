package seedu.address.model.room;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.room.exceptions.OverlappingReservationException;
import seedu.address.model.room.exceptions.ReservationNotFoundException;

/**
 * A list of Reservations that maintains non-overlapping property between its elements and does not allow nulls.
 * A Reservation is considered non-overlapping by comparing using
 * {@code Reservation#canAcceptReservation(Reservation)}. As such, adding and updating of
 * Reservations uses Reservation#canAcceptReservation(Reservation) so as to ensure that the
 * Reservation being added or updated does not overlap any existing ones in Reservations.
 * However, the removal of a Reservation uses Reservation#equals(Object) so
 * as to ensure that the Reservation with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Reservation#isOverlapping(Reservation)
 */
public class Reservations implements Iterable<Reservation> {

    private final ObservableList<Reservation> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list canAcceptReservation an equivalent Reservation as the given argument.
     */
    public boolean canAcceptReservation(Reservation toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isOverlapping);
    }

    /**
     * Returns true if {@code Reservations} contains no overlapping Reservations.
     */
    private boolean reservationsAreNotOverlapping(List<Reservation> reservations) {
        for (int i = 0; i < reservations.size() - 1; i++) {
            for (int j = i + 1; j < reservations.size(); j++) {
                if (reservations.get(i).isOverlapping(reservations.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Adds a Reservation to the list.
     * The Reservation must not already exist in the list.
     */
    public void add(Reservation toAdd) {
        requireNonNull(toAdd);
        if (canAcceptReservation(toAdd)) {
            throw new OverlappingReservationException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent Reservation from the list.
     * The Reservation must exist in the list.
     */
    public void remove(Reservation toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ReservationNotFoundException();
        }
    }

    /**
     * Replaces the Reservation {@code target} in the list with {@code editedReservation}.
     * {@code target} must exist in the list.
     * The Reservation identity of {@code editedReservation} must not be the same as another existing
     * Reservation in the list.
     */
    public void setReservation(Reservation target, Reservation editedReservation) {
        requireAllNonNull(target, editedReservation);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ReservationNotFoundException();
        }

        if (canAcceptReservation(editedReservation)) {
            throw new OverlappingReservationException();
        }

        internalList.set(index, editedReservation);
    }


    public void setReservations(Reservations replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code Reservations}.
     * {@code Reservations} must not contain duplicate Reservations.
     */
    public void setReservations(List<Reservation> reservations) {
        requireAllNonNull(reservations);
        if (!reservationsAreNotOverlapping(reservations)) {
            throw new OverlappingReservationException();
        }

        internalList.setAll(reservations);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Reservation> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Reservation> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Reservations // instanceof handles nulls
                        && internalList.equals(((Reservations) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        internalList.forEach(builder::append);
        return builder.toString();
    }
}
