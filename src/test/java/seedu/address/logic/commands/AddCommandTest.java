package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.guest.Guest;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.Booking;
import seedu.address.model.room.booking.BookingPeriod;
import seedu.address.testutil.GuestBuilder;
import seedu.address.testutil.TypicalBookingPeriods;
import seedu.address.testutil.TypicalConcierge;
import seedu.address.testutil.TypicalRoomNumbers;

/**
 * Refactored to use actual Model, removed ModelStubs
 */
public class AddCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(TypicalConcierge.getTypicalConcierge(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullGuest_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        RoomNumber validRoomNumber = TypicalRoomNumbers.ROOM_NUMBER_002;
        BookingPeriod validBookingPeriod = TypicalBookingPeriods.TODAY_TOMORROW;

        new AddCommand(null, validRoomNumber, validBookingPeriod);
    }

    @Test
    public void execute_bookingAcceptedByModel_addSuccessful() {
        Guest validGuest = new GuestBuilder().build();
        RoomNumber validRoomNumber = TypicalRoomNumbers.ROOM_NUMBER_002;
        BookingPeriod validBookingPeriod = TypicalBookingPeriods.TODAY_TOMORROW;
        Booking validBooking = new Booking(validGuest, validBookingPeriod);
        AddCommand addCommand = new AddCommand(validGuest, validRoomNumber, validBookingPeriod);

        String expectedMessage =
            String.format(AddCommand.MESSAGE_SUCCESS, validGuest, validRoomNumber, validBookingPeriod);

        Model expectedModel = new ModelManager(model.getConcierge(), new UserPrefs());
        expectedModel.addBooking(validRoomNumber, validBooking);
        expectedModel.commitConcierge();

        assertCommandSuccess(addCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_overlappingBooking_throwsCommandException() {
        Guest validGuest = new GuestBuilder().build();
        RoomNumber validRoomNumber = TypicalRoomNumbers.ROOM_NUMBER_002;
        BookingPeriod validBookingPeriod = TypicalBookingPeriods.TODAY_TOMORROW;
        Booking validBooking = new Booking(validGuest, validBookingPeriod);

        String expectedMessage = String.format(AddCommand.MESSAGE_OVERLAPPING_BOOKING, validRoomNumber);

        // add first time -> valid
        model.addBooking(validRoomNumber, validBooking);

        // add second time with same booking period -> throw overlapping booking exception
        assertCommandFailure(new AddCommand(validGuest, validRoomNumber, validBookingPeriod),
            model, commandHistory,
            expectedMessage);
    }

    @Test
    public void equals() {
        Guest alice = new GuestBuilder().withName("Alice").build();
        Guest bob = new GuestBuilder().withName("Bob").build();
        RoomNumber validRoomNumber = TypicalRoomNumbers.ROOM_NUMBER_002;
        BookingPeriod validBookingPeriod = TypicalBookingPeriods.TODAY_TOMORROW;

        AddCommand addAliceCommand = new AddCommand(alice, validRoomNumber, validBookingPeriod);
        AddCommand addBobCommand = new AddCommand(bob, validRoomNumber, validBookingPeriod);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice, validRoomNumber, validBookingPeriod);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different guest -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));

    }

}
