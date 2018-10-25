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
import static seedu.address.logic.commands.CommandTestUtil.ROOM_DESC_001;
import static seedu.address.logic.commands.CommandTestUtil.ROOM_DESC_020;
import static seedu.address.logic.commands.CommandTestUtil.ROOM_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.TypicalBookingPeriods.BOOKING_PERIOD_AMY;
import static seedu.address.testutil.TypicalBookingPeriods.BOOKING_PERIOD_BOB;
import static seedu.address.testutil.TypicalBookingPeriods.TOMORROW_NEXTWEEK;
import static seedu.address.testutil.TypicalGuests.AMY;
import static seedu.address.testutil.TypicalGuests.BOB;
import static seedu.address.testutil.TypicalGuests.HOON;
import static seedu.address.testutil.TypicalGuests.IDA;
import static seedu.address.testutil.TypicalGuests.JAKOB;
import static seedu.address.testutil.TypicalGuests.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_001;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_020;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_023;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_024;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_099;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_AMY;

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
import seedu.address.testutil.BookingBuilder;
import seedu.address.testutil.GuestBuilder;
import seedu.address.testutil.GuestUtil;
import seedu.address.testutil.TypicalBookingPeriods;
import seedu.address.testutil.TypicalRoomNumbers;

public class AddCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a guest without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        Guest guestToAdd = AMY;
        RoomNumber roomNumberToAdd = ROOM_NUMBER_AMY;
        BookingPeriod bookingPeriodToAdd = BOOKING_PERIOD_AMY;

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
        model.addGuest(guestToAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a guest with all fields same as another guest in the
        address book except name and room -> added */
        guestToAdd = new GuestBuilder(AMY).withName(VALID_NAME_BOB).build();
        roomNumberToAdd = TypicalRoomNumbers.ROOM_NUMBER_001;
        bookingPeriodToAdd = TypicalBookingPeriods.BOOKING_PERIOD_AMY;
        command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + ROOM_DESC_001 + DATE_START_DESC_AMY + DATE_END_DESC_AMY;
        assertCommandSuccess(command, guestToAdd, roomNumberToAdd, bookingPeriodToAdd);

        /* Case: add a guest with all fields same as another guest in the address book except phone and email
         * -> added
         */
        guestToAdd = new GuestBuilder(AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        roomNumberToAdd = TypicalRoomNumbers.ROOM_NUMBER_011;
        bookingPeriodToAdd = TypicalBookingPeriods.BOOKING_PERIOD_AMY;

        command = GuestUtil.getAddCommand(guestToAdd, roomNumberToAdd, bookingPeriodToAdd);
        assertCommandSuccess(command, guestToAdd, roomNumberToAdd, bookingPeriodToAdd);

        /* Case: add a guest with tags, command with parameters in random order -> added */
        guestToAdd = BOB;
        roomNumberToAdd = ROOM_NUMBER_020;
        bookingPeriodToAdd = BOOKING_PERIOD_BOB;
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND
                + DATE_END_DESC_BOB + TAG_DESC_HUSBAND + EMAIL_DESC_BOB + ROOM_DESC_020
                + DATE_START_DESC_BOB + PHONE_DESC_BOB + NAME_DESC_BOB + DATE_END_DESC_BOB;
        assertCommandSuccess(command, guestToAdd, roomNumberToAdd,
                 bookingPeriodToAdd);

        /* Case: add a guest, missing tags -> added */
        roomNumberToAdd = ROOM_NUMBER_023;
        bookingPeriodToAdd = TOMORROW_NEXTWEEK;
        assertCommandSuccess(HOON, roomNumberToAdd, bookingPeriodToAdd);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the guest list before adding -> added */
        showGuestsWithName(KEYWORD_MATCHING_MEIER);
        roomNumberToAdd = ROOM_NUMBER_024;
        assertCommandSuccess(IDA, roomNumberToAdd, bookingPeriodToAdd);

        /* ------------------------ Perform add operation while a guest card is selected --------------------------- */

        /* Case: selects first card in the guest list, add a guest -> added, card selection remains unchanged */
        selectGuest(Index.fromOneBased(1));
        roomNumberToAdd = ROOM_NUMBER_099;
        assertCommandSuccess(JAKOB, roomNumberToAdd, bookingPeriodToAdd);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        Guest validGuest = new GuestBuilder().build();
        RoomNumber validRoomNumber = ROOM_NUMBER_001;
        BookingPeriod validBookingPeriod =
                BookingBuilder.DEFAULT_BOOKING_PERIOD_TODAY_TOMORROW;

        /* Case: add a duplicate guest -> rejected */
        command = GuestUtil.getAddCommand(HOON, validRoomNumber, validBookingPeriod);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_GUEST);

        /* Case: add a duplicate guest except with different phone -> rejected */
        guestToAdd = new GuestBuilder(HOON).withPhone(VALID_PHONE_BOB).build();
        command = GuestUtil.getAddCommand(guestToAdd, validRoomNumber, validBookingPeriod);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_GUEST);

        /* Case: add a duplicate guest except with different email -> rejected */
        guestToAdd = new GuestBuilder(HOON).withEmail(VALID_EMAIL_BOB).build();
        command = GuestUtil.getAddCommand(guestToAdd, validRoomNumber, validBookingPeriod);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_GUEST);

        /* Case: add a duplicate guest except with different address -> rejected */
        guestToAdd = new GuestBuilder(HOON).build();
        command = GuestUtil.getAddCommand(guestToAdd, validRoomNumber, validBookingPeriod);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_GUEST);

        /* Case: add a duplicate guest except with different tags -> rejected */
        guestToAdd = new GuestBuilder(HOON).withTags("friends").build();
        command = GuestUtil.getAddCommand(guestToAdd, validRoomNumber, validBookingPeriod);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_GUEST);

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
        command = "adds " + GuestUtil.getAddCommand(validGuest,
                validRoomNumber, validBookingPeriod);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command =
                AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_AMY
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
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Guest guestToAdd,
                                      RoomNumber roomNumberToAdd,
                                      BookingPeriod bookingPeriodToAdd) {

        String command = GuestUtil.getAddCommand(guestToAdd,
                roomNumberToAdd, bookingPeriodToAdd);

        assertCommandSuccess(command,
                guestToAdd, roomNumberToAdd, bookingPeriodToAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Guest,
     * RoomNumber, BookingPeriod)}. Executes {@code command} instead.
     *
     * @see AddCommandSystemTest#assertCommandSuccess(Guest, RoomNumber, BookingPeriod)
     */
    private void assertCommandSuccess(String command, Guest guestToAdd,
                                       RoomNumber roomNumber, BookingPeriod bookingPeriod) {
        Model expectedModel = getModel();
        expectedModel.addGuest(guestToAdd);

        Booking expectedBooking = new Booking(guestToAdd, bookingPeriod);
        expectedModel.addBooking(roomNumber, expectedBooking);
        String expectedResultMessage =
                String.format(AddCommand.MESSAGE_SUCCESS, guestToAdd,
                        roomNumber, bookingPeriod);

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
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
