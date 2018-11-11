package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CheckInCommand.MESSAGE_BOOKING_ALREADY_CHECKED_IN;
import static seedu.address.logic.commands.CheckInCommand.MESSAGE_INACTIVE_BOOKING_CHECK_IN;
import static seedu.address.logic.commands.CheckInCommand.MESSAGE_NO_BOOKING_CHECK_IN;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROOM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ROOM_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ROOM_DESC_BOB;
import static seedu.address.logic.commands.LogInCommand.MESSAGE_NOT_SIGNED_IN;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ROOMS;
import static seedu.address.testutil.TypicalBookingPeriods.BOOKING_PERIOD_AMY;
import static seedu.address.testutil.TypicalBookingPeriods.TODAY_TOMORROW;
import static seedu.address.testutil.TypicalBookingPeriods.TOMORROW_NEXTWEEK;
import static seedu.address.testutil.TypicalGuests.AMY;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_AMY;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_BOB;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CheckInCommand;
import seedu.address.model.Model;
import seedu.address.model.room.RoomNumber;
import seedu.address.testutil.BookingUtil;
import seedu.address.testutil.LogInUtil;
import seedu.address.testutil.RoomUtil;

// TODO: Add support to assert the success of auxiliary commands (login, logout, add)
public class CheckInCommandSystemTest extends ConciergeSystemTest {

    @Test
    public void checkIn() {

        /* Signs in to Concierge first */
        String command = LogInUtil.getValidLogInCommand();
        executeCommand(command);

        // attempt to checkin but no bookings found in room -> rejected
        command = CheckInCommand.COMMAND_WORD + ROOM_DESC_BOB;
        assertCommandFailure(command,
                String.format(MESSAGE_NO_BOOKING_CHECK_IN, ROOM_NUMBER_BOB));

        // TODO: Add test case for checkin of expired bookings
        // add guest with expired booking to Concierge
        // attempt to checkin expired booking -> rejected

        // add guest with inactive booking to Concierge
        executeCommand(BookingUtil.getAddCommand(AMY, ROOM_NUMBER_AMY, TOMORROW_NEXTWEEK));
        // attempt to checkin inactive booking -> rejected
        command = CheckInCommand.COMMAND_WORD + ROOM_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INACTIVE_BOOKING_CHECK_IN, ROOM_NUMBER_AMY));

        // add guest with active booking to Concierge
        executeCommand(BookingUtil.getAddCommand(AMY, ROOM_NUMBER_AMY, TODAY_TOMORROW));
        assertCommandSuccess(ROOM_NUMBER_AMY);

        // attempt to checkin the same booking twice -> rejected
        command = CheckInCommand.COMMAND_WORD + ROOM_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_BOOKING_ALREADY_CHECKED_IN, ROOM_NUMBER_AMY));

        /* ------------------------------- Perform invalid checkin operations ---------------------------------- */

        /* Case: invalid room number -> rejected */
        command = CheckInCommand.COMMAND_WORD + INVALID_ROOM_DESC;
        assertCommandFailure(command,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckInCommand.MESSAGE_USAGE));

        /* Case: missing room number -> rejected */
        command = CheckInCommand.COMMAND_WORD;
        assertCommandFailure(command,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckInCommand.MESSAGE_USAGE));

        /* ----------------------------------- Verify the need to sign in --------------------------------------- */

        // add guest to Concierge
        executeCommand(BookingUtil.getAddCommand(AMY, ROOM_NUMBER_AMY, BOOKING_PERIOD_AMY));
        // sign out of concierge
        executeCommand(LogInUtil.getLogOutCommand());

        // not signed in -> rejected
        command = RoomUtil.getCheckInCommand(ROOM_NUMBER_AMY);
        assertCommandFailure(command, String.format(MESSAGE_NOT_SIGNED_IN));
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String,
     * RoomNumber)} except that the browser url and selected card remain unchanged.
     * @param roomNumberToCheckIn the room number to checkin
     * @see CheckInCommandSystemTest#assertCommandSuccess(String, RoomNumber)
     */
    private void assertCommandSuccess(RoomNumber roomNumberToCheckIn) {
        String command = RoomUtil.getCheckInCommand(roomNumberToCheckIn);
        assertCommandSuccess(command, roomNumberToCheckIn);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess (RoomNumber} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code CheckInCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the room with room number
     * {@code roomNumberToCheckIn} being updated to values specified.<br>
     * @param roomNumberToCheckIn the room number to checkin
     * @see CheckInCommandSystemTest#assertCommandSuccess(RoomNumber)
     */
    private void assertCommandSuccess(String command, RoomNumber roomNumberToCheckIn) {
        Model expectedModel = getModel();
        expectedModel.checkInRoom(roomNumberToCheckIn);

        assertCommandSuccess(command, expectedModel,
                String.format(CheckInCommand.MESSAGE_CHECKIN_ROOM_SUCCESS, roomNumberToCheckIn));
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see CheckInCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code ConciergeSystemTest#assertApplicationDisplaysExpectedRoom(String, String, Model)}.<br>
     * @see ConciergeSystemTest#assertApplicationDisplaysExpectedRoom(String, String, Model)
     * @see ConciergeSystemTest#assertSelectedRoomCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredRoomList(PREDICATE_SHOW_ALL_ROOMS);
        assertApplicationDisplaysExpectedRoom("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedRoomCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedRoomCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code RoomListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ConciergeSystemTest#assertApplicationDisplaysExpectedRoom(String, String, Model)}.<br>
     * @see ConciergeSystemTest#assertApplicationDisplaysExpectedRoom(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpectedRoom(command, expectedResultMessage, expectedModel);
        assertSelectedRoomCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
