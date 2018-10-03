package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.guest.Guest;

/**
 * Unmodifiable view of an guest list.
 */
public interface ReadOnlyGuestList {

    /**
     * Returns an unmodifiable view of the guests list.
     * This list will not contain any duplicate guests.
     */
    ObservableList<Guest> getListOfGuests();

}
