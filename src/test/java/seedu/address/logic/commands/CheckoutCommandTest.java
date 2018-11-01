package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.BookingPeriod;
import seedu.address.testutil.TypicalBookingPeriods;
import seedu.address.testutil.TypicalConcierge;
import seedu.address.testutil.TypicalRoomNumbers;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code CheckoutCommand}.
 */
public class CheckoutCommandTest {

    private Model model = new ModelManager(TypicalConcierge.getTypicalConcierge(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validCheckoutExpiredBookingLastweekYesterday_success() {
        RoomNumber roomNumberToCheckout = TypicalRoomNumbers.ROOM_NUMBER_001;
        CheckoutCommand checkoutCommand = new CheckoutCommand(roomNumberToCheckout);

        String expectedMessage = String.format(CheckoutCommand.MESSAGE_CHECKOUT_ROOM_SUCCESS, roomNumberToCheckout);

        Model expectedModel = new ModelManager(model.getConcierge(), new UserPrefs());
        expectedModel.checkoutRoom(roomNumberToCheckout);
        expectedModel.commitConcierge();

        assertCommandSuccess(checkoutCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validCheckoutBookingNotCheckedIn_success() {
        RoomNumber roomNumberToCheckout = TypicalRoomNumbers.ROOM_NUMBER_011;
        CheckoutCommand checkoutCommand = new CheckoutCommand(roomNumberToCheckout);

        String expectedMessage = String.format(CheckoutCommand.MESSAGE_CHECKOUT_ROOM_SUCCESS, roomNumberToCheckout);

        Model expectedModel = new ModelManager(model.getConcierge(), new UserPrefs());
        expectedModel.checkoutRoom(roomNumberToCheckout);
        expectedModel.commitConcierge();

        assertCommandSuccess(checkoutCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validCheckoutBookingCheckedIn_success() {
        RoomNumber roomNumberToCheckout = TypicalRoomNumbers.ROOM_NUMBER_012;
        CheckoutCommand checkoutCommand = new CheckoutCommand(roomNumberToCheckout);

        String expectedMessage = String.format(CheckoutCommand.MESSAGE_CHECKOUT_ROOM_SUCCESS, roomNumberToCheckout);

        Model expectedModel = new ModelManager(model.getConcierge(), new UserPrefs());
        expectedModel.checkoutRoom(roomNumberToCheckout);
        expectedModel.commitConcierge();

        assertCommandSuccess(checkoutCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validCheckoutBookingPeriod_success() {
        RoomNumber roomNumberToCheckout = TypicalRoomNumbers.ROOM_NUMBER_022;
        BookingPeriod bookingPeriodToCheckOut = TypicalBookingPeriods.TOMORROW_NEXTWEEK;
        CheckoutCommand checkoutCommand = new CheckoutCommand(roomNumberToCheckout, bookingPeriodToCheckOut);

        String expectedMessage = String.format(CheckoutCommand.MESSAGE_CHECKOUT_ROOM_SUCCESS, roomNumberToCheckout);

        Model expectedModel = new ModelManager(model.getConcierge(), new UserPrefs());
        expectedModel.checkoutRoom(roomNumberToCheckout, bookingPeriodToCheckOut);
        expectedModel.commitConcierge();

        assertCommandSuccess(checkoutCommand, model, commandHistory, expectedMessage, expectedModel);

    }

    @Test
    public void execute_invalidCheckoutBookingPeriod_throwsCommandException() {
        RoomNumber roomNumberToCheckout = TypicalRoomNumbers.ROOM_NUMBER_010;
        BookingPeriod invalidBookingPeriodToCheckOut = TypicalBookingPeriods.TODAY_TOMORROW;
        CheckoutCommand checkoutCommand = new CheckoutCommand(roomNumberToCheckout, invalidBookingPeriodToCheckOut);

        String expectedMessage = String.format(CheckoutCommand.MESSAGE_BOOKING_NOT_FOUND,
                roomNumberToCheckout, invalidBookingPeriodToCheckOut);

        assertCommandFailure(checkoutCommand, model, commandHistory, expectedMessage);
    }


    @Test
    public void execute_invalidCheckoutNoBooking_throwsCommandException() {
        RoomNumber roomNumberToCheckout = TypicalRoomNumbers.ROOM_NUMBER_031;
        CheckoutCommand checkoutCommand = new CheckoutCommand(roomNumberToCheckout);

        String expectedMessage = String.format(CheckoutCommand.MESSAGE_NO_ROOM_BOOKING, roomNumberToCheckout);

        assertCommandFailure(checkoutCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void executeUndoRedo_validCheckout_success() throws Exception {
        RoomNumber roomNumberToCheckout = TypicalRoomNumbers.ROOM_NUMBER_022;
        BookingPeriod bookingPeriodToCheckOut = TypicalBookingPeriods.TOMORROW_NEXTWEEK;
        CheckoutCommand checkoutCommand = new CheckoutCommand(roomNumberToCheckout, bookingPeriodToCheckOut);

        Model expectedModel = new ModelManager(model.getConcierge(), new UserPrefs());
        expectedModel.checkoutRoom(roomNumberToCheckout, bookingPeriodToCheckOut);
        expectedModel.commitConcierge();

        // checkout -> room booking checked out
        checkoutCommand.execute(model, commandHistory);

        // undo -> reverts concierge back to previous state and filtered room to show all rooms
        expectedModel.undoConcierge();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same room booking checked out
        expectedModel.redoConcierge();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidCheckout_failure() throws Exception {
        RoomNumber roomNumberToCheckout = TypicalRoomNumbers.ROOM_NUMBER_010;
        BookingPeriod invalidBookingPeriodToCheckOut = TypicalBookingPeriods.TODAY_TOMORROW;
        CheckoutCommand checkoutCommand = new CheckoutCommand(roomNumberToCheckout, invalidBookingPeriodToCheckOut);

        String expectedMessage = String.format(CheckoutCommand.MESSAGE_BOOKING_NOT_FOUND,
            roomNumberToCheckout, invalidBookingPeriodToCheckOut);

        assertCommandFailure(checkoutCommand, model, commandHistory, expectedMessage);

        // single Concierge state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        CheckoutCommand checkoutFirstCommand = new CheckoutCommand(TypicalRoomNumbers.ROOM_NUMBER_001);
        CheckoutCommand checkoutSecondCommand = new CheckoutCommand(TypicalRoomNumbers.ROOM_NUMBER_002);

        // same object -> returns true
        assertTrue(checkoutFirstCommand.equals(checkoutFirstCommand));

        // same values -> returns true
        CheckoutCommand checkoutFirstCommandCopy = new CheckoutCommand(TypicalRoomNumbers.ROOM_NUMBER_001);
        assertTrue(checkoutFirstCommand.equals(checkoutFirstCommandCopy));

        // different types -> returns false
        assertFalse(checkoutFirstCommand.equals(1));

        // null -> returns false
        assertFalse(checkoutFirstCommand.equals(null));

        // different room number -> returns false
        assertFalse(checkoutFirstCommand.equals(checkoutSecondCommand));
    }
}
