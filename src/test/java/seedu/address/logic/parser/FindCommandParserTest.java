package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.FLAG_GUEST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.guest.Guest;
import seedu.address.model.guest.GuestNameContainsKeywordsPredicate;
import seedu.address.model.room.Room;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(FindCommandParser.MESSAGE_NO_FLAGS, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        List<Predicate<Guest>> listPredicates = new LinkedList<>();
        List<Predicate<Room>> emptyRoomPredicates = new LinkedList<>();
        listPredicates.add(new GuestNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        FindCommand expectedFindCommand =
                new FindCommand(FLAG_GUEST.toString(), listPredicates, emptyRoomPredicates);
        assertParseSuccess(parser, " " + FLAG_GUEST + " " + PREFIX_NAME + "Alice Bob", expectedFindCommand);
    }

}
