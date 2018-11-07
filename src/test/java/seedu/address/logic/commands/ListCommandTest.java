package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.FLAG_CHECKED_IN_GUEST;
import static seedu.address.logic.parser.CliSyntax.FLAG_GUEST;
import static seedu.address.logic.parser.CliSyntax.FLAG_ROOM;
import static seedu.address.testutil.TypicalConcierge.getTypicalConciergeClean;

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
        model = new ModelManager(getTypicalConciergeClean(), new UserPrefs());
        expectedModel = new ModelManager(model.getConcierge(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameRoomList() {
        expectedModel.setDisplayedListFlag(FLAG_ROOM);
        assertCommandSuccess(new ListCommand(FLAG_ROOM),
                model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_showsSameGuestList() {
        expectedModel.setDisplayedListFlag(FLAG_GUEST);
        assertCommandSuccess(new ListCommand(FLAG_GUEST),
                model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
    @Test
    public void execute_listIsNotFiltered_showsSameCheckedInGuestList() {
        expectedModel.setDisplayedListFlag(FLAG_CHECKED_IN_GUEST);
        assertCommandSuccess(new ListCommand(FLAG_CHECKED_IN_GUEST),
            model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
