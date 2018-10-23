package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalConcierge.getTypicalConcierge;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalConcierge(), new UserPrefs());
        expectedModel = new ModelManager(model.getConcierge(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameRoomList() {
        assertCommandSuccess(new ListCommand("list -r ".trim().split("\\s+")),
                model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_showsSameGuestList() {
        assertCommandSuccess(new ListCommand("list -g ".trim().split("\\s+")),
                model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
