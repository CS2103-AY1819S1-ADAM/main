package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.FLAG_GUEST;
import static seedu.address.logic.parser.CliSyntax.FLAG_ROOM;
import static seedu.address.logic.parser.CliSyntax.FLAG_ROOM_HAS_BOOKINGS;
import static seedu.address.logic.parser.CliSyntax.FLAG_ROOM_NO_BOOKINGS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM_CAPACITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.guest.Guest;
import seedu.address.model.guest.GuestEmailExactPredicate;
import seedu.address.model.guest.GuestNameContainsKeywordsPredicate;
import seedu.address.model.guest.GuestPhoneExactPredicate;
import seedu.address.model.guest.GuestTagsContainsKeywordsPredicate;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomBookingsDateRangePredicate;
import seedu.address.model.room.RoomCapacityExactPredicate;
import seedu.address.model.room.RoomHasBookingsExactPredicate;
import seedu.address.model.room.RoomNumberExactPredicate;
import seedu.address.model.room.RoomTagsContainsKeywordsPredicate;
import seedu.address.model.room.booking.BookingPeriod;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(FindCommandParser.MESSAGE_NO_FLAGS, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_wrongFlag_throwsParseException() {
        assertParseFailure(parser, " -a",
                String.format(FindCommandParser.MESSAGE_NO_FLAGS, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validGuestFlagNoFilters_throwsParseException() {
        assertParseFailure(parser, " " + FLAG_GUEST,
                String.format(FindCommandParser.MESSAGE_NO_FILTERS, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validGuestFlagNullValueFilter_throwsParseException() {
        assertParseFailure(parser, " " + FLAG_GUEST + " " + PREFIX_NAME,
                String.format(FindCommandParser.MESSAGE_NULL_FILTERS, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validRoomFlagNoFilters_throwsParseException() {
        assertParseFailure(parser, " " + FLAG_ROOM,
                String.format(FindCommandParser.MESSAGE_NO_FILTERS, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validRoomFlagNullValueFilter_throwsParseException() {
        assertParseFailure(parser, " " + FLAG_ROOM + " " + PREFIX_ROOM,
                String.format(FindCommandParser.MESSAGE_NULL_FILTERS, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validRoomFlagTwoBookingFlags_throwsParseException() {
        assertParseFailure(parser, " " + FLAG_ROOM + " " + FLAG_ROOM_HAS_BOOKINGS + " "
                        + FLAG_ROOM_NO_BOOKINGS,
                String.format(FindCommandParser.MESSAGE_EXTRA_BOOKING_FLAG, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validRoomFlagHasStartEndDateNoBookingFlag_throwsParseException() {
        assertParseFailure(parser, " " + FLAG_ROOM + " " + PREFIX_DATE_START + " 01/01/2018 ",
                String.format(FindCommandParser.MESSAGE_BOOKING_PERIOD_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + FLAG_ROOM + " " + PREFIX_DATE_END + " 01/01/2018 ",
                String.format(FindCommandParser.MESSAGE_BOOKING_PERIOD_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + FLAG_ROOM + " " + PREFIX_DATE_START + " 01/01/2018 "
                + PREFIX_DATE_END + " 02/01/2018",
                String.format(FindCommandParser.MESSAGE_BOOKING_PERIOD_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + FLAG_ROOM + " " + PREFIX_DATE_END + " 02/01/2018 "
                        + PREFIX_DATE_START + " 01/01/2018",
                String.format(FindCommandParser.MESSAGE_BOOKING_PERIOD_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validRoomFlagHasBookingFlagIncompletePeriod_throwsParseException() {
        assertParseFailure(parser, " " + FLAG_ROOM + " " + FLAG_ROOM_HAS_BOOKINGS + " "
                + PREFIX_DATE_START + " 01/01/2018",
                String.format(FindCommandParser.MESSAGE_BOOKING_PERIOD_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + FLAG_ROOM + " " + FLAG_ROOM_HAS_BOOKINGS + " "
                        + PREFIX_DATE_END + " 01/01/2018",
                String.format(FindCommandParser.MESSAGE_BOOKING_PERIOD_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + FLAG_ROOM + " " + FLAG_ROOM_NO_BOOKINGS + " "
                        + PREFIX_DATE_START + " 01/01/2018",
                String.format(FindCommandParser.MESSAGE_BOOKING_PERIOD_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + FLAG_ROOM + " " + FLAG_ROOM_NO_BOOKINGS + " "
                        + PREFIX_DATE_END + " 01/01/2018",
                String.format(FindCommandParser.MESSAGE_BOOKING_PERIOD_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validRoomFlagBookingPeriodSame_throwsParseException() {
        assertParseFailure(parser, " " + FLAG_ROOM + " " + FLAG_ROOM_NO_BOOKINGS + " "
                        + PREFIX_DATE_START + " 01/01/2018 " + PREFIX_DATE_END + " 01/01/2018",
                String.format(FindCommandParser.MESSAGE_BOOKING_SAME_DATE, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + FLAG_ROOM + " " + FLAG_ROOM_HAS_BOOKINGS + " "
                        + PREFIX_DATE_START + " 01/01/2018 " + PREFIX_DATE_END + " 01/01/2018",
                String.format(FindCommandParser.MESSAGE_BOOKING_SAME_DATE, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validGuestFlag_returnsFindCommand() {
        // no leading and trailing whitespaces
        List<Predicate<Guest>> listPredicates = new LinkedList<>();
        List<Predicate<Room>> emptyRoomPredicates = new LinkedList<>();
        listPredicates.add(new GuestNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        FindCommand expectedFindCommand =
                new FindCommand(FLAG_GUEST.toString(), listPredicates, emptyRoomPredicates);
        assertParseSuccess(parser, " " + FLAG_GUEST + " " + PREFIX_NAME + "Alice Bob", expectedFindCommand);
    }

    @Test
    public void parse_validGuestFlagAllFilters_returnsFindCommand() {
        // no leading and trailing whitespaces
        List<Predicate<Guest>> guestPredicates = new LinkedList<>();
        List<Predicate<Room>> emptyRoomPredicates = new LinkedList<>();
        guestPredicates.add(new GuestNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        guestPredicates.add(new GuestPhoneExactPredicate("81027115"));
        guestPredicates.add(new GuestEmailExactPredicate("alice@gmail.com"));
        guestPredicates.add(new GuestTagsContainsKeywordsPredicate(
                new LinkedList<>(Arrays.asList("Friends", "Neighbour"))));
        FindCommand expectedFindCommand =
                new FindCommand(FLAG_GUEST.toString(), guestPredicates, emptyRoomPredicates);
        assertParseSuccess(parser, " " + FLAG_GUEST + " " + PREFIX_NAME + "Alice Bob " + PREFIX_PHONE
                + "81027115 " + PREFIX_EMAIL + "alice@gmail.com " + PREFIX_TAG + "Friends Neighbour",
                expectedFindCommand);
    }

    @Test
    public void parse_validRoomFlag_returnsFindCommand() {
        // no leading and trailing whitespaces
        List<Predicate<Guest>> emptyGuestPredicates = new LinkedList<>();
        List<Predicate<Room>> roomPredicates = new LinkedList<>();
        roomPredicates.add(new RoomNumberExactPredicate("001"));
        FindCommand expectedFindCommand =
                new FindCommand(FLAG_ROOM.toString(), emptyGuestPredicates, roomPredicates);
        assertParseSuccess(parser, " " + FLAG_ROOM + " " + PREFIX_ROOM + "001", expectedFindCommand);
    }

    @Test
    public void parse_validRoomFlagAllFilters_returnsFindCommand() {
        // no leading and trailing whitespaces
        List<Predicate<Guest>> emptyGuestPredicates = new LinkedList<>();
        List<Predicate<Room>> roomPredicates = new LinkedList<>();
        roomPredicates.add(new RoomNumberExactPredicate("001"));
        roomPredicates.add(new RoomCapacityExactPredicate("2"));
        roomPredicates.add(new RoomTagsContainsKeywordsPredicate(
                new LinkedList<>(Arrays.asList("Filthy", "AF"))));
        roomPredicates.add(new RoomHasBookingsExactPredicate(true));
        roomPredicates.add(new RoomBookingsDateRangePredicate(new BookingPeriod("01/01/2018", "02/01/2018")));
        FindCommand expectedFindCommand =
                new FindCommand(FLAG_ROOM.toString(), emptyGuestPredicates, roomPredicates);
        assertParseSuccess(parser, " " + FLAG_ROOM + " " + PREFIX_ROOM + "001 " + PREFIX_ROOM_CAPACITY
                        + "2 " + PREFIX_TAG + "Filthy AF " + FLAG_ROOM_HAS_BOOKINGS + " " + PREFIX_DATE_START
                + " 01/01/2018 " + PREFIX_DATE_END + " 02/01/2018",
                expectedFindCommand);
    }

}
