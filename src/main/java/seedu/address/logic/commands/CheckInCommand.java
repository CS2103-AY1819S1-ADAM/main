package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.exceptions.BookingAlreadyCheckedInException;
import seedu.address.model.room.booking.exceptions.ExpiredBookingException;
import seedu.address.model.room.booking.exceptions.InactiveBookingCheckInException;
import seedu.address.model.room.booking.exceptions.NoBookingException;

/**
 * Check in a room identified using its room number.
 */
public class CheckInCommand extends Command {

    public static final String COMMAND_WORD = "checkin";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Checks in the first active booking of the room identified by the room number.\n"
            + Messages.MESSAGE_VALID_ROOM
            + "Parameters: " + PREFIX_ROOM + "ROOM_NUMBER\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_ROOM + "r/001";

    public static final String MESSAGE_CHECKIN_ROOM_SUCCESS = "Checked in Room: %1$s";
    public static final String MESSAGE_NO_BOOKING_CHECK_IN =
        "Cannot check in Room %1$s, as it has no bookings.";
    public static final String MESSAGE_EXPIRED_BOOKING_CHECK_IN =
        "Cannot check in Room %1$s, as it has expired bookings.";
    public static final String MESSAGE_INACTIVE_BOOKING_CHECK_IN =
        "Cannot check in Room %1$s, as it does not have an active booking.";
    public static final String MESSAGE_BOOKING_ALREADY_CHECKED_IN =
        "Cannot check in Room %1$s, as it is already checked in.";

    private final RoomNumber roomNumber;

    public CheckInCommand(RoomNumber roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try {
            model.checkInRoom(roomNumber);
            model.commitConcierge();
            return new CommandResult(String.format(MESSAGE_CHECKIN_ROOM_SUCCESS, roomNumber));
        } catch (NoBookingException e) {
            throw new CommandException(String.format(MESSAGE_NO_BOOKING_CHECK_IN, roomNumber));
        } catch (ExpiredBookingException e) {
            throw new CommandException(String.format(MESSAGE_EXPIRED_BOOKING_CHECK_IN, roomNumber));
        } catch (InactiveBookingCheckInException e) {
            throw new CommandException(String.format(MESSAGE_INACTIVE_BOOKING_CHECK_IN, roomNumber));
        } catch (BookingAlreadyCheckedInException e) {
            throw new CommandException(String.format(MESSAGE_BOOKING_ALREADY_CHECKED_IN, roomNumber));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CheckInCommand // instanceof handles nulls
                && roomNumber.equals(((CheckInCommand) other).roomNumber)); // state check
    }
}
