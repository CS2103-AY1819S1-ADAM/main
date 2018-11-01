package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM;

import seedu.address.logic.commands.CheckInCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.room.RoomNumber;

/**
 * Parses input arguments and creates a new CheckInCommand object
 */
public class CheckInCommandParser implements Parser<CheckInCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CheckInCommand
     * and returns an CheckInCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CheckInCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ROOM);
        if (!argMultimap.getValue(PREFIX_ROOM).isPresent() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckInCommand.MESSAGE_USAGE));
        }
        try {
            RoomNumber roomNumber = ParserUtil.parseRoomNumber(argMultimap.getValue(PREFIX_ROOM).get());
            return new CheckInCommand(roomNumber);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckInCommand.MESSAGE_USAGE), pe);
        }
    }

}
