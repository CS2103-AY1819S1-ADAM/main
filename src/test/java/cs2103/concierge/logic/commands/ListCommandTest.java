package cs2103.concierge.logic.commands;

import static cs2103.concierge.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static cs2103.concierge.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import cs2103.concierge.logic.CommandHistory;
import cs2103.concierge.model.Model;
import cs2103.concierge.model.ModelManager;
import cs2103.concierge.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        CommandTestUtil.assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        CommandTestUtil.showPersonAtIndex(model, INDEX_FIRST_PERSON);
        CommandTestUtil.assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
