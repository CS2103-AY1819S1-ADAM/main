package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Guest;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyGuestList {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Guest> getPersonList();

}
