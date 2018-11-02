package seedu.address.logic.commands;

import static seedu.address.logic.commands.AddCommand.MESSAGE_OVERLAPPING_BOOKING;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalConcierge.getTypicalConciergeClean;

import org.junit.Before;
import org.junit.Test;

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
import seedu.address.testutil.TypicalGuests;
import seedu.address.testutil.TypicalRoomNumbers;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalConciergeClean(), new UserPrefs());
    }

    @Test
    public void execute_newBooking_success() {
        Guest validGuest = new GuestBuilder().withName(CommandTestUtil.VALID_NAME_BOB).build();
        RoomNumber validRoomNumber = TypicalRoomNumbers.ROOM_NUMBER_002;
        BookingPeriod validBookingPeriod = TypicalBookingPeriods.TODAY_TOMORROW;
        Booking validBooking = new Booking(validGuest, validBookingPeriod);

        String expectedMessage =
            String.format(AddCommand.MESSAGE_SUCCESS, validGuest, validRoomNumber, validBookingPeriod);

        Model expectedModel = new ModelManager(model.getConcierge(), new UserPrefs());
        expectedModel.addBooking(validRoomNumber, validBooking);
        expectedModel.commitConcierge();

        assertCommandSuccess(new AddCommand(validGuest, validRoomNumber, validBookingPeriod),
                model, commandHistory,
                expectedMessage,
                expectedModel);
    }

    @Test
    public void execute_overlappingBooking_throwsCommandException() {
        Guest validGuest = TypicalGuests.ALICE;
        RoomNumber validRoomNumber = TypicalRoomNumbers.ROOM_NUMBER_002;
        BookingPeriod validBookingPeriod = TypicalBookingPeriods.TODAY_TOMORROW;
        Booking validBooking = new Booking(validGuest, validBookingPeriod);

        String expectedMessage = String.format(MESSAGE_OVERLAPPING_BOOKING, validRoomNumber);

        // add first time -> valid
        model.addBooking(validRoomNumber, validBooking);

        // add second time with same booking period -> throw overlapping booking exception
        assertCommandFailure(new AddCommand(validGuest, validRoomNumber, validBookingPeriod),
                model, commandHistory,
                expectedMessage);
    }

}
