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
import seedu.address.commons.events.model.GuestListChangedEvent;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedGuestList versionedGuestList;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given guestList and userPrefs.
     */
    public ModelManager(ReadOnlyGuestList guestList, UserPrefs userPrefs) {
        super();
        requireAllNonNull(guestList, userPrefs);

        logger.fine("Initializing with address book: " + guestList + " and user prefs " + userPrefs);

        versionedGuestList = new VersionedGuestList(guestList);
        filteredPersons = new FilteredList<>(versionedGuestList.getPersonList());
    }

    public ModelManager() {
        this(new GuestList(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyGuestList newData) {
        versionedGuestList.resetData(newData);
        indicateGuestListChanged();
    }

    @Override
    public ReadOnlyGuestList getGuestList() {
        return versionedGuestList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateGuestListChanged() {
        raise(new GuestListChangedEvent(versionedGuestList));
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedGuestList.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        versionedGuestList.removePerson(target);
        indicateGuestListChanged();
    }

    @Override
    public void addPerson(Person person) {
        versionedGuestList.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateGuestListChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedGuestList.updatePerson(target, editedPerson);
        indicateGuestListChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedGuestList}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoGuestList() {
        return versionedGuestList.canUndo();
    }

    @Override
    public boolean canRedoGuestList() {
        return versionedGuestList.canRedo();
    }

    @Override
    public void undoGuestList() {
        versionedGuestList.undo();
        indicateGuestListChanged();
    }

    @Override
    public void redoGuestList() {
        versionedGuestList.redo();
        indicateGuestListChanged();
    }

    @Override
    public void commitGuestList() {
        versionedGuestList.commit();
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
        return versionedGuestList.equals(other.versionedGuestList)
                && filteredPersons.equals(other.filteredPersons);
    }

}
