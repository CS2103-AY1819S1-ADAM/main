package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_GUEST_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ROOM_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.SelectCommand.MESSAGE_SELECT_GUEST_SUCCESS;
import static seedu.address.logic.commands.SelectCommand.MESSAGE_SELECT_ROOM_SUCCESS;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TypicalGuests.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_001;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.testutil.ListUtil;

public class SelectCommandSystemTest extends ConciergeSystemTest {
    @Test
    public void selectArchivedGuest() {
        /* ------------------------ Perform select operations on the shown unfiltered guest list ----------------- */
        executeCommand(ListUtil.getListGuestCommand());
        /* Case: select the first card in the guest list, command with leading spaces and trailing spaces
         * -> selected
         */
        String command = "   " + SelectCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + "   ";
        assertCommandSuccessGuest(command, INDEX_FIRST);

        /* Case: select the last card in the guest list -> selected */
        Index guestCount = getLastIndex(getModel());
        command = SelectCommand.COMMAND_WORD + " " + guestCount.getOneBased();
        assertCommandSuccessGuest(command, guestCount);

        /* Case: undo previous selection -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailureGuest(command, expectedResultMessage);

        /* Case: redo selecting last card in the list -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailureGuest(command, expectedResultMessage);

        /* Case: select the middle card in the guest list -> selected */
        Index middleIndex = getMidIndex(getModel());
        command = SelectCommand.COMMAND_WORD + " " + middleIndex.getOneBased();
        assertCommandSuccessGuest(command, middleIndex);

        /* Case: select the current selected card -> selected */
        assertCommandSuccessGuest(command, middleIndex);

        /* --------------- Perform select operations on the shown filtered guest list ---------------------------- */

        /* Case: filtered guest list, select index within bounds of Concierge but out of bounds of guest list
         * -> rejected
         */
        showGuestsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getConcierge().getGuestList().size();
        assertCommandFailureGuest(
                SelectCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_GUEST_DISPLAYED_INDEX);

        /* Case: filtered guest list, select index within bounds of Concierge and guest list -> selected */
        Index validIndex = Index.fromOneBased(1);
        assertTrue(validIndex.getZeroBased() < getModel().getFilteredGuestList().size());
        command = SelectCommand.COMMAND_WORD + " " + validIndex.getOneBased();
        assertCommandSuccessGuest(command, validIndex);

        /* ------------------ Perform invalid select operations on guest list ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailureGuest(SelectCommand.COMMAND_WORD + " " + 0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailureGuest(SelectCommand.COMMAND_WORD + " " + -1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredGuestList().size() + 1;
        assertCommandFailureGuest(
                SelectCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_GUEST_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailureGuest(SelectCommand.COMMAND_WORD + " abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailureGuest(SelectCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: mixed case command word -> rejected */
        assertCommandFailureGuest("SeLeCt 1", MESSAGE_UNKNOWN_COMMAND);

        /* Case: select from empty Concierge -> rejected */
        deleteAllGuests();
        assertCommandFailureGuest(SelectCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(),
                MESSAGE_INVALID_GUEST_DISPLAYED_INDEX);
    }

