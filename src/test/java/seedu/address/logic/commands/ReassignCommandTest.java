package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBookingPeriods.LASTWEEK_YESTERDAY;
import static seedu.address.testutil.TypicalBookingPeriods.TODAY_TOMORROW;
import static seedu.address.testutil.TypicalBookingPeriods.YESTERDAY_TODAY;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_001;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_002;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_010;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_011;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_012;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_020;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_031;

import java.time.LocalDate;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.room.RoomNumber;
import seedu.address.testutil.TypicalConcierge;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code ReassignCommand}.
 */
public class ReassignCommandTest {

    private Model model = new ModelManager(TypicalConcierge.getTypicalConcierge(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_originalRoomReassign_throwsCommandException() {
        RoomNumber roomNumber = ROOM_NUMBER_011;
        LocalDate startDate = TODAY_TOMORROW.getStartDate();
        RoomNumber newRoomNumber = ROOM_NUMBER_011;
        ReassignCommand reassignCommand = new ReassignCommand(roomNumber, startDate, newRoomNumber);

        String expectedMessage = ReassignCommand.MESSAGE_REASSIGN_TO_ORIGINAL_ROOM;

        assertCommandFailure(reassignCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_nonExistentBookingReassign_throwsCommandException() {
        RoomNumber roomNumber = ROOM_NUMBER_001;
        LocalDate startDate = LocalDate.now().minusYears(10);
        RoomNumber newRoomNumber = ROOM_NUMBER_031;
        ReassignCommand reassignCommand = new ReassignCommand(roomNumber, startDate, newRoomNumber);

        String expectedMessage = String.format(ReassignCommand.MESSAGE_BOOKING_NOT_FOUND, roomNumber,
                ParserUtil.parseDateToString(startDate));

        assertCommandFailure(reassignCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_expiredBookingReassign_throwsCommandException() {
        RoomNumber roomNumber = ROOM_NUMBER_002;
        LocalDate startDate = LASTWEEK_YESTERDAY.getStartDate();
        RoomNumber newRoomNumber = ROOM_NUMBER_031;
        ReassignCommand reassignCommand = new ReassignCommand(roomNumber, startDate, newRoomNumber);

        String expectedMessage = ReassignCommand.MESSAGE_EXPIRED_BOOKING_REASSIGN;

        assertCommandFailure(reassignCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_overlappingBookingReassign_throwsCommandException() {
        RoomNumber roomNumber = ROOM_NUMBER_011;
        LocalDate startDate = TODAY_TOMORROW.getStartDate();
        RoomNumber newRoomNumber = ROOM_NUMBER_020;
        ReassignCommand reassignCommand = new ReassignCommand(roomNumber, startDate, newRoomNumber);

        String expectedMessage = String.format(ReassignCommand.MESSAGE_OVERLAPPING_BOOKING, newRoomNumber);

        assertCommandFailure(reassignCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_newRoomBookingCheckedIn_throwsCommandException() {
        RoomNumber roomNumber = ROOM_NUMBER_010;
        LocalDate startDate = YESTERDAY_TODAY.getStartDate();
        RoomNumber newRoomNumber = ROOM_NUMBER_012;
        ReassignCommand reassignCommand = new ReassignCommand(roomNumber, startDate, newRoomNumber);

        String expectedMessage = ReassignCommand.MESSAGE_BOOKING_STARTS_BEFORE_NEW_BOOKING_CHECKED_IN;

        assertCommandFailure(reassignCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_oldRoomBookingCheckedIn_throwsCommandException() {
        RoomNumber roomNumber = ROOM_NUMBER_012;
        LocalDate startDate = TODAY_TOMORROW.getStartDate();
        RoomNumber newRoomNumber = ROOM_NUMBER_010;
        ReassignCommand reassignCommand = new ReassignCommand(roomNumber, startDate, newRoomNumber);

        String expectedMessage = ReassignCommand.MESSAGE_NEW_BOOKING_STARTS_BEFORE_BOOKING_CHECKED_IN;

        assertCommandFailure(reassignCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_validReassign_success() {
        RoomNumber roomNumber = ROOM_NUMBER_011;
        LocalDate startDate = TODAY_TOMORROW.getStartDate();
        RoomNumber newRoomNumber = ROOM_NUMBER_031;
        ReassignCommand reassignCommand = new ReassignCommand(roomNumber, startDate, newRoomNumber);

        String expectedMessage = String.format(ReassignCommand.MESSAGE_REASSIGN_SUCCESS,
                ParserUtil.parseDateToString(startDate), roomNumber, newRoomNumber);

        Model expectedModel = new ModelManager(model.getConcierge(), new UserPrefs());
        expectedModel.reassignRoom(roomNumber, startDate, newRoomNumber);
        expectedModel.commitConcierge();

        assertCommandSuccess(reassignCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validCheckedInReassign_success() {
        RoomNumber roomNumber = ROOM_NUMBER_012;
        LocalDate startDate = TODAY_TOMORROW.getStartDate();
        RoomNumber newRoomNumber = ROOM_NUMBER_031;
        ReassignCommand reassignCommand = new ReassignCommand(roomNumber, startDate, newRoomNumber);

        String expectedMessage = String.format(ReassignCommand.MESSAGE_REASSIGN_SUCCESS,
                ParserUtil.parseDateToString(startDate), roomNumber, newRoomNumber);

        Model expectedModel = new ModelManager(model.getConcierge(), new UserPrefs());
        expectedModel.reassignRoom(roomNumber, startDate, newRoomNumber);
        expectedModel.commitConcierge();

        assertCommandSuccess(reassignCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeUndoRedo_validReassign_success() throws Exception {
        RoomNumber roomNumber = ROOM_NUMBER_012;
        LocalDate startDate = TODAY_TOMORROW.getStartDate();
        RoomNumber newRoomNumber = ROOM_NUMBER_031;
        ReassignCommand reassignCommand = new ReassignCommand(roomNumber, startDate, newRoomNumber);

        Model expectedModel = new ModelManager(model.getConcierge(), new UserPrefs());
        expectedModel.reassignRoom(roomNumber, startDate, newRoomNumber);
        expectedModel.commitConcierge();

        // reassign -> booking reassigned
        reassignCommand.execute(model, commandHistory);

        // undo -> reverts concierge back to previous state
        expectedModel.undoConcierge();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same booking reassigned
        expectedModel.redoConcierge();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidReassign_failure() {
        RoomNumber roomNumber = ROOM_NUMBER_011;
        LocalDate startDate = TODAY_TOMORROW.getStartDate();
        RoomNumber newRoomNumber = ROOM_NUMBER_020;
        ReassignCommand reassignCommand = new ReassignCommand(roomNumber, startDate, newRoomNumber);

        String expectedMessage = String.format(ReassignCommand.MESSAGE_OVERLAPPING_BOOKING, newRoomNumber);

        assertCommandFailure(reassignCommand, model, commandHistory, expectedMessage);

        // single Concierge state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        RoomNumber firstRoomNumber = ROOM_NUMBER_010;
        RoomNumber secondRoomNumber = ROOM_NUMBER_011;
        LocalDate validStartDate = TODAY_TOMORROW.getStartDate();
        RoomNumber validRoomNumberToReassignTo = ROOM_NUMBER_031;

        ReassignCommand reassignFirstCommand = new ReassignCommand(firstRoomNumber, validStartDate,
                validRoomNumberToReassignTo);
        ReassignCommand reassignSecondCommand = new ReassignCommand(secondRoomNumber, validStartDate,
                validRoomNumberToReassignTo);

        // same object -> returns true
        assertTrue(reassignFirstCommand.equals(reassignFirstCommand));

        // same values -> returns true
        ReassignCommand reassignFirstCommandCopy = new ReassignCommand(firstRoomNumber, validStartDate,
                validRoomNumberToReassignTo);
        assertTrue(reassignFirstCommand.equals(reassignFirstCommandCopy));

        // different types -> returns false
        assertFalse(reassignFirstCommand.equals(1));

        // null -> returns false
        assertFalse(reassignFirstCommand.equals(null));

        // different room number -> returns false
        assertFalse(reassignFirstCommand.equals(reassignSecondCommand));
    }
}
