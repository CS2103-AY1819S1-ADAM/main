package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.person.Guest;
import seedu.address.model.person.UniqueGuestList;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.UniqueRoomList;
import seedu.address.model.room.booking.Booking;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameGuest comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueGuestList persons;
    private final UniqueRoomList rooms;
    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniqueGuestList();
        rooms = new UniqueRoomList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the guest list with {@code guests}.
     * {@code guests} must not contain duplicate guests.
     */
    public void setPersons(List<Guest> guests) {
        this.persons.setGuests(guests);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setRooms(newData.getRoomList());
    }

    //// guest-level operations

    /**
     * Returns true if a guest with the same identity as {@code guest} exists in the address book.
     */
    public boolean hasPerson(Guest guest) {
        requireNonNull(guest);
        return persons.contains(guest);
    }

    /**
     * Adds a guest to the address book.
     * The guest must not already exist in the address book.
     */
    public void addPerson(Guest p) {
        persons.add(p);
    }

    /**
     * Replaces the given guest {@code target} in the list with {@code editedGuest}.
     * {@code target} must exist in the address book.
     * The guest identity of {@code editedGuest} must not be the same as another existing guest in the address book.
     */
    public void updatePerson(Guest target, Guest editedGuest) {
        requireNonNull(editedGuest);

        persons.setGuest(target, editedGuest);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Guest key) {
        persons.remove(key);
    }

    /**
     * Adds a room to the address book.
     * The room must not already exist in the address book.
     */
    public void addRoom(Room r) {
        rooms.add(r);
    }

    /**
     * Returns true if a room with the same identity as {@code room} exists in the address book.
     */
    public boolean hasRoom(Room room) {
        requireNonNull(room);
        return rooms.contains(room);
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
        rooms.addBooking(roomNumber, booking);
    }

    /**
     * Returns true if the room identified by its room number is checked in.
     */
    public boolean isRoomCheckedIn(RoomNumber roomNumber) {
        return rooms.isRoomCheckedIn(roomNumber);
    }

    /**
     * Returns true if the room's bookings is non-empty
     */
    public boolean roomHasBooking(RoomNumber roomNumber) {
        return rooms.roomHasBooking(roomNumber);
    }

    /**
     * Returns true if the room's first booking is active.
     */
    public boolean roomHasActiveBooking(RoomNumber roomNumber) {
        return rooms.roomHasActiveBooking(roomNumber);
    }

    /**
     * Returns true if the room's first booking is active or expired
     */
    public boolean roomHasActiveOrExpiredBooking(RoomNumber roomNumber) {
        return rooms.roomHasActiveOrExpiredBooking(roomNumber);
    }

    /**
     * Checks in the room using its room number
     */
    public void checkinRoom(RoomNumber roomNumber) {
        rooms.checkinRoom(roomNumber);
    }

    /**
     * Checks out a room using its room number
     */
    public void checkoutRoom(RoomNumber roomNumber) {
        rooms.checkoutRoom(roomNumber);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Guest> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }


    @Override
    public ObservableList<Room> getRoomList() {
        return rooms.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
