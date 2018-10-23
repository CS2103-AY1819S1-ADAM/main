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
import seedu.address.commons.events.model.ConciergeChangedEvent;
import seedu.address.model.person.Guest;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.Booking;

/**
 * Represents the in-memory model of the concierge data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedConcierge versionedConcierge;
    private final FilteredList<Guest> filteredGuests;
    private final FilteredList<Room> filteredRooms;

    /**
     * Initializes a ModelManager with the given concierge and userPrefs.
     */
    public ModelManager(ReadOnlyConcierge concierge, UserPrefs userPrefs) {
        super();
        requireAllNonNull(concierge, userPrefs);

        logger.fine("Initializing with concierge: " + concierge + " and user prefs " + userPrefs);

        versionedConcierge = new VersionedConcierge(concierge);
        filteredGuests = new FilteredList<>(versionedConcierge.getPersonList());
        filteredRooms = new FilteredList<>(versionedConcierge.getRoomList());
    }

    public ModelManager() {
        this(new Concierge(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyConcierge newData) {
        versionedConcierge.resetData(newData);
        indicateConciergeChanged();
    }

    @Override
    public ReadOnlyConcierge getConcierge() {
        return versionedConcierge;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateConciergeChanged() {
        raise(new ConciergeChangedEvent(versionedConcierge));
    }

    @Override
    public boolean hasPerson(Guest guest) {
        requireNonNull(guest);
        return versionedConcierge.hasPerson(guest);
    }

    @Override
    public void deletePerson(Guest target) {
        versionedConcierge.removePerson(target);
        indicateConciergeChanged();
    }

    @Override
    public void addPerson(Guest guest) {
        versionedConcierge.addPerson(guest);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateConciergeChanged();
    }

    @Override
    public void updatePerson(Guest target, Guest editedGuest) {
        requireAllNonNull(target, editedGuest);

        versionedConcierge.updatePerson(target, editedGuest);
        indicateConciergeChanged();
    }

    //=========== Filtered Guest List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Guest} backed by the internal list of
     * {@code versionedConcierge}
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
     * {@code versionedConcierge}
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

    //=========== Room =======================================================

    @Override
    public void checkinRoom(RoomNumber roomNumber) {
        versionedConcierge.checkinRoom(roomNumber);
        updateFilteredRoomList(PREDICATE_SHOW_ALL_ROOMS);
        indicateConciergeChanged();
    }

    @Override
    public void checkoutRoom(RoomNumber roomNumber) {
        versionedConcierge.checkoutRoom(roomNumber);
        updateFilteredRoomList(PREDICATE_SHOW_ALL_ROOMS);
        indicateConciergeChanged();
    }

    @Override
    public boolean isRoomCheckedIn(RoomNumber roomNumber) {
        return versionedConcierge.isRoomCheckedIn(roomNumber);
    }

    public boolean roomHasBooking(RoomNumber roomNumber) {
        return versionedConcierge.roomHasBooking(roomNumber);
    }

    @Override
    public boolean roomHasActiveBooking(RoomNumber roomNumber) {
        return versionedConcierge.roomHasActiveBooking(roomNumber);
    }


    @Override
    public boolean roomHasActiveOrExpiredBooking(RoomNumber roomNumber) {
        return versionedConcierge.roomHasActiveOrExpiredBooking(roomNumber);
    }

    @Override
    public void addBooking(RoomNumber roomNumber, Booking booking) {
        versionedConcierge.addBooking(roomNumber, booking);
        updateFilteredRoomList(PREDICATE_SHOW_ALL_ROOMS);
        indicateConciergeChanged();
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoConcierge() {
        return versionedConcierge.canUndo();
    }

    @Override
    public boolean canRedoConcierge() {
        return versionedConcierge.canRedo();
    }

    @Override
    public void undoConcierge() {
        versionedConcierge.undo();
        indicateConciergeChanged();
    }

    @Override
    public void redoConcierge() {
        versionedConcierge.redo();
        indicateConciergeChanged();
    }

    @Override
    public void commitConcierge() {
        versionedConcierge.commit();
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
        return versionedConcierge.equals(other.versionedConcierge)
                && filteredGuests.equals(other.filteredGuests);
    }

}
