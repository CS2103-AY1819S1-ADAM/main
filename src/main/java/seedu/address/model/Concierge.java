package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.expenses.Expense;
import seedu.address.model.expenses.ExpenseType;
import seedu.address.model.guest.Guest;
import seedu.address.model.guest.UniqueGuestList;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.UniqueRoomList;
import seedu.address.model.room.booking.Booking;
import seedu.address.model.room.booking.BookingPeriod;

/**
 * Wraps all data at the Concierge level
 * Duplicates are not allowed (by .isSameGuest comparison)
 */
public class Concierge implements ReadOnlyConcierge {

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

    public Concierge() {}

    /**
     * Creates an Concierge using the data in the {@code toBeCopied}
     */
    public Concierge(ReadOnlyConcierge toBeCopied) {
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
     * Adds a guest to Concierge.
     * The guest must not already exist in Concierge.
     */
    public void addGuest(Guest g) {
        guests.add(g);
    }

    /**
     * Replaces the given guest {@code target} in the list with {@code editedGuest}.
     * {@code target} must exist in Concierge.
     * The guest identity of {@code editedGuest} must not be the same as another existing guest in Concierge.
     */
    public void updateGuest(Guest target, Guest editedGuest) {
        requireNonNull(editedGuest);

        guests.setGuest(target, editedGuest);
    }

    /**
     * Removes {@code key} from this {@code Concierge}.
     * {@code key} must exist in Concierge.
     */
    public void removeGuest(Guest key) {
        guests.remove(key);
    }

    //=========== Room operations =============================================================

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

    /**
     * Checks out a room's booking using its room number and the specified booking period
     */
    public void checkoutRoom(RoomNumber roomNumber, BookingPeriod bookingPeriod) {
        Room room = rooms.getRoom(roomNumber);
        rooms.setRoom(room, room.checkout(bookingPeriod));
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
     * Resets the existing data of this {@code Concierge} with {@code newData}.
     */
    public void resetData(ReadOnlyConcierge newData) {
        requireNonNull(newData);

        setGuests(newData.getGuestList());
        setRooms(newData.getRoomList());
        setMenu(newData.getMenuMap());
    }

    //=========== Boolean checkers =============================================================

    /**
     * Returns true if a guest with the same identity as {@code guest} exists in Concierge.
     */
    public boolean hasGuest(Guest guest) {
        requireNonNull(guest);
        return guests.contains(guest);
    }

    //// util methods

    @Override
    public String toString() {
        return guests.asUnmodifiableObservableList().size() + " guests"
                + rooms.toString();
        // TODO: refine later
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Concierge // instanceof handles nulls
                    && guests.equals(((Concierge) other).guests) && rooms.equals(((Concierge) other).rooms));
    }

    @Override
    public int hashCode() {
        return Objects.hash(guests, rooms);
    }
}
