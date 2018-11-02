package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_END_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_END_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATE_START_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_START_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ROOM_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ROOM_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.testutil.TypicalBookingPeriods.BOOKING_PERIOD_AMY;
import static seedu.address.testutil.TypicalBookingPeriods.BOOKING_PERIOD_BOB;
import static seedu.address.testutil.TypicalBookingPeriods.TOMORROW_NEXTWEEK;
import static seedu.address.testutil.TypicalGuests.AMY;
import static seedu.address.testutil.TypicalGuests.BOB;
import static seedu.address.testutil.TypicalGuests.HOON;
import static seedu.address.testutil.TypicalGuests.IDA;
import static seedu.address.testutil.TypicalGuests.JAKOB;
import static seedu.address.testutil.TypicalGuests.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_050;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_051;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_052;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_AMY;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_BOB;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.guest.Email;
import seedu.address.model.guest.Guest;
import seedu.address.model.guest.Name;
import seedu.address.model.guest.Phone;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.Booking;
import seedu.address.model.room.booking.BookingPeriod;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.GuestUtil;

// TODO Fix and redo these tests for refactored addCommand!
public class AddCommandSystemTest extends ConciergeSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a booking to a non-empty Concierge, command with leading spaces and trailing spaces -> added
         */
        Guest guestToAdd = AMY;
        BookingPeriod bookingPeriodToAdd = BOOKING_PERIOD_AMY;
        RoomNumber roomNumberToAdd = ROOM_NUMBER_AMY;
        Booking bookingToAdd = new Booking(guestToAdd, bookingPeriodToAdd);

        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY
                + "  " + PHONE_DESC_AMY + " " + EMAIL_DESC_AMY + " "
                + "   " + TAG_DESC_FRIEND + " "
                + ROOM_DESC_AMY + " " + DATE_START_DESC_AMY + " " + DATE_END_DESC_AMY + "   ";
        assertCommandSuccess(command, guestToAdd, roomNumberToAdd, bookingPeriodToAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addBooking(roomNumberToAdd, bookingToAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a guest with tags, command with parameters in random order -> added */
        guestToAdd = BOB;
        roomNumberToAdd = ROOM_NUMBER_BOB;
        bookingPeriodToAdd = BOOKING_PERIOD_BOB;
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND
                + DATE_END_DESC_BOB + TAG_DESC_HUSBAND + EMAIL_DESC_BOB + ROOM_DESC_BOB
                + DATE_START_DESC_BOB + PHONE_DESC_BOB + NAME_DESC_BOB + DATE_END_DESC_BOB;
        assertCommandSuccess(command, guestToAdd, roomNumberToAdd, bookingPeriodToAdd);

        /* Case: add a guest, missing tags -> added */
        roomNumberToAdd = ROOM_NUMBER_050;
        bookingPeriodToAdd = TOMORROW_NEXTWEEK;
        assertCommandSuccess(HOON, roomNumberToAdd, bookingPeriodToAdd);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the guest list before adding -> added */
        showGuestsWithName(KEYWORD_MATCHING_MEIER);
        roomNumberToAdd = ROOM_NUMBER_051;
        assertCommandSuccess(IDA, roomNumberToAdd, bookingPeriodToAdd);

        /* ------------------------ Perform add operation while a guest card is selected --------------------------- */

        /* Case: selects first card in the guest list, add a guest -> added, card selection remains unchanged */
        selectGuest(Index.fromOneBased(1));
        roomNumberToAdd = ROOM_NUMBER_052;
        assertCommandSuccess(JAKOB, roomNumberToAdd, bookingPeriodToAdd);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ROOM_DESC_AMY + DATE_START_DESC_AMY + DATE_END_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY
                + ROOM_DESC_AMY + DATE_START_DESC_AMY + DATE_END_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + ROOM_DESC_AMY + DATE_START_DESC_AMY + DATE_END_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + NAME_DESC_AMY + PHONE_DESC_AMY
                + ROOM_DESC_AMY + DATE_START_DESC_AMY + DATE_END_DESC_AMY;
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_AMY
                    + EMAIL_DESC_AMY + ROOM_DESC_AMY
                    + DATE_START_DESC_AMY + DATE_END_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_PHONE_DESC
                + EMAIL_DESC_AMY + ROOM_DESC_AMY + DATE_START_DESC_AMY
                + DATE_END_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + INVALID_EMAIL_DESC + ROOM_DESC_AMY + DATE_START_DESC_AMY
                + DATE_END_DESC_AMY;
        assertCommandFailure(command, Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + INVALID_TAG_DESC + ROOM_DESC_AMY
                + DATE_START_DESC_AMY + DATE_END_DESC_AMY;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code guestToAdd} to the
     * model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code guestToAdd}.<br>
     * 4. {@code Storage} and {@code GuestListPanel} equal to the corresponding components in
     * the current model added with {@code guestToAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ConciergeSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ConciergeSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Guest guestToAdd,
                                      RoomNumber roomNumberToAdd,
                                      BookingPeriod bookingPeriodToAdd) {

        String command = GuestUtil.getAddCommand(guestToAdd, roomNumberToAdd, bookingPeriodToAdd);

        assertCommandSuccess(command, guestToAdd, roomNumberToAdd, bookingPeriodToAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Guest,
     * RoomNumber, BookingPeriod)}. Executes {@code command} instead.
     *
     * @see AddCommandSystemTest#assertCommandSuccess(Guest, RoomNumber, BookingPeriod)
     */
    private void assertCommandSuccess(String command,
                                      Guest guestToAdd,
                                      RoomNumber roomNumber,
                                      BookingPeriod bookingPeriod) {
        Model expectedModel = getModel();

        Booking expectedBooking = new Booking(guestToAdd, bookingPeriod);
        expectedModel.addBooking(roomNumber, expectedBooking);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, guestToAdd, roomNumber, bookingPeriod);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String,
     * Guest, RoomNumber, BookingPeriod)} except asserts that the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code GuestListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     *
     * @see AddCommandSystemTest#assertCommandSuccess(String, Guest, RoomNumber, BookingPeriod)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code GuestListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ConciergeSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ConciergeSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
