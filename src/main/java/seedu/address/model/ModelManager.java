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
import seedu.address.model.guest.Guest;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.Booking;
import seedu.address.model.room.booking.BookingPeriod;

/**
 * Represents the in-memory model of Concierge data.
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

        logger.fine("Initializing with Concierge: " + concierge + " and user prefs " + userPrefs);

        versionedConcierge = new VersionedConcierge(concierge);
        filteredGuests = new FilteredList<>(versionedConcierge.getGuestList());
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
    public boolean hasGuest(Guest guest) {
        requireNonNull(guest);
        return versionedConcierge.hasGuest(guest);
    }

    @Override
    public void deleteGuest(Guest target) {
        versionedConcierge.removeGuest(target);
        indicateConciergeChanged();
    }

    @Override
    public void addGuest(Guest guest) {
        versionedConcierge.addGuest(guest);
        updateFilteredGuestList(PREDICATE_SHOW_ALL_GUESTS);
        indicateConciergeChanged();
    }

    @Override
    public void updateGuest(Guest target, Guest editedGuest) {
        requireAllNonNull(target, editedGuest);

        versionedConcierge.updateGuest(target, editedGuest);
        indicateConciergeChanged();
    }

    //=========== Filtered Guest List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Guest} backed by the internal list of
     * {@code versionedConcierge}
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
    public void addBooking(RoomNumber roomNumber, Booking booking) {
        versionedConcierge.addBooking(roomNumber, booking);
        updateFilteredRoomList(PREDICATE_SHOW_ALL_ROOMS);
        indicateConciergeChanged();
    }

    @Override
    public void checkInRoom(RoomNumber roomNumber) {
        versionedConcierge.checkInRoom(roomNumber);
        updateFilteredRoomList(PREDICATE_SHOW_ALL_ROOMS);
        indicateConciergeChanged();
    }

    @Override
    public void checkoutRoom(RoomNumber roomNumber) {
        versionedConcierge.checkoutRoom(roomNumber);
        updateFilteredRoomList(PREDICATE_SHOW_ALL_ROOMS);
        indicateConciergeChanged();
    }

    /**
     * Checks out a room's booking using its room number and the specified booking period
     */
    @Override
    public void checkoutRoom(RoomNumber roomNumber, BookingPeriod bookingPeriod) {
        versionedConcierge.checkoutRoom(roomNumber, bookingPeriod);
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
