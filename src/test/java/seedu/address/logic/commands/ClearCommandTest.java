package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalGuestList;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.GuestList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyGuestList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitGuestList();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyGuestList_success() {
        Model model = new ModelManager(getTypicalGuestList(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalGuestList(), new UserPrefs());
        expectedModel.resetData(new GuestList());
        expectedModel.commitGuestList();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
