package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.person.Guest;
import seedu.address.model.room.RoomNumber;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Guest> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a guest with the same identity as {@code guest} exists in the address book.
     */
    boolean hasPerson(Guest guest);

    /**
     * Deletes the given guest.
     * The guest must exist in the address book.
     */
    void deletePerson(Guest target);

    /**
     * Adds the given guest.
     * {@code guest} must not already exist in the address book.
     */
    void addPerson(Guest guest);

    /**
     * Replaces the given guest {@code target} with {@code editedGuest}.
     * {@code target} must exist in the address book.
     * The guest identity of {@code editedGuest} must not be the same as another existing guest in the address book.
     */
    void updatePerson(Guest target, Guest editedGuest);

    /** Returns an unmodifiable view of the filtered guest list */
    ObservableList<Guest> getFilteredPersonList();

    /**
     * Updates the filter of the filtered guest list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Guest> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();

    /**
     * Checks out the room by its room number and removes its registered guest.
     */
    void checkoutRoom(RoomNumber roomNumber);

    /**
     * Saves the current room list state for undo/redo
     */
    void commitRoomList();
}
