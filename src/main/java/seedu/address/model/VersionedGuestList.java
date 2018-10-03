package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code GuestList} that keeps track of its own history.
 */
public class VersionedGuestList extends GuestList {

    private final List<ReadOnlyGuestList> guestListStateList;
    private int currentStatePointer;

    public VersionedGuestList(ReadOnlyGuestList initialState) {
        super(initialState);

        guestListStateList = new ArrayList<>();
        guestListStateList.add(new GuestList(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code GuestList} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        guestListStateList.add(new GuestList(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        guestListStateList.subList(currentStatePointer + 1, guestListStateList.size()).clear();
    }

    /**
     * Restores the guest list to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(guestListStateList.get(currentStatePointer));
    }

    /**
     * Restores the guest list to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(guestListStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has guest list states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has guest list states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < guestListStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedGuestList)) {
            return false;
        }

        VersionedGuestList otherVersionedGuestList = (VersionedGuestList) other;

        // state check
        return super.equals(otherVersionedGuestList)
                && guestListStateList.equals(otherVersionedGuestList.guestListStateList)
                && currentStatePointer == otherVersionedGuestList.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of guestListState list, " +
                    "unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of guestListState list, " +
                    "unable to redo.");
        }
    }
}
