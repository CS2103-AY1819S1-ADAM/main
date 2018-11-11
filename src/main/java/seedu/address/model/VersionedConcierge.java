package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code Concierge} that keeps track of its own history.
 */
public class VersionedConcierge extends Concierge {

    private final List<ReadOnlyConcierge> conciergeStateList;
    private int currentStatePointer;

    public VersionedConcierge(ReadOnlyConcierge initialState) {
        super(initialState);

        conciergeStateList = new ArrayList<>();
        conciergeStateList.add(new Concierge(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code Concierge} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        conciergeStateList.add(new Concierge(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        conciergeStateList.subList(currentStatePointer + 1, conciergeStateList.size()).clear();
    }

    /**
     * Restores Concierge to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(conciergeStateList.get(currentStatePointer));
    }

    /**
     * Restores Concierge to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(conciergeStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has Concierge states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has Concierge states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < conciergeStateList.size() - 1;
    }

    /**
     * Resets the undo and redo history. {@code undo()} and {@code redo()}
     * will throw the respective {@code NoUndoableStateException} and {@code
     * NoRedoableStateException}.
     */
    public void resetUndoRedoHistory() {
        currentStatePointer = -1;
        commit();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedConcierge)) {
            return false;
        }

        VersionedConcierge otherVersionedConcierge = (VersionedConcierge) other;

        // state check
        return super.equals(otherVersionedConcierge)
                && conciergeStateList.equals(otherVersionedConcierge.conciergeStateList)
                && currentStatePointer == otherVersionedConcierge.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of conciergeState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of conciergeState list, unable to redo.");
        }
    }
}
