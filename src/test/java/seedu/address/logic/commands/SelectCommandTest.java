package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCheckedInGuestAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showGuestAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showRoomAtIndex;
import static seedu.address.logic.parser.CliSyntax.FLAG_CHECKED_IN_GUEST;
import static seedu.address.logic.parser.CliSyntax.FLAG_GUEST;
import static seedu.address.logic.parser.CliSyntax.FLAG_ROOM;
import static seedu.address.testutil.TypicalConcierge.getTypicalConcierge;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.Rule;
import org.junit.Test;
import org.opentest4j.TestAbortedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.Prefix;
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

    private Model model = new ModelManager(getTypicalConcierge(), new UserPrefs());
    private Model expectedModel = new ModelManager(model.getConcierge(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredArchivedGuestList_success() {
        Prefix displayedFlag = FLAG_GUEST;

        model.setDisplayedListFlag(displayedFlag);
        expectedModel.setDisplayedListFlag(displayedFlag);

        Index lastGuestIndex = Index.fromOneBased(model.getFilteredGuestList().size());

        assertExecutionSuccess(INDEX_FIRST, displayedFlag);
        assertExecutionSuccess(INDEX_THIRD, displayedFlag);
        assertExecutionSuccess(lastGuestIndex, displayedFlag);
    }

    @Test
    public void execute_validIndexUnfilteredCheckedInGuestList_success() {
        Prefix displayedFlag = FLAG_CHECKED_IN_GUEST;

        model.setDisplayedListFlag(displayedFlag);
        expectedModel.setDisplayedListFlag(displayedFlag);

        Index lastGuestIndex = Index.fromOneBased(model.getFilteredCheckedInGuestList().size());

        assertExecutionSuccess(INDEX_FIRST, displayedFlag);
        assertExecutionSuccess(INDEX_SECOND, displayedFlag);
        assertExecutionSuccess(lastGuestIndex, displayedFlag);
    }

    @Test
    public void execute_validIndexUnfilteredRoomList_success() {
        Prefix displayedFlag = FLAG_ROOM;

        model.setDisplayedListFlag(displayedFlag);
        expectedModel.setDisplayedListFlag(displayedFlag);

        Index lastRoomIndex = Index.fromOneBased(model.getFilteredRoomList().size());

        assertExecutionSuccess(INDEX_FIRST, displayedFlag);
        assertExecutionSuccess(INDEX_SECOND, displayedFlag);
        assertExecutionSuccess(lastRoomIndex, displayedFlag);
    }

    @Test
    public void execute_invalidIndexUnfilteredGuestList_failure() {
        Prefix displayedFlag = FLAG_GUEST;

        model.setDisplayedListFlag(displayedFlag);
        expectedModel.setDisplayedListFlag(displayedFlag);

        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredGuestList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, displayedFlag);
    }

    @Test
    public void execute_invalidIndexUnfilteredCheckedInGuestList_failure() {
        Prefix displayedFlag = FLAG_CHECKED_IN_GUEST;

        model.setDisplayedListFlag(displayedFlag);
        expectedModel.setDisplayedListFlag(displayedFlag);

        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredCheckedInGuestList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, displayedFlag);
    }

    @Test
    public void execute_invalidIndexUnfilteredRoomList_failure() {
        Prefix displayedFlag = FLAG_ROOM;

        model.setDisplayedListFlag(displayedFlag);
        expectedModel.setDisplayedListFlag(displayedFlag);

        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredRoomList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, displayedFlag);
    }

    @Test
    public void execute_validIndexFilteredGuestList_success() {
        Prefix displayedFlag = FLAG_GUEST;

        model.setDisplayedListFlag(displayedFlag);
        expectedModel.setDisplayedListFlag(displayedFlag);

        showGuestAtIndex(model, INDEX_FIRST);
        showGuestAtIndex(expectedModel, INDEX_FIRST);

        assertExecutionSuccess(INDEX_FIRST, displayedFlag);
    }

    @Test
    public void execute_validIndexFilteredCheckedInGuestList_success() {
        Prefix displayedFlag = FLAG_CHECKED_IN_GUEST;

        model.setDisplayedListFlag(displayedFlag);
        expectedModel.setDisplayedListFlag(displayedFlag);

        showCheckedInGuestAtIndex(model, INDEX_FIRST);
        showCheckedInGuestAtIndex(expectedModel, INDEX_FIRST);

        assertExecutionSuccess(INDEX_FIRST, displayedFlag);
    }

    @Test
    public void execute_validIndexFilteredRoomList_success() {
        Prefix displayedFlag = FLAG_ROOM;

        model.setDisplayedListFlag(displayedFlag);
        expectedModel.setDisplayedListFlag(displayedFlag);

        showRoomAtIndex(model, INDEX_FIRST);
        showRoomAtIndex(expectedModel, INDEX_FIRST);

        assertExecutionSuccess(INDEX_FIRST, displayedFlag);
    }

    @Test
    public void execute_invalidIndexFilteredGuestList_failure() {
        Prefix displayedFlag = FLAG_GUEST;

        model.setDisplayedListFlag(displayedFlag);
        expectedModel.setDisplayedListFlag(displayedFlag);

        showGuestAtIndex(model, INDEX_FIRST);
        showGuestAtIndex(expectedModel, INDEX_FIRST);

        Index outOfBoundsIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of Concierge guest list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getConcierge().getGuestList().size());

        assertExecutionFailure(outOfBoundsIndex, displayedFlag);
    }

    @Test
    public void execute_invalidIndexFilteredCheckedInGuestList_failure() {
        Prefix displayedFlag = FLAG_CHECKED_IN_GUEST;

        model.setDisplayedListFlag(displayedFlag);
        expectedModel.setDisplayedListFlag(displayedFlag);

        showCheckedInGuestAtIndex(model, INDEX_FIRST);
        showCheckedInGuestAtIndex(expectedModel, INDEX_FIRST);

        Index outOfBoundsIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of Concierge checked-in guest list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getConcierge().getCheckedInGuestList().size());

        assertExecutionFailure(outOfBoundsIndex, displayedFlag);
    }

    @Test
    public void execute_invalidIndexFilteredRoomList_failure() {
        Prefix displayedFlag = FLAG_ROOM;

        model.setDisplayedListFlag(displayedFlag);
        expectedModel.setDisplayedListFlag(displayedFlag);

        showRoomAtIndex(model, INDEX_FIRST);
        showRoomAtIndex(expectedModel, INDEX_FIRST);

        Index outOfBoundsIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of Concierge room list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getConcierge().getRoomList().size());

        assertExecutionFailure(outOfBoundsIndex, displayedFlag);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST);
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
    private void assertExecutionSuccess(Index index, Prefix displayedListFlag) {
        SelectCommand selectCommand = new SelectCommand(index);

        String expectedMessage = String.format(getSelectionMessageSuccess(displayedListFlag), index.getOneBased());

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);

        JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, Prefix displayedListFlag) {
        SelectCommand selectCommand = new SelectCommand(index);

        String expectedMessage = getSelectionMessageFailure(displayedListFlag);

        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }

    /**
     * Returns the success message that corresponds to the selection category (guest, checked-in guest, or room)
     */
    private String getSelectionMessageSuccess(Prefix displayedListFlag) throws TestAbortedException {
        if (displayedListFlag.equals(FLAG_GUEST)) {
            return SelectCommand.MESSAGE_SELECT_GUEST_SUCCESS;
        } else if (displayedListFlag.equals(FLAG_CHECKED_IN_GUEST)) {
            return SelectCommand.MESSAGE_SELECT_CHECKED_IN_GUEST_SUCCESS;
        } else if (displayedListFlag.equals(FLAG_ROOM)) {
            return SelectCommand.MESSAGE_SELECT_ROOM_SUCCESS;
        } else {
            throw new TestAbortedException("Invalid list flag!");
        }
    }

    /**
     * Returns the failure message that corresponds to the selection category (guest, checked-in guest, or room)
     */
    private String getSelectionMessageFailure(Prefix displayedListFlag) throws TestAbortedException {
        if (displayedListFlag.equals(FLAG_GUEST)) {
            return Messages.MESSAGE_INVALID_GUEST_DISPLAYED_INDEX;
        } else if (displayedListFlag.equals(FLAG_CHECKED_IN_GUEST)) {
            return Messages.MESSAGE_INVALID_CHECKED_IN_GUEST_DISPLAYED_INDEX;
        } else if (displayedListFlag.equals(FLAG_ROOM)) {
            return Messages.MESSAGE_INVALID_ROOM_DISPLAYED_INDEX;
        } else {
            throw new TestAbortedException("Invalid list flag!");
        }
    }
}
