package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.exceptions.ExpiredBookingsFoundException;
import seedu.address.model.room.booking.exceptions.NoActiveBookingException;
import seedu.address.model.room.exceptions.OccupiedRoomCheckinException;

/**
 * Check in a room identified using its room number.
 */
public class CheckInCommand extends Command {

    public static final String COMMAND_WORD = "checkin";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Checks in the room identified by the room number.\n"
            + "Parameters: ROOM_NUMBER (must be a 3-digit positive integer from 001 to "
            + RoomNumber.MAX_ROOM_NUMBER + " )\n"
            + "Example: " + COMMAND_WORD + " 001";

    public static final String MESSAGE_CHECKIN_ROOM_SUCCESS = "Checked in Room: %1$s";
    public static final String MESSAGE_EXPIRED_BOOKINGS_FOUND =
        "Cannot check in Room %1$s, as it has expired bookings.";
    public static final String MESSAGE_NO_ACTIVE_BOOKING_CHECKIN =
        "Cannot check in Room %1$s, as it does not have an active booking.";
    public static final String MESSAGE_OCCUPIED_ROOM_CHECKIN =
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
        } catch (ExpiredBookingsFoundException e) {
            throw new CommandException(String.format(MESSAGE_EXPIRED_BOOKINGS_FOUND, roomNumber));
        } catch (NoActiveBookingException e) {
            throw new CommandException(String.format(MESSAGE_NO_ACTIVE_BOOKING_CHECKIN, roomNumber));
        } catch (OccupiedRoomCheckinException e) {
            throw new CommandException(String.format(MESSAGE_OCCUPIED_ROOM_CHECKIN, roomNumber));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CheckInCommand // instanceof handles nulls
                && roomNumber.equals(((CheckInCommand) other).roomNumber)); // state check
    }
}
