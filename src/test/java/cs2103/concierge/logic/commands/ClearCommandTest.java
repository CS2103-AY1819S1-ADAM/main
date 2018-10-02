package cs2103.concierge.logic.commands;

import static cs2103.concierge.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import cs2103.concierge.logic.CommandHistory;
import cs2103.concierge.model.AddressBook;
import cs2103.concierge.model.Model;
import cs2103.concierge.model.ModelManager;
import cs2103.concierge.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitAddressBook();

        CommandTestUtil.assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.resetData(new AddressBook());
        expectedModel.commitAddressBook();

        CommandTestUtil.assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
