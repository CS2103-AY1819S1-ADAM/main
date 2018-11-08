package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_001;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_002;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_010;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_012;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_021;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_031;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.room.RoomNumber;
import seedu.address.testutil.TypicalConcierge;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code CheckInCommand}.
 */
public class CheckInCommandTest {

    private Model model = new ModelManager(TypicalConcierge.getTypicalConcierge(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_invalidCheckinExpiredBooking_throwsExpiredBookingException() {
        RoomNumber roomNumberToCheckIn = ROOM_NUMBER_001;
        CheckInCommand checkInCommand = new CheckInCommand(roomNumberToCheckIn);

        String expectedMessage = String.format(CheckInCommand.MESSAGE_EXPIRED_BOOKING_CHECK_IN, roomNumberToCheckIn);

        assertCommandFailure(checkInCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_validCheckInBooking_success() {
        RoomNumber roomNumberToCheckIn = ROOM_NUMBER_010;
        CheckInCommand checkInCommand = new CheckInCommand(roomNumberToCheckIn);

        String expectedMessage = String.format(CheckInCommand.MESSAGE_CHECKIN_ROOM_SUCCESS, roomNumberToCheckIn);

        Model expectedModel = new ModelManager(model.getConcierge(), new UserPrefs());
        expectedModel.checkInRoom(roomNumberToCheckIn);
        expectedModel.commitConcierge();

        assertCommandSuccess(checkInCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidCheckInUpcomingBooking_throwsNoActiveBookingException() {
        RoomNumber roomNumberToCheckIn = ROOM_NUMBER_021;
        CheckInCommand checkInCommand = new CheckInCommand(roomNumberToCheckIn);

        String expectedMessage = String.format(CheckInCommand.MESSAGE_INACTIVE_BOOKING_CHECK_IN, roomNumberToCheckIn);

        assertCommandFailure(checkInCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_noBookingCheckIn_throwsNoActiveBookingException() {
        RoomNumber roomNumberToCheckIn = ROOM_NUMBER_031;
        CheckInCommand checkInCommand = new CheckInCommand(roomNumberToCheckIn);

        String expectedMessage = String.format(CheckInCommand.MESSAGE_NO_BOOKING_CHECK_IN, roomNumberToCheckIn);

        assertCommandFailure(checkInCommand, model, commandHistory, expectedMessage);
    }


    @Test
    public void execute_invalidCheckInOccupiedRoomBooking_throwsOccupiedRoomCheckInException() {
        RoomNumber roomNumberToCheckIn = ROOM_NUMBER_012;
        CheckInCommand checkInCommand = new CheckInCommand(roomNumberToCheckIn);

        String expectedMessage = String.format(CheckInCommand.MESSAGE_BOOKING_ALREADY_CHECKED_IN, roomNumberToCheckIn);

        assertCommandFailure(checkInCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void executeUndoRedo_validCheckIn_success() throws Exception {
        RoomNumber roomNumberToCheckIn = ROOM_NUMBER_010;
        CheckInCommand checkInCommand = new CheckInCommand(roomNumberToCheckIn);

        Model expectedModel = new ModelManager(model.getConcierge(), new UserPrefs());
        expectedModel.checkInRoom(roomNumberToCheckIn);
        expectedModel.commitConcierge();

        // checkIn -> room booking checked in
        checkInCommand.execute(model, commandHistory);

        // undo -> reverts concierge back to previous state and filtered room to show all rooms
        expectedModel.undoConcierge();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same room booking checked out
        expectedModel.redoConcierge();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidCheckIn_failure() throws Exception {
        RoomNumber roomNumberToCheckIn = ROOM_NUMBER_012;
        CheckInCommand checkInCommand = new CheckInCommand(roomNumberToCheckIn);

        String expectedMessage = String.format(CheckInCommand.MESSAGE_BOOKING_ALREADY_CHECKED_IN, roomNumberToCheckIn);

        assertCommandFailure(checkInCommand, model, commandHistory, expectedMessage);

        // single Concierge state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        CheckInCommand checkInFirstCommand = new CheckInCommand(ROOM_NUMBER_001);
        CheckInCommand checkInSecondCommand = new CheckInCommand(ROOM_NUMBER_002);

        // same object -> returns true
        assertTrue(checkInFirstCommand.equals(checkInFirstCommand));

        // same values -> returns true
        CheckInCommand deleteFirstCommandCopy = new CheckInCommand(ROOM_NUMBER_001);
        assertTrue(checkInFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(checkInFirstCommand.equals(1));

        // null -> returns false
        assertFalse(checkInFirstCommand.equals(null));

        // different room number -> returns false
        assertFalse(checkInFirstCommand.equals(checkInSecondCommand));
    }
}
