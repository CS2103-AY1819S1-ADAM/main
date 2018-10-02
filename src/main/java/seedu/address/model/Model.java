package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyGuestList newData);

    /** Returns the GuestList */
    ReadOnlyGuestList getGuestList();

    /**
     * Returns true if a person with the same identity as {@code person}
     * exists in the guest list.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the guest list.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the guest list.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the guest list.
     * The person identity of {@code editedPerson} must not be the same as
     * another existing person in the guest list.
     */
    void updatePerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns true if the model has previous guest list states to restore.
     */
    boolean canUndoGuestList();

    /**
     * Returns true if the model has undone guest list states to restore.
     */
    boolean canRedoGuestList();

    /**
     * Restores the model's guest list to its previous state.
     */
    void undoGuestList();

    /**
     * Restores the model's guest list to its previously undone state.
     */
    void redoGuestList();

    /**
     * Saves the current guest list state for undo/redo.
     */
    void commitGuestList();
}
