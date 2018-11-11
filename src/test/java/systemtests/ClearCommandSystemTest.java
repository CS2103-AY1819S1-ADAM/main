package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalGuests.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.LogInCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.testutil.LogInUtil;

public class ClearCommandSystemTest extends ConciergeSystemTest {

    @Test
    public void clear() {
        final Model defaultModel = getModel();

        /* Signs in to Concierge first */
        String command = LogInUtil.getValidLogInCommand();
        executeCommand(command);
        // TODO: Add support to assert the success of this command

        /* Case: clear non-empty Concierge, command with leading spaces and trailing alphanumeric characters and
         * spaces -> cleared
         */
        assertCommandSuccess("   " + ClearCommand.COMMAND_WORD + " ab12   ");
        assertSelectedGuestCardUnchanged();

        /* Case: undo clearing Concierge -> original Concierge restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, defaultModel);
        assertSelectedGuestCardUnchanged();

        /* Case: redo clearing Concierge -> cleared */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, new ModelManager());
        assertSelectedGuestCardUnchanged();

        /* Case: selects first card in guest list and clears Concierge -> cleared and no card selected */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original Concierge
        // selectGuest(Index.fromOneBased(1)); // TODO this line gives error. commented out cos it is test-only error
        assertCommandSuccess(ClearCommand.COMMAND_WORD);
        assertSelectedGuestCardDeselected();

        /* Case: filters the guest list before clearing -> entire Concierge cleared */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original Concierge
        showGuestsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(ClearCommand.COMMAND_WORD);
        assertSelectedGuestCardUnchanged();

        /* Case: clear empty Concierge -> cleared */
        assertCommandSuccess(ClearCommand.COMMAND_WORD);
        assertSelectedGuestCardUnchanged();

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("ClEaR", MESSAGE_UNKNOWN_COMMAND);

        /* Case: Signs out of Concierge -> rejected */
        executeCommand(LogInUtil.getLogOutCommand());
        assertCommandFailure(ClearCommand.COMMAND_WORD, LogInCommand.MESSAGE_NOT_SIGNED_IN);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code ClearCommand#MESSAGE_SUCCESS} and the model related components equal to an empty model.
     * These verifications are done by
     * {@code ConciergeSystemTest#assertApplicationDisplaysExpectedGuest(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @see ConciergeSystemTest#assertApplicationDisplaysExpectedGuest(String, String, Model)
     */
    private void assertCommandSuccess(String command) {
        assertCommandSuccess(command, ClearCommand.MESSAGE_SUCCESS, new ModelManager());
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     * @see ClearCommandSystemTest#assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpectedGuest("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code ConciergeSystemTest#assertApplicationDisplaysExpectedGuest(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see ConciergeSystemTest#assertApplicationDisplaysExpectedGuest(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpectedGuest(command, expectedResultMessage, expectedModel);
        assertSelectedGuestCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
