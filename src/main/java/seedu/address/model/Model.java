package seedu.address.model;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.expenses.Expense;
import seedu.address.model.guest.Guest;
import seedu.address.model.login.InvalidLogInException;
import seedu.address.model.login.InvalidLogOutException;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.Booking;
import seedu.address.model.tag.Tag;

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
    void resetData(ReadOnlyConcierge newData);

    /** Returns the Concierge */
    ReadOnlyConcierge getConcierge();

    /** Returns the Menu.*/
    Menu getMenu();

    // =========== Signing in. ================================================

    /**
     * Returns true if Concierge is currently logged in.
     */
    boolean isSignedIn();

    /**
     * Returns an Optional containing the username currently tagged to the
     * session.
     */
    Optional<String> getUsername();

    /**
     * Attempts to sign in with username {@code username} and password {@code
     * hashedPassword}.
     */
    void signIn(String username, String hashedPassword) throws InvalidLogInException;

    /**
     * Attempts to sign out of Concierge.
     */
    void signOut() throws InvalidLogOutException;

    // =========== Methods for guest. =========================================

    /**
     * Returns true if a guest with the same identity as {@code guest} exists in Concierge.
     */
    boolean hasGuest(Guest guest);

    /**
     * Deletes the given guest.
     * The guest must exist in Concierge.
     */
    void deleteGuest(Guest target);

    /**
     * Adds the given guest.
     * {@code guest} must not already exist in Concierge.
     */
    void addGuest(Guest guest);

    /**
     * Get the flag representing the last displayed list.
     */
    Prefix getDisplayedListFlag();

    /**
     * Set the flag representing the last displayed list.
     */
    void setDisplayedListFlag(Prefix flag);

    /** Returns an unmodifiable view of the filtered guest list */
    ObservableList<Guest> getFilteredGuestList();

    /**
     * Updates the filter of the filtered guest list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredGuestList(Predicate<Guest> predicate);

    /**
     * Returns an unmodifiable view of the list of checked-in {@code Guest} backed by the internal list of
     * {@code versionedConcierge}
     */
    ObservableList<Guest> getFilteredCheckedInGuestList();

    void updateFilteredCheckedInGuestList(Predicate<Guest> predicate);

    /** Returns an unmodifiable view of the filtered room list */
    ObservableList<Room> getFilteredRoomList();

    /**
     * Updates the filter of the filtered room list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRoomList(Predicate<Room> predicate);

    //=========== Methods for room. ===========================================

    /**
     * Adds given tags to the specified room
     */
    public void addRoomTags(RoomNumber roomNumber, Tag... tags);


    /**
     * Reassigns the booking identified by {@code startDate} in the room identified by {@code roomNumber} to the room
     * identified by {@code newRoomNumber}
     */
    public void reassignRoom(RoomNumber roomNumber, LocalDate startDate, RoomNumber newRoomNumber);

    /**
     * Add a booking to a room identified by its room number.
     */
    void addBooking(RoomNumber roomNumber, Booking booking);

    /**
     *  Displays room list instead of guest list
     */
    //void displayRoomList(Predicate<Room> predicate);

    /**
     * Checks in the room by its room number
     */
    void checkInRoom(RoomNumber roomNumber);

    /**
     * Checks out the room.
     * @param roomNumber
     */
    void checkoutRoom(RoomNumber roomNumber);

    /**
     * Checks out a room's booking using its room number and the specified start date
     */
    void checkoutRoom(RoomNumber roomNumber, LocalDate startDate);

    /**
     * Adds an Expense to a room.
     */
    void addExpense(RoomNumber roomNumber, Expense expense);


    // =========== Methods for undo and redo. =================================

    /**
     * Resets the undo/ redo history.
     */
    void resetUndoRedoHistory();

    /**
     * Returns true if the model has previous Concierge states to restore.
     */
    boolean canUndoConcierge();

    /**
     * Returns true if the model has undone Concierge states to restore.
     */
    boolean canRedoConcierge();

    /**
     * Restores the model's Concierge to its previous state.
     */
    void undoConcierge();

    /**
     * Restores the model's Concierge to its previously undone state.
     */
    void redoConcierge();

    /**
     * Saves the current Concierge state for undo/redo.
     */
    void commitConcierge();
}
