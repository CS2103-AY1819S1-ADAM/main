package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalConcierge.getTypicalConcierge;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Concierge;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyConcierge_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitConcierge();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyConcierge_success() {
        Model model = new ModelManager(getTypicalConcierge(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalConcierge(), new UserPrefs());
        expectedModel.resetData(new Concierge());
        expectedModel.commitConcierge();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
