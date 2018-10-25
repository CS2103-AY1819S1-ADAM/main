package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import seedu.address.model.expenses.Expense;
import seedu.address.model.expenses.ExpenseType;
import seedu.address.model.guest.Guest;
import seedu.address.model.guest.UniqueGuestList;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.UniqueRoomList;
import seedu.address.model.room.booking.Booking;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameGuest comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueGuestList guests;
    private final UniqueRoomList rooms;
    private final Menu menu;
    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        guests = new UniqueGuestList();
        rooms = new UniqueRoomList();
        menu = new Menu();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Guests in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //=========== Getters =============================================================

    @Override
    public ObservableList<Guest> getGuestList() {
        return guests.asUnmodifiableObservableList();
    }


    @Override
    public ObservableList<Room> getRoomList() {
        return rooms.asUnmodifiableObservableList();
    }

    @Override
    public Menu getMenu() {
        return menu;
    }

    @Override
    public Map<String, ExpenseType> getMenuMap() {
        return menu.asUnmodifiableMap();
    }

    //=========== Guest operations =============================================================

    /**
     * Replaces the contents of the guest list with {@code guests}.
     * {@code guests} must not contain duplicate guests.
     */
    public void setGuests(List<Guest> guests) {
        this.guests.setGuests(guests);
    }

    /**
     * Adds a guest to the address book.
     * The guest must not already exist in the address book.
     */
    public void addGuest(Guest g) {
        guests.add(g);
    }

    /**
     * Replaces the given guest {@code target} in the list with {@code editedGuest}.
     * {@code target} must exist in the address book.
     * The guest identity of {@code editedGuest} must not be the same as another existing guest in the address book.
     */
    public void updateGuest(Guest target, Guest editedGuest) {
        requireNonNull(editedGuest);

        guests.setGuest(target, editedGuest);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeGuest(Guest key) {
        guests.remove(key);
    }

    //=========== Room operations =============================================================

    /**
     * Adds a room to the address book.
     * The room must not already exist in the address book.
     */
    public void addRoom(Room r) {
        rooms.add(r);
    }

    /**
     * Replaces the contents of the room list with {@code rooms}.
     * {@code rooms} must not contain duplicate rooms.
     */
    public void setRooms(List<Room> rooms) {
        this.rooms.setRooms(rooms);
    }

    /**
     * Add a booking to a room identified by its room number.
     */
    public void addBooking(RoomNumber roomNumber, Booking booking) {
        Room room = rooms.getRoom(roomNumber);
        Room editedRoom = room.addBooking(booking);
        rooms.setRoom(room, editedRoom);
    }

    /**
     * Checks in the room using its room number
     */
    public void checkInRoom(RoomNumber roomNumber) {
        Room room = rooms.getRoom(roomNumber);
        rooms.setRoom(room, room.checkIn());
    }

    /**
     * Checks out a room using its room number
     */
    public void checkoutRoom(RoomNumber roomNumber) {
        Room room = rooms.getRoom(roomNumber);
        rooms.setRoom(room, room.checkout());
    }

    public void setMenu(Map<String, ExpenseType> menu) {
        this.menu.setMenu(menu);
    }

    /**
     * Adds an expense to the room using its room number
     */
    public void addExpense(RoomNumber roomNumber, Expense expense) {
        rooms.getRoom(roomNumber).addExpense(expense);
    }

    //=========== Reset data =============================================================

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setGuests(newData.getGuestList());
        setRooms(newData.getRoomList());
        setMenu(newData.getMenuMap());
    }

    //=========== Boolean checkers =============================================================

    /**
     * Returns true if a guest with the same identity as {@code guest} exists in the address book.
     */
    public boolean hasGuest(Guest guest) {
        requireNonNull(guest);
        return guests.contains(guest);
    }

    /**
     * Returns true if a room with the same identity as {@code room} exists in the address book.
     */
    public boolean hasRoom(Room room) {
        requireNonNull(room);
        return rooms.contains(room);
    }

    /**
     * Returns true if the room identified by its room number is checked in.
     */
    public boolean isRoomCheckedIn(RoomNumber roomNumber) {
        return rooms.getRoom(roomNumber).isCheckedIn();
    }

    /**
     * Returns true if the room's bookings is non-empty
     */
    public boolean roomHasBookings(RoomNumber roomNumber) {
        return rooms.getRoom(roomNumber).hasBookings();
    }

    /**
     * Returns true if the room's first booking is active.
     */
    public boolean roomHasActiveBooking(RoomNumber roomNumber) {
        return rooms.getRoom(roomNumber).hasActiveBooking();
    }

    /**
     * Returns true if the room's first booking is active or expired
     */
    public boolean roomHasActiveOrExpiredBooking(RoomNumber roomNumber) {
        return rooms.getRoom(roomNumber).hasActiveOrExpiredBooking();
    }

    //// util methods

    @Override
    public String toString() {
        return guests.asUnmodifiableObservableList().size() + " guests";
        // TODO: refine later
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && guests.equals(((AddressBook) other).guests));
    }

    @Override
    public int hashCode() {
        return guests.hashCode();
    }
}
