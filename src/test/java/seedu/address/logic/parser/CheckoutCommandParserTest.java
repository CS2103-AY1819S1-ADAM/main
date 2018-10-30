package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_END_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_START_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ROOM_DESC_001;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_END_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_START_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.CheckoutCommand;
import seedu.address.model.room.booking.BookingPeriod;
import seedu.address.testutil.TypicalRoomNumbers;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the CheckoutCommand code. For example, inputs "1", "01" , and "0001" take the
 * same path through the CheckoutCommand, and therefore we test only one of
 * them. The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class CheckoutCommandParserTest {

    private CheckoutCommandParser parser = new CheckoutCommandParser();

    @Test
    public void parse_validArgs_returnsCheckoutCommand() {
        assertParseSuccess(parser, ROOM_DESC_001, new CheckoutCommand(TypicalRoomNumbers.ROOM_NUMBER_001));
        assertParseSuccess(parser, ROOM_DESC_001 + DATE_START_DESC_AMY + DATE_END_DESC_AMY,
                new CheckoutCommand(TypicalRoomNumbers.ROOM_NUMBER_001,
                        new BookingPeriod(VALID_DATE_START_AMY, VALID_DATE_END_AMY)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckoutCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "001", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckoutCommand.MESSAGE_USAGE));
        assertParseFailure(parser, ROOM_DESC_001 + DATE_START_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckoutCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " r/001 from/01/01/18 to/30/02/18",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckoutCommand.MESSAGE_USAGE));
    }
}
