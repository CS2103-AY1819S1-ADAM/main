package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM;

import seedu.address.logic.commands.CheckoutCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.BookingPeriod;

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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ROOM, PREFIX_DATE_START, PREFIX_DATE_END);
        if (!argMultimap.getValue(PREFIX_ROOM).isPresent()
                || !argMultimap.getPreamble().isEmpty()
                || (argMultimap.getValue(PREFIX_DATE_START).isPresent()
                    && !argMultimap.getValue(PREFIX_DATE_END).isPresent())
                || (argMultimap.getValue(PREFIX_DATE_END).isPresent()
                    && !argMultimap.getValue(PREFIX_DATE_START).isPresent())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckoutCommand.MESSAGE_USAGE));
        }
        try {
            RoomNumber roomNumber = ParserUtil.parseRoomNumber(argMultimap.getValue(PREFIX_ROOM).get());
            if (ParserUtil.arePrefixesPresent(argMultimap, PREFIX_DATE_START, PREFIX_DATE_END)) {
                BookingPeriod bookingPeriod = ParserUtil.parseBookingPeriod(
                        argMultimap.getValue(PREFIX_DATE_START).get(), argMultimap.getValue(PREFIX_DATE_END).get());
                return new CheckoutCommand(roomNumber, bookingPeriod);
            }
            return new CheckoutCommand(roomNumber);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckoutCommand.MESSAGE_USAGE), pe);
        }
    }

}
