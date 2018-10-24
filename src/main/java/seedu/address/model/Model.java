package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.guest.Guest;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.Booking;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true/false */
    Predicate<Guest> PREDICATE_SHOW_ALL_GUESTS = unused -> true;
    Predicate<Guest> PREDICATE_SHOW_NO_GUESTS = unused -> false;
    Predicate<Room> PREDICATE_SHOW_ALL_ROOMS = unused -> true;
    Predicate<Room> PREDICATE_SHOW_NO_ROOMS = unused -> false;


    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    // =========== Methods for guest. =========================================

    /**
     * Returns true if a guest with the same identity as {@code guest} exists in the address book.
     */
    boolean hasGuest(Guest guest);

    /**
     * Deletes the given guest.
     * The guest must exist in the address book.
     */
    void deleteGuest(Guest target);

    /**
     * Adds the given guest.
     * {@code guest} must not already exist in the address book.
     */
    void addGuest(Guest guest);

    /**
     * Replaces the given guest {@code target} with {@code editedGuest}.
     * {@code target} must exist in the address book.
     * The guest identity of {@code editedGuest} must not be the same as another existing guest in the address book.
     */
    void updateGuest(Guest target, Guest editedGuest);

    /** Returns an unmodifiable view of the filtered guest list */
    ObservableList<Guest> getFilteredGuestList();

    /**
     * Updates the filter of the filtered guest list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredGuestList(Predicate<Guest> predicate);

    /** Returns an unmodifiable view of the filtered room list */
    ObservableList<Room> getFilteredRoomList();

    /**
     * Updates the filter of the filtered room list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRoomList(Predicate<Room> predicate);

    //=========== Methods for room. ===========================================

    /**
     * Returns true if the room's bookings is non-empty
     */
    boolean roomHasBooking(RoomNumber roomNumber);

    /**
     * Returns true if the room's first booking is active.
     */
    boolean roomHasActiveBooking(RoomNumber roomNumber);

    /**
     * Returns true if the room's first booking is active or upcoming
     */
    public boolean roomHasActiveOrExpiredBooking(RoomNumber roomNumber);

    /**
     * Add a booking to a room identified by its room number.
     */
    public void addBooking(RoomNumber roomNumber, Booking booking);

    /**
     *  Displays room list instead of guest list
     */
    //void displayRoomList(Predicate<Room> predicate);

    /**
     * Checks in the room by its room number
     */
    void checkinRoom(RoomNumber roomNumber);

    /**
     * Checks out the room.
     * @param roomNumber
     */
    void checkoutRoom(RoomNumber roomNumber);

    /**
     * Returns true if the room identified by its room number is checked in.
     */
    boolean isRoomCheckedIn(RoomNumber roomNumber);

    /* =========== Methods for undo and redo. =================================

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
}
