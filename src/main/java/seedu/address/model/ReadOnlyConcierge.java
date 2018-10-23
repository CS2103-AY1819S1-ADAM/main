package seedu.address.model;

import java.util.Map;

import javafx.collections.ObservableList;
import seedu.address.model.expenses.ExpenseType;
import seedu.address.model.person.Guest;
import seedu.address.model.room.Room;

/**
 * Unmodifiable view of an concierge
 */
public interface ReadOnlyConcierge {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Guest> getPersonList();

    /**
     * Returns an unmodifiable view of the room list.
     * This list will not contain any duplicate rooms.
     */
    ObservableList<Room> getRoomList();

    /**
     * Returns the menu for reference purposes.
     */
    Menu getMenu();

    /**
     * Returns an unmodifiable view of the menu.
     */
    Map<String, ExpenseType> getMenuMap();
}
