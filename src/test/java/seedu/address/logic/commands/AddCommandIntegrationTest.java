package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.guest.Guest;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.BookingPeriod;
import seedu.address.testutil.GuestBuilder;
import seedu.address.testutil.TypicalBookingPeriods;
import seedu.address.testutil.TypicalRoomNumbers;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newGuest_success() {
        Guest validGuest = new GuestBuilder().build();
        RoomNumber validRoomNumber = TypicalRoomNumbers.ROOM_NUMBER_002;
        BookingPeriod validBookingPeriod = TypicalBookingPeriods.TODAY_TOMORROW;

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addGuest(validGuest);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new AddCommand(validGuest, validRoomNumber, validBookingPeriod),
                model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validGuest,
                        validRoomNumber, validBookingPeriod),
                expectedModel);
    }

    @Test
    public void execute_duplicateGuest_throwsCommandException() {
        Guest guestInList = model.getAddressBook().getGuestList().get(0);
        RoomNumber validRoomNumber = TypicalRoomNumbers.ROOM_NUMBER_002;
        BookingPeriod validBookingPeriod = TypicalBookingPeriods.TODAY_TOMORROW;

        assertCommandFailure(new AddCommand(guestInList, validRoomNumber, validBookingPeriod),
                model, commandHistory, AddCommand.MESSAGE_DUPLICATE_GUEST);
    }

}
