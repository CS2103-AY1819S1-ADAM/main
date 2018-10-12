package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRooms.getTypicalUniqueRoomList;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class CheckoutCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalUniqueRoomList());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validCheckout_success() {
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() {
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
    }

    @Test
    public void equals() {
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
    }
}
