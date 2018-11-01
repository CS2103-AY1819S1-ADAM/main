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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
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

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    public static final String MESSAGE_NO_FLAGS = "No flags found! \n%1$s";
    public static final String MESSAGE_NO_FILTERS = "No specified filters found! \n%1$s";
    public static final String MESSAGE_NULL_FILTERS = "Null value in filters found! \n%1$s";
    public static final String MESSAGE_EXTRA_BOOKING_FLAG = "Extra bookings flag found! \n%1$s";
    public static final String MESSAGE_BOOKING_PERIOD_FORMAT = "Booking period format is wrong! \n%1$s";
    public static final String MESSAGE_BOOKING_SAME_DATE = "Specified days should not be the same! \n%1$s";

    private final List<Predicate<Guest>> guestPredicates;
    private final List<Predicate<Room>> roomPredicates;

    public FindCommandParser() {
        guestPredicates = new LinkedList<>();
        roomPredicates = new LinkedList<>();
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, FLAG_ROOM, FLAG_GUEST);

        if ((!ParserUtil.arePrefixesPresent(argMultimap, FLAG_ROOM)
                && !ParserUtil.arePrefixesPresent(argMultimap, FLAG_GUEST))
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_NO_FLAGS, FindCommand.MESSAGE_USAGE));
        }

        if (ParserUtil.arePrefixesPresent(argMultimap, FLAG_GUEST)) {
            getGuestPredicates(args);
            return new FindCommand(FLAG_GUEST.toString(), guestPredicates, roomPredicates);
        } else {
            getRoomPredicates(args);
            return new FindCommand(FLAG_ROOM.toString(), guestPredicates, roomPredicates);
        }
    }

    /**
     * Handles the logic and creation of several predicates based on specified prefixes/flags by the user from
     * {@code suffixFilters} to find guests.
     * @throws ParseException if the user input does not conform the expected format
     */
    private void getGuestPredicates(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE,
                        PREFIX_EMAIL, PREFIX_TAG);

        if (!ParserUtil.areAnyPrefixPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_TAG)) {
            throw new ParseException(
                    String.format(MESSAGE_NO_FILTERS, FindCommand.MESSAGE_USAGE));
        }

        if (ParserUtil.areAnyPrefixValueNull(argMultimap, PREFIX_NAME, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_TAG)) {
            throw new ParseException(
                    String.format(MESSAGE_NULL_FILTERS, FindCommand.MESSAGE_USAGE));
        }

        if (ParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            String[] nameKeywords = argMultimap.getValue(PREFIX_NAME).get().trim().split("\\s+");
            guestPredicates.add(new GuestNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }

        if (ParserUtil.arePrefixesPresent(argMultimap, PREFIX_PHONE)) {
            guestPredicates.add(new GuestPhoneExactPredicate(argMultimap.getValue(PREFIX_PHONE).get()));
        }

        if (ParserUtil.arePrefixesPresent(argMultimap, PREFIX_EMAIL)) {
            guestPredicates.add(new GuestEmailExactPredicate(argMultimap.getValue(PREFIX_EMAIL).get()));
        }

        if (ParserUtil.arePrefixesPresent(argMultimap, PREFIX_TAG)) {
            String[] tagsKeywords = argMultimap.getValue(PREFIX_TAG).get().trim().split("\\s+");
            guestPredicates.add(new GuestTagsContainsKeywordsPredicate(Arrays.asList(tagsKeywords)));
        }
    }

    /**
     * Handles the logic and creation of several predicates based on specified prefixes/flags by the user from
     * {@code suffixFilters} to find rooms.
     * @throws ParseException if the user input does not conform the expected format
     */
    private void getRoomPredicates(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ROOM, PREFIX_ROOM_CAPACITY,
                        PREFIX_TAG, FLAG_ROOM_HAS_BOOKINGS, FLAG_ROOM_NO_BOOKINGS, PREFIX_DATE_START,
                        PREFIX_DATE_END);

        if (!ParserUtil.areAnyPrefixPresent(argMultimap, PREFIX_ROOM, PREFIX_ROOM_CAPACITY,
                PREFIX_TAG, FLAG_ROOM_HAS_BOOKINGS, FLAG_ROOM_NO_BOOKINGS, PREFIX_DATE_START,
                PREFIX_DATE_END)) {
            throw new ParseException(
                    String.format(MESSAGE_NO_FILTERS, FindCommand.MESSAGE_USAGE));
        }

        if (ParserUtil.areAnyPrefixValueNull(argMultimap, PREFIX_ROOM, PREFIX_ROOM_CAPACITY,
                PREFIX_TAG, PREFIX_DATE_START, PREFIX_DATE_END)) {
            throw new ParseException(
                    String.format(MESSAGE_NULL_FILTERS, FindCommand.MESSAGE_USAGE));
        }

        if (ParserUtil.arePrefixesPresent(argMultimap, FLAG_ROOM_HAS_BOOKINGS, FLAG_ROOM_NO_BOOKINGS)) {
            throw new ParseException(
                    String.format(MESSAGE_EXTRA_BOOKING_FLAG, FindCommand.MESSAGE_USAGE));
        }

        if (ParserUtil.areAnyPrefixPresent(argMultimap, PREFIX_DATE_START, PREFIX_DATE_END)) {
            if (!ParserUtil.areAnyPrefixPresent(argMultimap, FLAG_ROOM_HAS_BOOKINGS, FLAG_ROOM_NO_BOOKINGS)) {
                throw new ParseException(
                        String.format(MESSAGE_BOOKING_PERIOD_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }

        if (ParserUtil.arePrefixesPresent(argMultimap, FLAG_ROOM_HAS_BOOKINGS)
                || ParserUtil.arePrefixesPresent(argMultimap, FLAG_ROOM_NO_BOOKINGS)) {
            if (ParserUtil.arePrefixesPresent(argMultimap, PREFIX_DATE_START)
                    || ParserUtil.arePrefixesPresent(argMultimap, PREFIX_DATE_END)) {
                if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_DATE_START, PREFIX_DATE_END)) {
                    throw new ParseException(
                            String.format(MESSAGE_BOOKING_PERIOD_FORMAT, FindCommand.MESSAGE_USAGE));
                }
            }
        }

        if (ParserUtil.arePrefixesPresent(argMultimap, PREFIX_ROOM)) {
            roomPredicates.add(new RoomNumberExactPredicate(argMultimap.getValue(PREFIX_ROOM).get()));
        }

        if (ParserUtil.arePrefixesPresent(argMultimap, PREFIX_ROOM_CAPACITY)) {
            roomPredicates.add(new RoomCapacityExactPredicate(argMultimap.getValue(PREFIX_ROOM_CAPACITY).get()));
        }

        if (ParserUtil.arePrefixesPresent(argMultimap, PREFIX_TAG)) {
            String[] tagsKeywords = argMultimap.getValue(PREFIX_TAG).get().trim().split("\\s+");
            roomPredicates.add(new RoomTagsContainsKeywordsPredicate(Arrays.asList(tagsKeywords)));
        }

        if (ParserUtil.arePrefixesPresent(argMultimap, FLAG_ROOM_HAS_BOOKINGS)) {
            roomPredicates.add(new RoomHasBookingsExactPredicate(true));
        } else if (ParserUtil.arePrefixesPresent(argMultimap, FLAG_ROOM_NO_BOOKINGS)) {
            roomPredicates.add(new RoomHasBookingsExactPredicate(false));
        }

        if (ParserUtil.arePrefixesPresent(argMultimap, PREFIX_DATE_START)) {
            String startDate = argMultimap.getValue(PREFIX_DATE_START).get();
            String endDate = argMultimap.getValue(PREFIX_DATE_END).get();

            try {
                roomPredicates.add(new RoomBookingsDateRangePredicate(new BookingPeriod(startDate, endDate)));
            } catch (IllegalArgumentException e) {
                throw new ParseException(
                        String.format(MESSAGE_BOOKING_SAME_DATE, FindCommand.MESSAGE_USAGE));
            }
        }
    }
}
