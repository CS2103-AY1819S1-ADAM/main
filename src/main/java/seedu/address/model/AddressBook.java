package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.person.Guest;
import seedu.address.model.person.UniqueGuestList;
import seedu.address.model.room.Room;
import seedu.address.model.room.UniqueRoomList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameGuest comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueGuestList persons;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniqueGuestList();
    }
    private final UniqueRoomList rooms;{
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

    /*
    @Override
    public ObservableList<Room> getRoomList() {
        return rooms.asUnmodifiableObservableList();
    }*/

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
