package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.guest.Guest;
import seedu.address.model.room.RoomNumber;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Guest> PREDICATE_SHOW_ALL_GUESTS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyGuestList newData);

    /** Returns the GuestList */
    ReadOnlyGuestList getGuestList();

    /**
     * Returns true if a guest with the same identity as {@code guest} exists
     * in the guest list.
     */
    boolean hasGuest(Guest guest);

    /**
     * Deletes the given guest.
     * The guest must exist in the guest list.
     */
    void deleteGuest(Guest target);

    /**
     * Adds the given guest.
     * {@code guest} must not already exist in the guest list.
     */
    void addGuest(Guest guest);

    /**
     * Replaces the given guest {@code target} with {@code editedGuest}.
     * {@code target} must exist in the guest list.
     * The guest identity of {@code editedGuest} must not be the same as
     * another existing guest in the guest list.
     */
    void updateGuest(Guest target, Guest editedGuest);

    /** Returns an unmodifiable view of the filtered guest list */
    ObservableList<Guest> getFilteredGuestList();

    /**
     * Updates the filter of the filtered guest list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredGuestList(Predicate<Guest> predicate);

    /**
     * Returns true if the model has previous guest list states to restore.
     */
    boolean canUndoGuestList();

    /**
     * Returns true if the model has undone guest list states to restore.
     */
    boolean canRedoGuestList();

    /**
     * Restores the model's guest list to its previous state.
     */
    void undoGuestList();

    /**
     * Restores the model's guest list to its previously undone state.
     */
    void redoGuestList();

    /**
     * Saves the current guest list state for undo/redo.
     */
    void commitGuestList();

    /**
     * Returns the RoomList
     * @return
     */
    RoomList getRoomList();

    /**
     * Checks out the room.
     * @param roomNumber
     */
    void checkoutRoom(RoomNumber roomNumber);

    /**
     * Saves the current room list state for undo/redo
     */
    void commitRoomList();
}
