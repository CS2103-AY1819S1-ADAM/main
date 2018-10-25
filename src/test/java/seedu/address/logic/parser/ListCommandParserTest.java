package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FLAG_GUEST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FLAG_ROOM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ListCommand;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_noFlag_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_doubleFlag_throwsParseException() {
        assertParseFailure(parser, VALID_FLAG_ROOM + " " + VALID_FLAG_GUEST,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        assertParseFailure(parser, VALID_FLAG_GUEST + " " + VALID_FLAG_ROOM,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_wrongFlag_throwsParseException() {
        assertParseFailure(parser, "-a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsListCommand() {

        ListCommand expectedRoomListCommand = new ListCommand(new String[]{VALID_FLAG_ROOM});
        ListCommand expectedGuestListCommand = new ListCommand(new String[]{VALID_FLAG_GUEST});

        // no leading and trailing whitespaces
        assertParseSuccess(parser, "-r", expectedRoomListCommand);
        // Newline after list
        assertParseSuccess(parser, " \n -r", expectedRoomListCommand);

        // no leading and trailing whitespaces
        assertParseSuccess(parser, "-g", expectedGuestListCommand);
        // Newline after list
        assertParseSuccess(parser, " \n -g", expectedGuestListCommand);
    }

}
