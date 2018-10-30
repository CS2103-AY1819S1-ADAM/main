package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GUEST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM_CAPACITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM_HAS_BOOKINGS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM_NO_BOOKINGS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM_NUMBER;
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
import seedu.address.model.guest.GuestTagsExactKeywordPredicate;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomBookingsDateRangePredicate;
import seedu.address.model.room.RoomCapacityExactPredicate;
import seedu.address.model.room.RoomHasBookingsExactPredicate;
import seedu.address.model.room.RoomNumberExactPredicate;
import seedu.address.model.room.RoomTagsExactKeywordsPredicate;
import seedu.address.model.room.booking.BookingPeriod;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    public static final String MESSAGE_NO_FLAGS = "No flags found! \n%1$s";
    public static final String MESSAGE_WRONG_FLAG = "Wrong flag found! \n%1$s";
    public static final String MESSAGE_NO_FILTERS = "No specified filters found! \n%1$s";
    public static final String MESSAGE_NULL_FILTERS = "Null value in filters found! \n%1$s";
    public static final String MESSAGE_EXTRA_BOOKING_FLAG = "Extra bookings flag found! \n%1$s";
    public static final String MESSAGE_INCORRECT_PREFIX = "Incorrect prefix found! \n%1$s";
    public static final String MESSAGE_BOOKING_PERIOD_FORMAT = "Booking period format is wrong! \n%1$s";

    private final List<Predicate<Guest>> guestPredicates;
    private final List<Predicate<Room>> roomPredicates;

    public FindCommandParser() {
        guestPredicates = new LinkedList<Predicate<Guest>>();
        roomPredicates = new LinkedList<Predicate<Room>>();
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_NO_FLAGS, FindCommand.MESSAGE_USAGE));
        }

        String[] suffixFilters = trimmedArgs.split("\\s+");
        String flag = suffixFilters[0];

        if (suffixFilters.length < 2) {
            if (flag.equals(PREFIX_GUEST.toString()) || (flag.equals(PREFIX_ROOM.toString()))) {
                throw new ParseException(
                        String.format(MESSAGE_NO_FILTERS, FindCommand.MESSAGE_USAGE));
            } else {
                throw new ParseException(
                        String.format(MESSAGE_WRONG_FLAG, FindCommand.MESSAGE_USAGE));
            }
        }

        suffixFilters = Arrays.copyOfRange(suffixFilters, 1, suffixFilters.length);

        if (flag.equals(PREFIX_GUEST.toString())) {
            getGuestPredicates(suffixFilters);
            if (guestPredicates.size() == 0) {
                throw new ParseException(
                        String.format(MESSAGE_NO_FILTERS, FindCommand.MESSAGE_USAGE));
            }

        } else if (flag.equals(PREFIX_ROOM.toString())) {
            getRoomPredicates(suffixFilters);
            if (roomPredicates.size() == 0) {
                throw new ParseException(
                        String.format(MESSAGE_NO_FILTERS, FindCommand.MESSAGE_USAGE));
            }
        } else {
            throw new ParseException(
                    String.format(MESSAGE_WRONG_FLAG, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(flag, guestPredicates, roomPredicates);
    }

    /**
     * Handles the logic and creation of several predicates based on specified prefixes/flags by the user from
     * {@code suffixFilters} to find guests.
     * @throws ParseException if the user input does not conform the expected format
     */
    private void getGuestPredicates(String[] suffixFilters) throws ParseException {
        boolean isParsingName = false;
        List<String> nameKeywords = new LinkedList<String>();

        for (String filter : suffixFilters) {

            if (!isParsingName) {
                if (filter.length() <= 2) {
                    throw new ParseException(
                            String.format(MESSAGE_NULL_FILTERS, FindCommand.MESSAGE_USAGE));
                }

                if (!hasValidGuestFlagOrPrefix(filter)) {
                    throw new ParseException(
                            String.format(MESSAGE_INCORRECT_PREFIX, FindCommand.MESSAGE_USAGE));
                }
            } else {
                if (!hasValidGuestFlagOrPrefix(filter)) {
                    nameKeywords.add(filter);
                    continue;
                } else {
                    checkAndParseName(isParsingName, nameKeywords);
                    isParsingName = false;
                }
            }

            if (filter.toLowerCase().contains(PREFIX_NAME.toString().toLowerCase())) {
                nameKeywords.add(filter.substring(2));
                isParsingName = true;
                continue;
            } else {
                if (filter.toLowerCase().contains(PREFIX_TAG.toString().toLowerCase())) {
                    guestPredicates.add(new GuestTagsExactKeywordPredicate(filter.substring(2)));
                } else if (filter.toLowerCase().contains(PREFIX_EMAIL.toString().toLowerCase())) {
                    guestPredicates.add(new GuestEmailExactPredicate(filter.substring(2)));
                } else {
                    guestPredicates.add(new GuestPhoneExactPredicate(filter.substring(2)));
                }
            }
        }

        if (isParsingName) {
            guestPredicates.add(new GuestNameContainsKeywordsPredicate(nameKeywords));
        }
    }

    /**
     * Handles the logic and creation of several predicates based on specified prefixes/flags by the user from
     * {@code suffixFilters} to find rooms.
     * @throws ParseException if the user input does not conform the expected format
     */
    private void getRoomPredicates(String[] suffixFilters) throws ParseException {
        boolean bookingsFlagFound = false;
        boolean lookForDateRange = false;
        String startDateFilter = "";
        String endDateFilter = "";

        for (int i = 0; i < suffixFilters.length; i++) {
            if (suffixFilters[i].toLowerCase().contains(PREFIX_ROOM_HAS_BOOKINGS.toString().toLowerCase())) {
                if (!bookingsFlagFound) {
                    bookingsFlagFound = true;
                    lookForDateRange = true;
                    roomPredicates.add(new RoomHasBookingsExactPredicate(true));
                } else {
                    throw new ParseException(
                            String.format(MESSAGE_EXTRA_BOOKING_FLAG, FindCommand.MESSAGE_USAGE));
                }
            }

            if (suffixFilters[i].toLowerCase().contains(PREFIX_ROOM_NO_BOOKINGS.toString().toLowerCase())) {
                if (!bookingsFlagFound) {
                    bookingsFlagFound = true;
                    lookForDateRange = true;
                    roomPredicates.add(new RoomHasBookingsExactPredicate(false));
                } else {
                    throw new ParseException(
                            String.format(MESSAGE_EXTRA_BOOKING_FLAG, FindCommand.MESSAGE_USAGE));
                }
            }

            if (lookForDateRange) {
                if (i + 1 < suffixFilters.length) {
                    if (!suffixFilters[i + 1].toLowerCase().contains(PREFIX_DATE_START.toString().toLowerCase())) {
                        lookForDateRange = false;
                        continue;
                    }
                } else {
                    lookForDateRange = false;
                    continue;
                }

                if (i + 4 < suffixFilters.length) {
                    if (suffixFilters[i + 1].toLowerCase().contains(PREFIX_DATE_START.toString().toLowerCase())) {
                        startDateFilter = suffixFilters[i + 2];
                    } else {
                        throw new ParseException(
                                String.format(MESSAGE_BOOKING_PERIOD_FORMAT, FindCommand.MESSAGE_USAGE));
                    }

                    if (suffixFilters[i + 3].toLowerCase().contains(PREFIX_DATE_END.toString().toLowerCase())) {
                        endDateFilter = suffixFilters[i + 4];
                    } else {
                        throw new ParseException(
                                String.format(MESSAGE_BOOKING_PERIOD_FORMAT, FindCommand.MESSAGE_USAGE));
                    }

                    if (startDateFilter.isEmpty() || endDateFilter.isEmpty()) {
                        throw new ParseException(
                                String.format(MESSAGE_BOOKING_PERIOD_FORMAT, FindCommand.MESSAGE_USAGE));
                    } else {
                        try {
                            roomPredicates.add(new RoomBookingsDateRangePredicate(
                                    new BookingPeriod(startDateFilter, endDateFilter)));
                        } catch (IllegalArgumentException e) {
                            throw new ParseException(
                                    String.format(MESSAGE_BOOKING_PERIOD_FORMAT, FindCommand.MESSAGE_USAGE));
                        }
                        i += 4;
                    }
                } else {

                    throw new ParseException(
                            String.format(MESSAGE_BOOKING_PERIOD_FORMAT, FindCommand.MESSAGE_USAGE));
                }

                lookForDateRange = false;
                continue;
            } else {
                if (!hasValidRoomFlagOrPrefix(suffixFilters[i])) {
                    throw new ParseException(
                            String.format(MESSAGE_INCORRECT_PREFIX, FindCommand.MESSAGE_USAGE));
                }
            }

            if (suffixFilters[i].toLowerCase().contains(PREFIX_TAG.toString().toLowerCase())) {
                if (suffixFilters[i].substring(2).isEmpty()) {
                    throw new ParseException(
                            String.format(MESSAGE_NULL_FILTERS, FindCommand.MESSAGE_USAGE));
                }
                roomPredicates.add(new RoomTagsExactKeywordsPredicate(suffixFilters[i].substring(2)));
            }

            if (suffixFilters[i].toLowerCase().contains(PREFIX_ROOM_NUMBER.toString().toLowerCase())) {
                if (suffixFilters[i].substring(2).isEmpty()) {
                    throw new ParseException(
                            String.format(MESSAGE_NULL_FILTERS, FindCommand.MESSAGE_USAGE));
                }
                roomPredicates.add(new RoomNumberExactPredicate(suffixFilters[i].substring(2)));
            }

            if (suffixFilters[i].toLowerCase().contains(PREFIX_ROOM_CAPACITY.toString().toLowerCase())) {
                if (suffixFilters[i].substring(2).isEmpty()) {
                    throw new ParseException(
                            String.format(MESSAGE_NULL_FILTERS, FindCommand.MESSAGE_USAGE));
                }
                roomPredicates.add(new RoomCapacityExactPredicate(suffixFilters[i].substring(2)));
            }

            if (!hasValidRoomFlagOrPrefix(suffixFilters[i])) {
                throw new ParseException(
                        String.format(MESSAGE_INCORRECT_PREFIX, FindCommand.MESSAGE_USAGE));
            }
        }

    }

    /**
     * Checks whether string of name keywords are ready to be added into list of predicates.
     */
    private void checkAndParseName(boolean isParsingName, List<String> nameKeywords) {
        if (isParsingName && nameKeywords.size() > 0) {
            guestPredicates.add(new GuestNameContainsKeywordsPredicate(nameKeywords));
        }
    }

    /**
     * Checks whether {@code arg} contains a valid flag/prefix belonging to Guest class
     */
    private boolean hasValidGuestFlagOrPrefix(String arg) {
        return (arg.toLowerCase().contains(PREFIX_GUEST.toString().toLowerCase())
                || arg.toLowerCase().contains(PREFIX_EMAIL.toString().toLowerCase())
                || arg.toLowerCase().contains(PREFIX_PHONE.toString().toLowerCase())
                || arg.toLowerCase().contains(PREFIX_NAME.toString().toLowerCase())
                || arg.toLowerCase().contains(PREFIX_TAG.toString().toLowerCase()));
    }

    /**
     * Checks whether {@code arg} contains a valid flag/prefix belonging to Room class
     */
    private boolean hasValidRoomFlagOrPrefix(String arg) {
        return (arg.toLowerCase().contains(PREFIX_ROOM_CAPACITY.toString().toLowerCase())
                || arg.toLowerCase().contains(PREFIX_ROOM_NUMBER.toString().toLowerCase())
                || arg.toLowerCase().contains(PREFIX_TAG.toString().toLowerCase())
                || arg.toLowerCase().contains(PREFIX_ROOM_NO_BOOKINGS.toString().toLowerCase())
                || arg.toLowerCase().contains(PREFIX_ROOM_HAS_BOOKINGS.toString().toLowerCase())
                || arg.toLowerCase().contains(PREFIX_ROOM.toString().toLowerCase())
                || arg.toLowerCase().contains(PREFIX_DATE_START.toString().toLowerCase())
                || arg.toLowerCase().contains(PREFIX_DATE_END.toString().toLowerCase()));
    }
}
