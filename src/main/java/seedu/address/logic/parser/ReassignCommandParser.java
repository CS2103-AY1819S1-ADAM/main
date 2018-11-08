package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_ROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM;

import java.time.LocalDate;

import seedu.address.logic.commands.ReassignCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.room.RoomNumber;

/**
 * Parses input arguments and creates a new ReassignCommand object
 */
public class ReassignCommandParser implements Parser<ReassignCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ReassignCommand
     * and returns an ReassignCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReassignCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ROOM, PREFIX_DATE_START, PREFIX_NEW_ROOM);
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_ROOM, PREFIX_DATE_START, PREFIX_NEW_ROOM)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReassignCommand.MESSAGE_USAGE));
        }
        try {
            RoomNumber roomNumber = ParserUtil.parseRoomNumber(argMultimap.getValue(PREFIX_ROOM).get());
            LocalDate startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE_START).get());
            RoomNumber newRoomNumber = ParserUtil.parseRoomNumber(argMultimap.getValue(PREFIX_NEW_ROOM).get());
            return new ReassignCommand(roomNumber, startDate, newRoomNumber);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReassignCommand.MESSAGE_USAGE), pe);
        }
    }

}
