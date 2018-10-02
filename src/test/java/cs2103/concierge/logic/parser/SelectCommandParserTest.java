package cs2103.concierge.logic.parser;

import static cs2103.concierge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static cs2103.concierge.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import cs2103.concierge.logic.commands.SelectCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class SelectCommandParserTest {

    private SelectCommandParser parser = new SelectCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, "1", new SelectCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
    }
}
