package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.testutil.GuestUtil.getGuestDetails;
import static seedu.address.testutil.RoomUtil.getRoomDesc;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.guest.Guest;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.BookingPeriod;

/**
 * A utility class for {@code Booking}
 */
public class BookingUtil {

    /**
     * Returns an add command string for adding the {@code guest}.
     */
    public static String getAddCommand(Guest guest, RoomNumber roomNumber,
                                       BookingPeriod bookingPeriod) {
        return AddCommand.COMMAND_WORD + " " + getGuestDetails(guest) + " "
                + getRoomDesc(roomNumber) + " " + getBookingPeriodDesc(bookingPeriod);
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
