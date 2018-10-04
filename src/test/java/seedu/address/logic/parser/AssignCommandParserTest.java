package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INDEX_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROOM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.ROOM_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROOM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.model.room.RoomNumber;

public class AssignCommandParserTest {
    private AssignCommandParser parser = new AssignCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        AssignCommand expectedAssignCommand =
                new AssignCommand(Index.fromOneBased(Integer.valueOf(VALID_INDEX)),
                        new RoomNumber(VALID_ROOM));

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + INDEX_DESC_BOB + ROOM_DESC_BOB, expectedAssignCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AssignCommand.MESSAGE_USAGE);

        // missing index prefix
        assertParseFailure(parser, VALID_INDEX + ROOM_DESC_BOB, expectedMessage);

        // missing room prefix
        assertParseFailure(parser, INDEX_DESC_BOB + VALID_ROOM, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_INDEX + VALID_ROOM, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid index
        assertParseFailure(parser, INVALID_INDEX_DESC + ROOM_DESC_BOB,
                Index.MESSAGE_INDEX_CONSTRAINTS);

        // invalid room number
        assertParseFailure(parser, INDEX_DESC_BOB + INVALID_ROOM_DESC,
                RoomNumber.MESSAGE_ROOM_NUMBER_CONSTRAINTS);

        // both invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_INDEX_DESC + INVALID_ROOM_DESC,
                Index.MESSAGE_INDEX_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + INDEX_DESC_BOB + ROOM_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AssignCommand.MESSAGE_USAGE));
    }
}
