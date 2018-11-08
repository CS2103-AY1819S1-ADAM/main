package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM;

import java.time.LocalDate;
import java.util.Optional;

import seedu.address.logic.commands.CheckoutCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.room.RoomNumber;

/**
 * Parses input arguments and creates a new CheckoutCommand object
 */
public class CheckoutCommandParser implements Parser<CheckoutCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CheckoutCommand
     * and returns an CheckoutCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CheckoutCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ROOM, PREFIX_DATE_START);
        if (!argMultimap.getValue(PREFIX_ROOM).isPresent() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckoutCommand.MESSAGE_USAGE));
        }
        try {
            RoomNumber roomNumber = ParserUtil.parseRoomNumber(argMultimap.getValue(PREFIX_ROOM).get());
            Optional<String> optionalStartDate = argMultimap.getValue(PREFIX_DATE_START);
            if (optionalStartDate.isPresent()) {
                LocalDate startDate = ParserUtil.parseDate(optionalStartDate.get());
                return new CheckoutCommand(roomNumber, startDate);
            }
            return new CheckoutCommand(roomNumber);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckoutCommand.MESSAGE_USAGE), pe);
        }
    }

}
