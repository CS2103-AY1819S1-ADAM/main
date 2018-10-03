package seedu.address.model.room;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.room.exceptions.OverlappingReservationDateException;
import seedu.address.model.room.exceptions.ReservationDateNotFoundException;

/**
 * A list of ReservationDates that non-overlapping property between its elements and does not allow nulls.
 * A ReservationDate is considered non-overlapping by comparing using
 * {@code ReservationDate#isOverlapping(ReservationDate)}. As such, adding and updating of
 * ReservationDates uses ReservationDate#isOverlapping(ReservationDate) so as to ensure that the
 * ReservationDate being added or updated does not overlap any existing ones in ReservationDates.
 * However, the removal of a ReservationDate uses ReservationDate#equals(Object) so
 * as to ensure that the ReservationDate with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see ReservationDate#isOverlapping(ReservationDate)
 */
public class ReservationDates implements Iterable<ReservationDate> {

    private final ObservableList<ReservationDate> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list isOverlapping an equivalent ReservationDate as the given argument.
     */
    public boolean isOverlapping(ReservationDate toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isOverlapping);
    }

    /**
     * Adds a ReservationDate to the list.
     * The ReservationDate must not already exist in the list.
     */
    public void add(ReservationDate toAdd) {
        requireNonNull(toAdd);
        if (isOverlapping(toAdd)) {
            throw new OverlappingReservationDateException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the ReservationDate {@code target} in the list with {@code editedReservationDate}.
     * {@code target} must exist in the list.
     * The ReservationDate identity of {@code editedReservationDate} must not be the same as another existing
     * ReservationDate in the list.
     */
    public void setReservationDate(ReservationDate target, ReservationDate editedReservationDate) {
        requireAllNonNull(target, editedReservationDate);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ReservationDateNotFoundException();
        }
        // If edited reservation date is the same as the original, do nothing.
        if (target.equals(editedReservationDate)) {
            return;
        }

        if (isOverlapping(editedReservationDate)) {
            throw new OverlappingReservationDateException();
        }

        internalList.set(index, editedReservationDate);
    }

    /**
     * Removes the equivalent ReservationDate from the list.
     * The ReservationDate must exist in the list.
     */
    public void remove(ReservationDate toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ReservationDateNotFoundException();
        }
    }

    public void setReservationDates(ReservationDates replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code ReservationDates}.
     * {@code ReservationDates} must not contain duplicate ReservationDates.
     */
    public void setReservationDates(List<ReservationDate> reservationDates) {
        requireAllNonNull(reservationDates);
        if (!reservationDatesAreNotOverlapping(reservationDates)) {
            throw new OverlappingReservationDateException();
        }

        internalList.setAll(reservationDates);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReservationDate> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<ReservationDate> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReservationDates // instanceof handles nulls
                        && internalList.equals(((ReservationDates) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code ReservationDates} contains no overlapping ReservationDates.
     */
    private boolean reservationDatesAreNotOverlapping(List<ReservationDate> reservationDates) {
        for (int i = 0; i < reservationDates.size() - 1; i++) {
            for (int j = i + 1; j < reservationDates.size(); j++) {
                if (reservationDates.get(i).isOverlapping(reservationDates.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
