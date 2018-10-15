package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.CheckoutCommand;
import seedu.address.testutil.TypicalRoomNumbers;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the CheckoutCommand code. For example, inputs "1", "01" , and "0001" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class CheckoutCommandParserTest {

    private CheckoutCommandParser parser = new CheckoutCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "001", new CheckoutCommand(TypicalRoomNumbers.ROOM_NUMBER_001));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckoutCommand.MESSAGE_USAGE));
    }
}
