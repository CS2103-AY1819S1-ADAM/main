package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.USERNAME_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.USERNAME_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_1;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.LogInCommand;

public class LogInCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogInCommand.MESSAGE_USAGE);
    private LogInCommandParser parser = new LogInCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String expectedUsername = VALID_USERNAME_1;
        String expectedPassword = VALID_PASSWORD_1;

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + USERNAME_DESC_1 + PASSWORD_DESC_1,
                new LogInCommand(expectedUsername, expectedPassword));

        // multiple usernames - last usernames accepted
        assertParseSuccess(parser,
                USERNAME_DESC_2 + USERNAME_DESC_1 + PASSWORD_DESC_1,
                new LogInCommand(expectedUsername, expectedPassword));

        // multiple passwords - last password accepted
        assertParseSuccess(parser,
                USERNAME_DESC_1 + PASSWORD_DESC_2 + PASSWORD_DESC_1,
                new LogInCommand(expectedUsername, expectedPassword));
    }

    @Test
    public void parse_missingParts_failure() {
        // no username specified
        assertParseFailure(parser, PASSWORD_DESC_1, MESSAGE_INVALID_FORMAT);

        // no password specified
        assertParseFailure(parser, USERNAME_DESC_1, MESSAGE_INVALID_FORMAT);

        // no username and no password specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                LogInCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser, VALID_USERNAME_1 + PASSWORD_DESC_1,
                expectedMessage);

        // missing password prefix
        assertParseFailure(parser, USERNAME_DESC_1 + VALID_USERNAME_1,
                expectedMessage);

        // both prefixes missing
        assertParseFailure(parser, VALID_USERNAME_1 + VALID_PASSWORD_1,
                expectedMessage);
    }

}
