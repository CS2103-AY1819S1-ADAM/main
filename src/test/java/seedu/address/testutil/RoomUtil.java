package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM;

import seedu.address.logic.commands.CheckInCommand;
import seedu.address.logic.commands.CheckoutCommand;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.BookingPeriod;

/**
 * A utility class for Room.
 */
public class RoomUtil {

    /**
     * Returns a check-in command string for checking in the room with the {@code roomNumber}
     */
    public static String getCheckInCommand(RoomNumber roomNumber) {
        return CheckInCommand.COMMAND_WORD + " " + getRoomDesc(roomNumber);
    }

    /**
     * Returns a checkout command string for checking in the room with the {@code roomNumber}
     */
    public static String getCheckoutCommand(RoomNumber roomNumber) {
        return CheckoutCommand.COMMAND_WORD + " " + getRoomDesc(roomNumber);
    }

    /**
     * Returns a checkout command string for checking in the room with the {@code roomNumber} and {@code bookingPeriod}
     */
    public static String getCheckoutCommandWithBookingPeriod(RoomNumber roomNumber, BookingPeriod bookingPeriod) {
        return getCheckoutCommand(roomNumber) + " " + getBookingPeriodDesc(bookingPeriod);
    }

    /**
     * Returns the part of command string for the given {@code roomNumber}.
     */
    public static String getRoomDesc(RoomNumber roomNumber) {
        return PREFIX_ROOM + " " + roomNumber.value;
    }
    /**
     * Returns the part of command string for the given {@code bookingPeriod}.
     */
    public static String getBookingPeriodDesc(BookingPeriod bookingPeriod) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DATE_START + bookingPeriod.getStartDateAsFormattedString());
        sb.append(" " + PREFIX_DATE_END + bookingPeriod.getEndDateAsFormattedString());
        return sb.toString();
    }

}
