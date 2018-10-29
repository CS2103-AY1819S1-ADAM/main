package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.BookingPeriod;
import seedu.address.model.room.booking.exceptions.BookingNotFoundException;
import seedu.address.model.room.booking.exceptions.NoBookingException;

/**
 * Check out a room identified using its room number and remove its registered guest from the guest list.
 */
public class CheckoutCommand extends Command {

    public static final String COMMAND_WORD = "checkout";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Checks out the room identified by the room number, and remove its registered guest from guest list.\n"
            + "Optionally, you can specify a booking period to specify which booking to checkout (i.e. delete)."
            + "If you do not specify a booking period, the first (i.e. earliest) booking will be checked out."
            + "Booking periods must be in dd/MM/yy or dd/MM/yyyy format.\n"
            + "Parameters: " + PREFIX_ROOM + "ROOM_NUMBER (must be a 3-digit positive integer from 001 to "
            + RoomNumber.MAX_ROOM_NUMBER + ") "
            + "[" + PREFIX_DATE_START + "START_DATE " + PREFIX_DATE_END + "END_DATE]"
            + "\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_ROOM + "001 "
            + PREFIX_DATE_START + "01/01/18 " + PREFIX_DATE_END + "02/01/18";

    public static final String MESSAGE_CHECKOUT_ROOM_SUCCESS = "Checked out Room: %1$s";
    public static final String MESSAGE_NO_ROOM_BOOKING = "Room %1$s has no bookings.";
    public static final String MESSAGE_BOOKING_NOT_FOUND = "Room %1$s has no such bookings with booking period %2$s.";

    private final RoomNumber roomNumber;
    private final BookingPeriod bookingPeriod;

    public CheckoutCommand(RoomNumber roomNumber) {
        this(roomNumber, null);
    }

    public CheckoutCommand(RoomNumber roomNumber, BookingPeriod bookingPeriod) {
        this.roomNumber = roomNumber;
        this.bookingPeriod = bookingPeriod;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try {
            if (bookingPeriod == null) {
                model.checkoutRoom(roomNumber);
            } else {
                model.checkoutRoom(roomNumber, bookingPeriod);
            }
            model.commitConcierge();
            return new CommandResult(String.format(MESSAGE_CHECKOUT_ROOM_SUCCESS, roomNumber));
        } catch (NoBookingException e) {
            throw new CommandException(String.format(MESSAGE_NO_ROOM_BOOKING, roomNumber));
        } catch (BookingNotFoundException e) {
            throw new CommandException(String.format(MESSAGE_BOOKING_NOT_FOUND, roomNumber, bookingPeriod));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CheckoutCommand // instanceof handles nulls
                && roomNumber.equals(((CheckoutCommand) other).roomNumber)); // state check
    }
}
