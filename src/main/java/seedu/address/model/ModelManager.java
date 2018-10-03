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
import seedu.address.model.room.RoomNumber;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedGuestList versionedAddressBook;
    private final FilteredList<Guest> filteredGuests;
    // Dummy variable for now. Delete when implemented.
    private final RoomList roomList;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyGuestList addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedGuestList(addressBook);
        filteredGuests = new FilteredList<>(versionedAddressBook.getListOfGuests());

        // Dummy variable for now. Delete when implemented.
        roomList = new RoomList();
    }

    public ModelManager() {
        this(new GuestList(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyGuestList newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyGuestList getGuestList() {
        return versionedAddressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new GuestListChangedEvent(versionedAddressBook));
    }

    @Override
    public boolean hasGuest(Guest guest) {
        requireNonNull(guest);
        return versionedAddressBook.hasPerson(guest);
    }

    @Override
    public void deleteGuest(Guest target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addGuest(Guest guest) {
        versionedAddressBook.addPerson(guest);
        updateFilteredGuestList(PREDICATE_SHOW_ALL_GUESTS);
        indicateAddressBookChanged();
    }

    @Override
    public void updateGuest(Guest target, Guest editedGuest) {
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
    public ObservableList<Guest> getFilteredGuestList() {
        return FXCollections.unmodifiableObservableList(filteredGuests);
    }

    @Override
    public void updateFilteredGuestList(Predicate<Guest> predicate) {
        requireNonNull(predicate);
        filteredGuests.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoGuestList() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoGuestList() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoGuestList() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoGuestList() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitGuestList() {
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
    public RoomList getRoomList() {
        return roomList;
    }
    @Override
    public void checkoutRoom(RoomNumber roomNumber) {}
    @Override
    public void commitRoomList() {}
}
