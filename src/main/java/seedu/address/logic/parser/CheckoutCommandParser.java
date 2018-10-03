package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

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
        try {
            RoomNumber roomNumber = ParserUtil.parseRoomNumber(args);
            return new CheckoutCommand(roomNumber);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckoutCommand.MESSAGE_USAGE), pe);
        }
    }

}