    @Test
    public void selectRoom() {
        /* ---------------- Perform select operations on the shown unfiltered room list ----------------- */
        executeCommand(ListUtil.getListRoomCommand());
        /* Case: select the first card in the room list, command with leading spaces and trailing spaces
         * -> selected
         */
        String command = "   " + SelectCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + "   ";
        assertCommandSuccessRoom(command, INDEX_FIRST);

        /* Case: select the last card in the room list -> selected */
        Index roomCount = getLastIndex(getModel());
        command = SelectCommand.COMMAND_WORD + " " + roomCount.getOneBased();
        assertCommandSuccessRoom(command, roomCount);

        /* Case: undo previous selection -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailureRoom(command, expectedResultMessage);

        /* Case: redo selecting last card in the list -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailureRoom(command, expectedResultMessage);

        /* Case: select the middle card in the room list -> selected */
        Index middleIndex = getMidIndex(getModel());
        command = SelectCommand.COMMAND_WORD + " " + middleIndex.getOneBased();
        assertCommandSuccessRoom(command, middleIndex);

        /* Case: select the current selected card -> selected */
        assertCommandSuccessRoom(command, middleIndex);

        /* --------------- Perform select operations on the shown filtered room list ---------------------------- */

        /* Case: filtered room list, select index within bounds of Concierge but out of bounds of room list
         * -> rejected
         */
        showRoomWithKeywords(ROOM_NUMBER_001.toString(), null, null, null, null, null, null);
        int invalidIndex = getModel().getConcierge().getGuestList().size();
        assertCommandFailureRoom(SelectCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_ROOM_DISPLAYED_INDEX);

        /* Case: filtered room list, select index within bounds of Concierge and room list -> selected */
        Index validIndex = Index.fromOneBased(1);
        assertTrue(validIndex.getZeroBased() < getModel().getFilteredRoomList().size());
        command = SelectCommand.COMMAND_WORD + " " + validIndex.getOneBased();
        assertCommandSuccessRoom(command, validIndex);

        /* ------------------ Perform invalid select operations on room list ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailureRoom(SelectCommand.COMMAND_WORD + " " + 0,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailureRoom(SelectCommand.COMMAND_WORD + " " + -1,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredGuestList().size() + 1;
        assertCommandFailureRoom(SelectCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_ROOM_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailureRoom(SelectCommand.COMMAND_WORD + " abc",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailureRoom(SelectCommand.COMMAND_WORD + " 1 abc",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: mixed case command word -> rejected */
        assertCommandFailureRoom("SeLeCt 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing select command with the
     * {@code expectedSelectedCardIndex} of the selected guest.<br>
     * 4. {@code Storage} and {@code GuestListPanel} remain unchanged.<br>
     * 5. Selected card is at {@code expectedSelectedCardIndex} and the browser url is updated accordingly.<br>
     * 6. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ConciergeSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ConciergeSystemTest#assertApplicationDisplaysExpectedGuest(String, String, Model)
     * @see ConciergeSystemTest#assertSelectedRoomCardChanged(Index)
     */
    private void assertCommandSuccessGuest(String command, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(
            MESSAGE_SELECT_GUEST_SUCCESS, expectedSelectedCardIndex.getOneBased());
        int preExecutionSelectedCardIndex = getGuestListPanel().getSelectedCardIndex();

        executeCommand(command);
        assertApplicationDisplaysExpectedGuest("", expectedResultMessage, expectedModel);

        if (preExecutionSelectedCardIndex == expectedSelectedCardIndex.getZeroBased()) {
            assertSelectedGuestCardUnchanged();
        } else {
            assertSelectedGuestCardChanged(expectedSelectedCardIndex);
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing select command with the
     * {@code expectedSelectedCardIndex} of the selected guest.<br>
     * 4. {@code Storage} and {@code GuestListPanel} remain unchanged.<br>
     * 5. Selected card is at {@code expectedSelectedCardIndex} and the browser url is updated accordingly.<br>
     * 6. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ConciergeSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ConciergeSystemTest#assertApplicationDisplaysExpectedRoom(String, String, Model)
     * @see ConciergeSystemTest#assertSelectedRoomCardChanged(Index)
     */
    private void assertCommandSuccessRoom(String command, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(
            MESSAGE_SELECT_ROOM_SUCCESS, expectedSelectedCardIndex.getOneBased());
        int preExecutionSelectedCardIndex = getRoomListPanel().getSelectedCardIndex();

        executeCommand(command);
        assertApplicationDisplaysExpectedRoom("", expectedResultMessage, expectedModel);

        if (preExecutionSelectedCardIndex == expectedSelectedCardIndex.getZeroBased()) {
            assertSelectedRoomCardUnchanged();
        } else {
            assertSelectedRoomCardChanged(expectedSelectedCardIndex);
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code GuestListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ConciergeSystemTest#assertApplicationDisplaysExpectedGuest(String, String, Model)}.<br>
     * @see ConciergeSystemTest#assertApplicationDisplaysExpectedGuest(String, String, Model)
     */
    private void assertCommandFailureGuest(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpectedGuest(command, expectedResultMessage, expectedModel);
        assertSelectedGuestCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code GuestListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ConciergeSystemTest#assertApplicationDisplaysExpectedGuest(String, String, Model)}.<br>
     * @see ConciergeSystemTest#assertApplicationDisplaysExpectedRoom(String, String, Model)
     */
    private void assertCommandFailureRoom(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpectedRoom(command, expectedResultMessage, expectedModel);
        assertSelectedRoomCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
