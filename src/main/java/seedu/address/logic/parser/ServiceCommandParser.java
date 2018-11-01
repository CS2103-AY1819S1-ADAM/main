package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM;

import java.util.Optional;

import seedu.address.logic.commands.ServiceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.expenses.Money;
import seedu.address.model.room.RoomNumber;

/**
 * Parses input arguments and creates a new ServiceCommand object.
 */
public class ServiceCommandParser implements Parser<ServiceCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ServiceCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ROOM, PREFIX_ITEM_NUMBER, PREFIX_COST);
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_ROOM, PREFIX_ITEM_NUMBER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ServiceCommand.MESSAGE_USAGE));
        }
        RoomNumber roomNumber = ParserUtil.parseRoomNumber(argMultimap.getValue(PREFIX_ROOM).get());
        String itemNumber = argMultimap.getValue(PREFIX_ITEM_NUMBER).get();
        Optional<Money> itemCost = ParserUtil.parseCost(argMultimap.getValue(PREFIX_COST));
        return new ServiceCommand(roomNumber, itemNumber, itemCost);
    }
}
