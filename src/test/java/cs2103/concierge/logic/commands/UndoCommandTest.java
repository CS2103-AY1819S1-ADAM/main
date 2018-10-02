package cs2103.concierge.logic.commands;

import static cs2103.concierge.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import cs2103.concierge.logic.CommandHistory;
import cs2103.concierge.model.Model;
import cs2103.concierge.model.ModelManager;
import cs2103.concierge.model.UserPrefs;

public class UndoCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of models' undo/redo history
        CommandTestUtil.deleteFirstPerson(model);
        CommandTestUtil.deleteFirstPerson(model);

        CommandTestUtil.deleteFirstPerson(expectedModel);
        CommandTestUtil.deleteFirstPerson(expectedModel);
    }

    @Test
    public void execute() {
        // multiple undoable states in model
        expectedModel.undoAddressBook();
        CommandTestUtil.assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable state in model
        expectedModel.undoAddressBook();
        CommandTestUtil.assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        CommandTestUtil.assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
    }
}
