package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.person.Guest;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.Booking;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Guest> filteredGuests;
    private final FilteredList<Room> filteredRooms;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredGuests = new FilteredList<>(versionedAddressBook.getPersonList());
        filteredRooms = new FilteredList<>(versionedAddressBook.getRoomList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    @Override
    public boolean hasPerson(Guest guest) {
        requireNonNull(guest);
        return versionedAddressBook.hasPerson(guest);
    }

    @Override
    public void deletePerson(Guest target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addPerson(Guest guest) {
        versionedAddressBook.addPerson(guest);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Guest target, Guest editedGuest) {
        requireAllNonNull(target, editedGuest);

        versionedAddressBook.updatePerson(target, editedGuest);
        indicateAddressBookChanged();
    }

    //=========== Filtered Guest List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Guest} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Guest> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredGuests);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Guest> predicate) {
        requireNonNull(predicate);
        filteredGuests.setPredicate(predicate);
    }

    //=========== Filtered Room List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Room} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Room> getFilteredRoomList() {
        return FXCollections.unmodifiableObservableList(filteredRooms);
    }

    @Override
    public void updateFilteredRoomList(Predicate<Room> predicate) {
        requireNonNull(predicate);
        filteredRooms.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && filteredGuests.equals(other.filteredGuests);
    }

    @Override
    public void checkinRoom(RoomNumber roomNumber) {
        versionedAddressBook.checkinRoom(roomNumber);
        indicateAddressBookChanged();
    }

    @Override
    public void checkoutRoom(RoomNumber roomNumber) {
        versionedAddressBook.checkoutRoom(roomNumber);
        indicateAddressBookChanged();
    }

    @Override
    public boolean isRoomCheckedIn(RoomNumber roomNumber) {
        return versionedAddressBook.isRoomCheckedIn(roomNumber);
    }

    public boolean roomHasBooking(RoomNumber roomNumber) {
        return versionedAddressBook.roomHasBooking(roomNumber);
    }

    @Override
    public boolean roomHasActiveBooking(RoomNumber roomNumber) {
        return versionedAddressBook.roomHasActiveBooking(roomNumber);
    }


    @Override
    public boolean roomHasActiveOrExpiredBooking(RoomNumber roomNumber) {
        return versionedAddressBook.roomHasActiveOrExpiredBooking(roomNumber);
    }

    @Override
    public void addBooking(RoomNumber roomNumber, Booking booking) {
        versionedAddressBook.addBooking(roomNumber, booking);
        indicateAddressBookChanged();
    }
}
