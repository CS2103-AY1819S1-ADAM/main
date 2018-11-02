package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.commands.LogInCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LogInCommand object
 */

public class LogInCommandParser implements Parser<LogInCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * LogInCommand and returns a LogInCommand object for execution.
     *
     * The password supplies will be hashed for security reasons.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LogInCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME,
                        PREFIX_PASSWORD);
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogInCommand.MESSAGE_USAGE));
        }

        String userName = argMultimap.getValue(PREFIX_USERNAME).get();
        String hashedPasswordGuess =
                ParserUtil.parseAndHashPassword(argMultimap.getValue(PREFIX_PASSWORD).get());

        return new LogInCommand(userName, hashedPasswordGuess);
    }

}
