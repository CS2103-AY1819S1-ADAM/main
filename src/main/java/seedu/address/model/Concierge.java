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
import seedu.address.model.tag.Tag;

/**
 * Wraps all data at the Concierge level
 * Duplicates are not allowed (by .isSameGuest comparison)
 */
public class Concierge implements ReadOnlyConcierge {

    private final UniqueGuestList guests;
    private final UniqueGuestList checkedInGuests;
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
        checkedInGuests = new UniqueGuestList();
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
    public ObservableList<Guest> getCheckedInGuestList() {
        return checkedInGuests.asUnmodifiableObservableList();
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
     * Replaces the contents of the checked-in guest list with {@code guests}.
     * {@code guests} must not contain duplicate guests.
     */
    public void setCheckedInGuests(List<Guest> guests) {
        this.checkedInGuests.setGuests(guests);
    }

    /**
     * Adds a guest to Concierge's guest list.
     * The guest must not already exist in Concierge's guest list.
     */
    public void addGuest(Guest g) {
        guests.add(g);
    }

    /**
     * Adds a guest to Concierge's checked-in guest list.
     * If the guest already exists in the checked-in guest list, do nothing. This is expected
     * behavior because a guest can make multiple bookings over overlapping booking periods (just for different
     * rooms).
     */
    public void addCheckedInGuest(Guest g) {
        if (hasCheckedInGuest(g)) {
            return;
        }
        checkedInGuests.add(g);
    }

    /**
     * Replaces the given guest {@code target} in the guest list with {@code editedGuest}.
     * {@code target} must exist in Concierge's guest list.
     * The guest identity of {@code editedGuest} must not be the same as another existing guest in Concierge's
     * guest list.
     */
    public void updateGuest(Guest target, Guest editedGuest) {
        requireNonNull(editedGuest);

        guests.setGuest(target, editedGuest);
    }

    /**
     * Removes {@code key} from this {@code Concierge}'s guest list.
     * {@code key} must exist in Concierge's guest list.
     */
    public void removeGuest(Guest key) {
        guests.remove(key);
    }

    /**
     * Removes {@code key} from this {@code Concierge}'s checked-in guest list.
     * {@code key} must exist in Concierge's checked-in guest list.
     */
    public void removeCheckedInGuest(Guest key) {
        checkedInGuests.remove(key);
    }

    //=========== Room operations =============================================================

    /**
     * Adds given tags to the specified room
     */
    public void addRoomTags(RoomNumber roomNumber, Tag... tags) {
        Room room = rooms.getRoom(roomNumber);
        Room editedRoom = room.addTags(tags);
        rooms.setRoom(room, editedRoom);
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
     * Checks in the room using its room number and adds guest to the checked-in guest list
     */
    public void checkInRoom(RoomNumber roomNumber) {
        Room room = rooms.getRoom(roomNumber);
        Room checkedInRoom = room.checkIn();
        rooms.setRoom(room, checkedInRoom);

        // First active booking is guaranteed to be present after executing room.checkIn() above
        Guest guestToCheckIn = checkedInRoom.getBookings().getFirstBooking().getGuest();

        addCheckedInGuest(guestToCheckIn);
    }

    /**
     * Checks out a room's first booking using its room number and remove the guest from the checked-in guest list
     */
    public void checkoutRoom(RoomNumber roomNumber) {
        Room room = rooms.getRoom(roomNumber);
        rooms.setRoom(room, room.checkout());

        Booking bookingToCheckout = room.getBookings().getFirstBooking();
        Guest guestToCheckout = bookingToCheckout.getGuest();

        // If the guest does not exist in the checked-in guest list, this block does nothing. This is expected
        // behavior, because a guest can have multiple bookings at once and checkout one before another.
        // Though not ideal, this is the current implementation.
        // TODO Check if guest to remove from checked-in list still has other bookings in other rooms OR
        // TODO Create new guest subclass that stores the room information, and add instances of that into
        // checked-in guest list
        if (hasCheckedInGuest(guestToCheckout)) {
            removeCheckedInGuest(guestToCheckout);
        }

        // If the guest exists in the archived guest list, this block does nothing. This is expected because a guest
        // may have stayed in the hotel before, so he would already be in the archived guest list.
        if (!hasGuest(guestToCheckout)) {
            addGuest(guestToCheckout);
        }
    }

    /**
     * Checks out a room's booking using its room number and the specified booking period and remove the guest
     * from the checked-in guest list
     */
    public void checkoutRoom(RoomNumber roomNumber, BookingPeriod bookingPeriod) {
        Room room = rooms.getRoom(roomNumber);
        rooms.setRoom(room, room.checkout(bookingPeriod));

        Booking bookingToCheckout = room.getBookings()
            .getFirstBookingByPredicate(booking -> booking.getBookingPeriod().equals(bookingPeriod));
        Guest guestToCheckout = bookingToCheckout.getGuest();

        // If the guest does not exist in the checked-in guest list, this code below does nothing. This is expected
        // behavior, because a guest can have multiple bookings at once and checkout one before another.
        // Though not ideal, this is the current implementation.
        // TODO Check if guest to remove from checked-in list still has other bookings in other rooms OR
        // TODO Create new guest subclass that stores the room information, and add instances of that into
        // checked-in guest list
        if (!hasCheckedInGuest(guestToCheckout)) {
            return;
        }
        removeCheckedInGuest(guestToCheckout);

        // If the guest exists in the archived guest list, the code below does nothing. This is expected because a guest
        // may have stayed in the hotel before, so he would already be in the archived guest list.
        if (hasGuest(guestToCheckout)) {
            return;
        }
        addGuest(guestToCheckout);

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
        setCheckedInGuests(newData.getCheckedInGuestList());
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

    /**
     * Returns true if a guest with the same identity as {@code guest} exists in Concierge's checked-in guest list.
     */
    public boolean hasCheckedInGuest(Guest guest) {
        requireNonNull(guest);
        return checkedInGuests.contains(guest);
    }

    //// util methods

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Guests:\n")
                .append(guests.asUnmodifiableObservableList())
                .append("\nChecked-in guests:\n")
                .append(checkedInGuests.asUnmodifiableObservableList())
                .append("\nRooms:\n")
                .append(rooms.asUnmodifiableObservableList());
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Concierge // instanceof handles nulls
                    && guests.equals(((Concierge) other).guests)
                    && checkedInGuests.equals(((Concierge) other).checkedInGuests)
                    && rooms.equals(((Concierge) other).rooms));
    }

    @Override
    public int hashCode() {
        return Objects.hash(guests, checkedInGuests, rooms);
    }

}
