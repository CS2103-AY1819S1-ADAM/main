package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_END_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_START_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ROOM_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_START_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_AMY;

import java.time.LocalDate;

import org.junit.Test;

import seedu.address.logic.commands.CheckoutCommand;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.BookingPeriod;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the CheckoutCommand code. For example, inputs "r/1", "r/01" , and "r/0001" take the
 * same path through the CheckoutCommand, and therefore we test only one of
 * them. The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class CheckoutCommandParserTest {

    private CheckoutCommandParser parser = new CheckoutCommandParser();

    @Test
    public void parse_validArgs_returnsCheckoutCommand() {
        RoomNumber expectedRoomNumber = ROOM_NUMBER_AMY;
        LocalDate expectedStartDate = LocalDate.parse(VALID_DATE_START_AMY, BookingPeriod.STRING_TO_DATE_FORMAT);
        assertParseSuccess(parser, ROOM_DESC_AMY, new CheckoutCommand(expectedRoomNumber));
        assertParseSuccess(parser, ROOM_DESC_AMY + DATE_START_DESC_AMY,
                new CheckoutCommand(expectedRoomNumber, expectedStartDate));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // empty string
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckoutCommand.MESSAGE_USAGE));

        // invalid room numbers
        assertParseFailure(parser, "r/1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckoutCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "r/01",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckoutCommand.MESSAGE_USAGE));

        // missing room flag prefix
        assertParseFailure(parser, "001", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckoutCommand.MESSAGE_USAGE));

        // invalid room flag prefix
        assertParseFailure(parser, "-r 001",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckoutCommand.MESSAGE_USAGE));

        // missing start date
        assertParseFailure(parser, ROOM_DESC_AMY + DATE_END_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckoutCommand.MESSAGE_USAGE));

        // missing room description
        assertParseFailure(parser, DATE_START_DESC_AMY + DATE_END_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckoutCommand.MESSAGE_USAGE));

        // invalid calendar dates (30 Feb does not exist)
        assertParseFailure(parser, ROOM_DESC_AMY + " from/30/02/18",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckoutCommand.MESSAGE_USAGE));

        // invalid date format (needs to be d/M/y)
        assertParseFailure(parser, ROOM_DESC_AMY + " from/2018/01/01",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckoutCommand.MESSAGE_USAGE));
    }
}
