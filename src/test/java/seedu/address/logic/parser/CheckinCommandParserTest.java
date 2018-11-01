package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_END_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_START_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ROOM_DESC_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.CheckInCommand;
import seedu.address.model.room.RoomNumber;
import seedu.address.testutil.TypicalRoomNumbers;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the CheckInCommand code. For example, inputs "1", "01" , and "0001" take the
 * same path through the CheckInCommand, and therefore we test only one of
 * them. The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class CheckinCommandParserTest {

    private CheckInCommandParser parser = new CheckInCommandParser();

    @Test
    public void parse_validArgs_returnsCheckInCommand() {
        RoomNumber expectedRoomNumber = TypicalRoomNumbers.ROOM_NUMBER_AMY;
        assertParseSuccess(parser, ROOM_DESC_AMY, new CheckInCommand(expectedRoomNumber));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckInCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckInCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "01", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckInCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "001", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckInCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-r 001",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckInCommand.MESSAGE_USAGE));
        assertParseFailure(parser, ROOM_DESC_AMY + DATE_START_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckInCommand.MESSAGE_USAGE));
        assertParseFailure(parser, ROOM_DESC_AMY + DATE_END_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckInCommand.MESSAGE_USAGE));
        assertParseFailure(parser, ROOM_DESC_AMY + DATE_START_DESC_AMY + DATE_END_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckInCommand.MESSAGE_USAGE));
    }
}
