package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
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
import seedu.address.model.room.booking.exceptions.ExpiredBookingException;
import seedu.address.model.room.booking.exceptions.NewBookingStartsBeforeOldBookingCheckedIn;
import seedu.address.model.room.booking.exceptions.OldBookingStartsBeforeNewBookingCheckedIn;
import seedu.address.model.room.booking.exceptions.OverlappingBookingException;
import seedu.address.model.room.exceptions.OriginalRoomReassignException;
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
    public void addCheckedInGuestIfNotPresent(Guest g) {
        if (hasCheckedInGuest(g)) {
            return;
        }
        checkedInGuests.add(g);
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
     * Reassigns the booking identified by {@code startDate} in the room identified by {@code roomNumber} to the room
     * identified by {@code newRoomNumber}. The expenses are moved over to the new room as well.<br>
     * The following checks are conducted in succession, each possibly throwing an exception:<br>
     * If room and new room are equal, throw OriginalRoomReassignException.<br>
     * If the booking does not exist, throw NoBookingFoundException.<br>
     * If the booking is expired, or the new room's first booking is expired, throw ExpiredBookingException.<br>
     * If the booking starts before the new booking, and the new booking is checked-in,
     * throw OldBookingStartsBeforeNewBookingCheckedIn.<br>
     * If the new booking starts before the old booking, and the old booking is checked-in,
     * throw NewBookingStartsBeforeOldBookingCheckedIn.<br>
     * If the booking overlaps with any of new room's bookings, throw OverlappingBookingException.
     */
    public void reassignRoom(RoomNumber roomNumber, LocalDate startDate, RoomNumber newRoomNumber) {
        Room room = rooms.getRoom(roomNumber);
        Room newRoom = rooms.getRoom(newRoomNumber);

        if (room.equals(newRoom)) {
            throw new OriginalRoomReassignException();
        }

        Booking bookingToReassign = room.getBookings()
                .getFirstBookingByPredicate(booking -> booking.getBookingPeriod().getStartDate().equals(startDate));
        if (bookingToReassign.isExpired()) {
            throw new ExpiredBookingException();
        }

        if (newRoom.hasBookings()) {
            Booking newRoomFirstBooking = newRoom.getBookings().getFirstBooking();

            if (newRoomFirstBooking.isExpired()) {
                throw new ExpiredBookingException();
            }

            if (bookingToReassign.isOverlapping(newRoomFirstBooking)) {
                throw new OverlappingBookingException();
            }

            if (bookingToReassign.startsBefore(newRoomFirstBooking) && newRoomFirstBooking.getIsCheckedIn()) {
                throw new OldBookingStartsBeforeNewBookingCheckedIn();
            }

            if (newRoomFirstBooking.startsBefore(bookingToReassign) && bookingToReassign.getIsCheckedIn()) {
                throw new NewBookingStartsBeforeOldBookingCheckedIn();
            }
        }

        Room editedNewRoom = newRoom.addBooking(bookingToReassign);
        for (Expense expense : room.getExpenses().getExpensesList()) {
            editedNewRoom = editedNewRoom.addExpense(expense);
        }
        rooms.setRoom(newRoom, editedNewRoom);

        Room editedRoom = room.checkout(bookingToReassign);
        rooms.setRoom(room, editedRoom);
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

        // First booking is guaranteed to be present after executing room.checkIn() above
        Guest guestToCheckIn = checkedInRoom.getBookings().getFirstBooking().getGuest();

        addCheckedInGuestIfNotPresent(guestToCheckIn);
    }

    /**
     * Checks out a room's first booking
     */
    public void checkoutRoom(RoomNumber roomNumber) {
        Room room = rooms.getRoom(roomNumber);
        checkoutRoom(room, room.getBookings().getFirstBooking());
    }

    /**
     * Checks out a room's booking using the specified start date
     */
    public void checkoutRoom(RoomNumber roomNumber, LocalDate startDate) {
        Room room = rooms.getRoom(roomNumber);
        checkoutRoom(room, room
                .getBookings()
                .getFirstBookingByPredicate(booking -> booking.getBookingPeriod().getStartDate().equals(startDate)));
    }

    /**
     * Checks out the given booking from the given room.
     *
     * If the booking's guest was checked-in (i.e. exists in the checked-in guest list), check:
     * 1) If the guest does not have checked-in bookings in other rooms, remove him from the checked-in guest list.
     * 2) If the guest does not exists in the archived guest list. add him to the archived guest list
     *
     * Reason for initial check: Guests who did not check-in do not count as having stayed in the hotel before.
     * Reason for 1): Guests can have multiple checked-in bookings at once.
     * Reason for 2): Guests may have stayed in the hotel before, and would thus already be in the archived guest list.
     */
    private void checkoutRoom(Room room, Booking bookingToCheckout) {
        rooms.setRoom(room, room.checkout(bookingToCheckout));

        Guest guestToCheckout = bookingToCheckout.getGuest();

        if (hasCheckedInGuest(guestToCheckout)) {
            boolean guestHasOtherBookings = rooms.asUnmodifiableObservableList()
                    .stream().anyMatch(r -> r.getBookings().getSortedBookingsSet()
                        .stream().anyMatch(b -> b.getIsCheckedIn() && b.getGuest().equals(guestToCheckout)));
            if (!guestHasOtherBookings) {
                removeCheckedInGuest(guestToCheckout);
            }

            if (!hasGuest(guestToCheckout)) {
                addGuest(guestToCheckout);
            }
        }

    }

    public void setMenu(Map<String, ExpenseType> menu) {
        this.menu.setMenu(menu);
    }

    /**
     * Adds an expense to the room using its room number
     */
    public void addExpense(RoomNumber roomNumber, Expense expense) {
        Room room = rooms.getRoom(roomNumber);
        Room editedRoom = room.addExpense(expense);
        rooms.setRoom(room, editedRoom);
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
