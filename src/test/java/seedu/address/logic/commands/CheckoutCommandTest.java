package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.room.RoomNumber;
import seedu.address.testutil.TypicalBookings;
import seedu.address.testutil.TypicalRoomNumbers;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code CheckoutCommand}.
 */
public class CheckoutCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void initialize() {
        model.addBooking(TypicalRoomNumbers.ROOM_NUMBER_001, TypicalBookings.LASTWEEK_YESTERDAY);
        model.addBooking(TypicalRoomNumbers.ROOM_NUMBER_002, TypicalBookings.YESTERDAY_TODAY);
        model.checkinRoom(TypicalRoomNumbers.ROOM_NUMBER_002);
        model.addBooking(TypicalRoomNumbers.ROOM_NUMBER_010, TypicalBookings.TODAY_TOMORROW);
        model.checkinRoom(TypicalRoomNumbers.ROOM_NUMBER_010);
        model.addBooking(TypicalRoomNumbers.ROOM_NUMBER_011, TypicalBookings.TOMORROW_NEXTWEEK);
        model.addBooking(TypicalRoomNumbers.ROOM_NUMBER_012, TypicalBookings.TODAY_TOMORROW);
    }

    @Test
    public void execute_invalidCheckoutExpiredBookingLastweekYesterday_success() {
        RoomNumber roomNumberToCheckout = TypicalRoomNumbers.ROOM_NUMBER_001;
        CheckoutCommand checkoutCommand = new CheckoutCommand(roomNumberToCheckout);

        String expectedMessage = String.format(CheckoutCommand.MESSAGE_CHECKOUT_ROOM_SUCCESS, roomNumberToCheckout);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.checkoutRoom(roomNumberToCheckout);
        expectedModel.commitAddressBook();

        assertCommandSuccess(checkoutCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validCheckoutActiveBookingYesterdayToday_success() {
        RoomNumber roomNumberToCheckout = TypicalRoomNumbers.ROOM_NUMBER_002;
        CheckoutCommand checkoutCommand = new CheckoutCommand(roomNumberToCheckout);

        String expectedMessage = String.format(CheckoutCommand.MESSAGE_CHECKOUT_ROOM_SUCCESS, roomNumberToCheckout);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.checkoutRoom(roomNumberToCheckout);
        expectedModel.commitAddressBook();

        assertCommandSuccess(checkoutCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validCheckoutActiveBookingTodayTomorrow_success() {
        RoomNumber roomNumberToCheckout = TypicalRoomNumbers.ROOM_NUMBER_010;
        CheckoutCommand checkoutCommand = new CheckoutCommand(roomNumberToCheckout);

        String expectedMessage = String.format(CheckoutCommand.MESSAGE_CHECKOUT_ROOM_SUCCESS, roomNumberToCheckout);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.checkoutRoom(roomNumberToCheckout);
        expectedModel.commitAddressBook();

        assertCommandSuccess(checkoutCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidCheckoutUpcomingBooking_throwsCommandException() {
        RoomNumber roomNumberToCheckout = TypicalRoomNumbers.ROOM_NUMBER_011;
        CheckoutCommand checkoutCommand = new CheckoutCommand(roomNumberToCheckout);

        String expectedMessage = String.format(CheckoutCommand.MESSAGE_NO_ACTIVE_OR_EXPIRED_ROOM_BOOKING_CHECKOUT,
            roomNumberToCheckout);

        assertCommandFailure(checkoutCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_invalidCheckoutNoBooking_throwsCommandException() {
        RoomNumber roomNumberToCheckout = TypicalRoomNumbers.ROOM_NUMBER_020;
        CheckoutCommand checkoutCommand = new CheckoutCommand(roomNumberToCheckout);

        String expectedMessage = String.format(CheckoutCommand.MESSAGE_NO_ROOM_BOOKING, roomNumberToCheckout);

        assertCommandFailure(checkoutCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() {
        // to be completed after AddressBook is refactored
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        // to be completed after AddressBook is refactored
    }

    @Test
    public void equals() {
        CheckoutCommand checkoutFirstCommand = new CheckoutCommand(TypicalRoomNumbers.ROOM_NUMBER_001);
        CheckoutCommand checkoutSecondCommand = new CheckoutCommand(TypicalRoomNumbers.ROOM_NUMBER_002);

        // same object -> returns true
        assertTrue(checkoutFirstCommand.equals(checkoutFirstCommand));

        // same values -> returns true
        CheckoutCommand deleteFirstCommandCopy = new CheckoutCommand(TypicalRoomNumbers.ROOM_NUMBER_001);
        assertTrue(checkoutFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(checkoutFirstCommand.equals(1));

        // null -> returns false
        assertFalse(checkoutFirstCommand.equals(null));

        // different room number -> returns false
        assertFalse(checkoutFirstCommand.equals(checkoutSecondCommand));
    }
}
