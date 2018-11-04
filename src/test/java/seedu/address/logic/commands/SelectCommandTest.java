package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showGuestAtIndex;
import static seedu.address.testutil.TypicalConcierge.getTypicalConciergeClean;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GUEST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_GUEST;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_GUEST;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class SelectCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalConciergeClean(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalConciergeClean(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        model.setDisplayedListFlag(CliSyntax.FLAG_GUEST);

        Index lastGuestIndex = Index.fromOneBased(model.getFilteredGuestList().size());

        assertExecutionSuccess(INDEX_FIRST_GUEST);
        assertExecutionSuccess(INDEX_THIRD_GUEST);
        assertExecutionSuccess(lastGuestIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        model.setDisplayedListFlag(CliSyntax.FLAG_GUEST);

        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredGuestList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_GUEST_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showGuestAtIndex(model, INDEX_FIRST_GUEST);
        showGuestAtIndex(expectedModel, INDEX_FIRST_GUEST);

        assertExecutionSuccess(INDEX_FIRST_GUEST);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showGuestAtIndex(model, INDEX_FIRST_GUEST);
        showGuestAtIndex(expectedModel, INDEX_FIRST_GUEST);

        Index outOfBoundsIndex = INDEX_SECOND_GUEST;
        // ensures that outOfBoundIndex is still in bounds of Concierge list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getConcierge().getGuestList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_GUEST_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_GUEST);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_GUEST);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_GUEST);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different guest -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCommand selectCommand = new SelectCommand(index);
        String expectedMessage = String.format(SelectCommand.MESSAGE_SELECT_GUEST_SUCCESS, index.getOneBased());

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);

        JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectCommand selectCommand = new SelectCommand(index);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }
}
