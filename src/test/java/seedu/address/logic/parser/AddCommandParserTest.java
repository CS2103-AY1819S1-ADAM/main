package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATE_END_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_END_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATE_START_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_START_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_END_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_START_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROOM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.ROOM_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ROOM_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_END_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_END_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_START_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_START_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROOM_NUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROOM_NUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalGuests.AMY;
import static seedu.address.testutil.TypicalGuests.BOB;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.guest.Address;
import seedu.address.model.guest.Email;
import seedu.address.model.guest.Guest;
import seedu.address.model.guest.Name;
import seedu.address.model.guest.Phone;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.BookingPeriod;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.GuestBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Guest expectedGuest = new GuestBuilder(BOB).withTags(VALID_TAG_FRIEND).build();
        RoomNumber expectedRoomNumber = new RoomNumber(VALID_ROOM_NUMBER_BOB);
        BookingPeriod expectedBookingPeriod =
                new BookingPeriod(VALID_DATE_START_BOB, VALID_DATE_END_BOB);

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND
                        + ROOM_DESC_BOB + DATE_START_DESC_BOB + DATE_END_DESC_BOB,
                new AddCommand(expectedGuest, expectedRoomNumber, expectedBookingPeriod));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB
                        + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + TAG_DESC_FRIEND + ROOM_DESC_BOB + DATE_START_DESC_BOB
                        + DATE_END_DESC_BOB,
                new AddCommand(expectedGuest, expectedRoomNumber, expectedBookingPeriod));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY
                        + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + TAG_DESC_FRIEND + ROOM_DESC_BOB + DATE_START_DESC_BOB
                        + DATE_END_DESC_BOB,
                new AddCommand(expectedGuest, expectedRoomNumber, expectedBookingPeriod));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_AMY + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + TAG_DESC_FRIEND + ROOM_DESC_BOB + DATE_START_DESC_BOB
                        + DATE_END_DESC_BOB,
                new AddCommand(expectedGuest, expectedRoomNumber, expectedBookingPeriod));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB
                        + TAG_DESC_FRIEND + ROOM_DESC_BOB + DATE_START_DESC_BOB
                        + DATE_END_DESC_BOB,
                new AddCommand(expectedGuest, expectedRoomNumber, expectedBookingPeriod));

        // multiple tags - all accepted
        Guest expectedGuestMultipleTags = new GuestBuilder(BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND + ROOM_DESC_BOB + DATE_START_DESC_BOB
                        + DATE_END_DESC_BOB,
                new AddCommand(expectedGuestMultipleTags, expectedRoomNumber,
                        expectedBookingPeriod));

        // multiple rooms - last room accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND
                        + ROOM_DESC_AMY + DATE_START_DESC_BOB + DATE_END_DESC_BOB,
                new AddCommand(expectedGuest, expectedRoomNumber, expectedBookingPeriod));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Guest expectedGuest = new GuestBuilder(AMY).withTags().build();
        RoomNumber expectedRoomNumber = new RoomNumber(VALID_ROOM_NUMBER_AMY);
        BookingPeriod expectedBookingPeriod =
                new BookingPeriod(VALID_DATE_START_AMY, VALID_DATE_END_AMY);

        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY
                        + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + ROOM_DESC_AMY
                        + DATE_START_DESC_AMY + DATE_END_DESC_AMY,
                new AddCommand(expectedGuest, expectedRoomNumber, expectedBookingPeriod));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                        + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB
                        + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND + ROOM_DESC_BOB + DATE_START_DESC_BOB
                        + DATE_END_DESC_BOB,
                 Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND + ROOM_DESC_BOB + DATE_START_DESC_BOB
                        + DATE_END_DESC_BOB,
                 Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                        + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND + ROOM_DESC_BOB + DATE_START_DESC_BOB
                        + DATE_END_DESC_BOB,
                Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND + ROOM_DESC_BOB + DATE_START_DESC_BOB
                        + DATE_END_DESC_BOB,
                 Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + INVALID_TAG_DESC
                        + VALID_TAG_FRIEND + ROOM_DESC_BOB + DATE_START_DESC_BOB
                        + DATE_END_DESC_BOB,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        // invalid room number
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND
                        + INVALID_ROOM_DESC + DATE_START_DESC_BOB + DATE_END_DESC_BOB,
                RoomNumber.MESSAGE_ROOM_NUMBER_CONSTRAINTS);

        // invalid start date
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND
                        + ROOM_DESC_BOB + INVALID_DATE_START_DESC + DATE_END_DESC_BOB,
                BookingPeriod.MESSAGE_BOOKING_PERIOD_CONSTRAINTS);

        // invalid end date
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND
                        + ROOM_DESC_BOB + DATE_START_DESC_BOB + INVALID_DATE_END_DESC,
                BookingPeriod.MESSAGE_BOOKING_PERIOD_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC + ROOM_DESC_BOB
                        + ROOM_DESC_BOB + DATE_START_DESC_BOB + DATE_END_DESC_BOB,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB
                        + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + ROOM_DESC_BOB
                        + DATE_START_DESC_BOB + DATE_END_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
