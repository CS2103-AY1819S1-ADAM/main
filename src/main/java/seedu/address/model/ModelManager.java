package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.login.PasswordHashList.getEmptyPasswordHashList;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.ConciergeChangedEvent;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.expenses.Expense;
import seedu.address.model.guest.Guest;
import seedu.address.model.login.InvalidLogInException;
import seedu.address.model.login.InvalidLogOutException;
import seedu.address.model.login.LogInManager;
import seedu.address.model.login.PasswordHashList;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.Booking;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of Concierge data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedConcierge versionedConcierge;
    private final FilteredList<Guest> filteredGuests;
    private final FilteredList<Room> filteredRooms;
    private final FilteredList<Guest> filteredCheckedInGuests;
    private final LogInManager logInManager;
    private Prefix displayedListFlag;

    /**
     * Initializes a ModelManager with the given concierge and userPrefs.
     * This method remains to support existing tests which do not require the
     * LogInHelper module.
     */
    public ModelManager(ReadOnlyConcierge concierge, UserPrefs userPrefs) {
        this(concierge, userPrefs, getEmptyPasswordHashList());
    }

    /**
     * Initializes a ModelManager with the given {@code concierge},
     * {@code userPrefs} and {@code passwordRef}.
     */
    public ModelManager(ReadOnlyConcierge concierge, UserPrefs userPrefs,
                        PasswordHashList passwordRef) {
        super();
        requireAllNonNull(concierge, userPrefs);

        logger.fine("Initializing with Concierge: " + concierge
                + " and user prefs " + userPrefs
                + " and password list " + passwordRef);

        versionedConcierge = new VersionedConcierge(concierge);
        filteredGuests = new FilteredList<>(versionedConcierge.getGuestList());
        filteredRooms = new FilteredList<>(versionedConcierge.getRoomList());
        filteredCheckedInGuests = new FilteredList<>(versionedConcierge.getCheckedInGuestList());
        logInManager = new LogInManager(passwordRef);
        displayedListFlag = CliSyntax.FLAG_ROOM;
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

    @Override
    public Menu getMenu() {
        return versionedConcierge.getMenu();
    }

    /** Raises an event to indicate the model has changed */
    private void indicateConciergeChanged() {
        raise(new ConciergeChangedEvent(versionedConcierge));
    }

    @Override
    public boolean isSignedIn() {
        return logInManager.isSignedIn();
    }

    @Override
    public Optional<String> getUsername() {
        return logInManager.getUsername();
    }

    @Override
    public void signIn(String userName, String hashedPassword) throws InvalidLogInException {
        logInManager.signIn(userName, hashedPassword);
    }

    @Override
    public void signOut() throws InvalidLogOutException {
        logInManager.signOut();
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
        indicateConciergeChanged();
    }

    @Override
    public void updateGuest(Guest target, Guest editedGuest) {
        requireAllNonNull(target, editedGuest);

        versionedConcierge.updateGuest(target, editedGuest);
        indicateConciergeChanged();
    }

    //=========== Displayed List Getter and Setter =============================================================

    @Override
    public Prefix getDisplayedListFlag() {
        return displayedListFlag;
    }

    @Override
    public void setDisplayedListFlag(Prefix flag) {
        displayedListFlag = flag;
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

    /**
     * Returns an unmodifiable view of the list of checked-in {@code Guest} backed by the internal list of
     * {@code versionedConcierge}
     */
    @Override
    public ObservableList<Guest> getFilteredCheckedInGuestList() {
        return FXCollections.unmodifiableObservableList(filteredCheckedInGuests);
    }

    @Override
    public void updateFilteredCheckedInGuestList(Predicate<Guest> predicate) {
        requireNonNull(predicate);
        filteredCheckedInGuests.setPredicate(predicate);
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
    public void addRoomTags(RoomNumber roomNumber, Tag... tags) {
        versionedConcierge.addRoomTags(roomNumber, tags);
        indicateConciergeChanged();
    }

    @Override
    public void reassignRoom(RoomNumber roomNumber, LocalDate startDate, RoomNumber newRoomNumber) {
        versionedConcierge.reassignRoom(roomNumber, startDate, newRoomNumber);
        indicateConciergeChanged();
    }

    @Override
    public void addBooking(RoomNumber roomNumber, Booking booking) {
        versionedConcierge.addBooking(roomNumber, booking);
        indicateConciergeChanged();
    }

    @Override
    public void checkInRoom(RoomNumber roomNumber) {
        versionedConcierge.checkInRoom(roomNumber);
        indicateConciergeChanged();
    }

    @Override
    public void checkoutRoom(RoomNumber roomNumber) {
        versionedConcierge.checkoutRoom(roomNumber);
        indicateConciergeChanged();
    }

    @Override
    public void checkoutRoom(RoomNumber roomNumber, LocalDate startDate) {
        versionedConcierge.checkoutRoom(roomNumber, startDate);
        indicateConciergeChanged();
    }

    // ========= Expenses =================================================

    @Override
    public void addExpense(RoomNumber roomNumber, Expense expense) {
        versionedConcierge.addExpense(roomNumber, expense);
        indicateConciergeChanged();
    }

    //=========== Undo/Redo ================================================

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
                && filteredGuests.equals(other.filteredGuests)
                && filteredCheckedInGuests.equals(other.filteredCheckedInGuests)
                && filteredRooms.equals(other.filteredRooms)
                && displayedListFlag.equals(other.displayedListFlag);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("displayed list flag: ")
            .append(displayedListFlag)
            .append("\n");
        return sb.toString();
    }

}
