package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showGuestAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GUEST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_GUEST;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.guest.Guest;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Guest guestToDelete = model.getFilteredGuestList().get(INDEX_FIRST_GUEST.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_GUEST);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_GUEST_SUCCESS, guestToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteGuest(guestToDelete);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGuestList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_GUEST_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showGuestAtIndex(model, INDEX_FIRST_GUEST);

        Guest guestToDelete = model.getFilteredGuestList().get(INDEX_FIRST_GUEST.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_GUEST);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_GUEST_SUCCESS, guestToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteGuest(guestToDelete);
        expectedModel.commitAddressBook();
        showNoGuest(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showGuestAtIndex(model, INDEX_FIRST_GUEST);

        Index outOfBoundIndex = INDEX_SECOND_GUEST;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getGuestList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_GUEST_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Guest guestToDelete = model.getFilteredGuestList().get(INDEX_FIRST_GUEST.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_GUEST);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteGuest(guestToDelete);
        expectedModel.commitAddressBook();

        // delete -> first guest deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered guest list to show all guests
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first guest deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGuestList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_GUEST_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Guest} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted guest in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the guest object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameGuestDeleted() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_GUEST);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showGuestAtIndex(model, INDEX_SECOND_GUEST);
        Guest guestToDelete = model.getFilteredGuestList().get(INDEX_FIRST_GUEST.getZeroBased());
        expectedModel.deleteGuest(guestToDelete);
        expectedModel.commitAddressBook();

        // delete -> deletes second guest in unfiltered guest list / first guest in filtered guest list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered guest list to show all guests
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(guestToDelete, model.getFilteredGuestList().get(INDEX_FIRST_GUEST.getZeroBased()));
        // redo -> deletes same second guest in unfiltered guest list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_GUEST);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_GUEST);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_GUEST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different guest -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoGuest(Model model) {
        model.updateFilteredGuestList(p -> false);

        assertTrue(model.getFilteredGuestList().isEmpty());
    }
}
