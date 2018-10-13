package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.room.RoomNumber;

/**
 * Check out a room identified using its room number and remove its registered guest from the guest list.
 */
public class CheckoutCommand extends Command {

    public static final String COMMAND_WORD = "checkout";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Checks out the room identified by the room number, and remove its registered guest from guest list.\n"
            + "Parameters: ROOM_NUMBER (must be a 3-digit positive integer from 001 to 100)\n"
            + "Example: " + COMMAND_WORD + " 001";

    public static final String MESSAGE_CHECKOUT_ROOM_SUCCESS = "Checked out Room: %1$s";
    public static final String MESSAGE_UNOCCUPIED_ROOM_CHECKOUT = "Cannot checkout Room %1$s, as it is not checked-in" +
        " yet.";
    public static final String MESSAGE_NO_ACTIVE_BOOKING_ROOM_CHECKOUT = "Cannot checkout Room %1$s, as it does not " +
        "have an active booking.";

    private final RoomNumber roomNumber;

    public CheckoutCommand(RoomNumber roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        // roomNumber is guaranteed to be a valid room number after parsing.
        if (!model.getUniqueRoomList().isRoomBookingActive(roomNumber)) {
            throw new CommandException(String.format(MESSAGE_NO_ACTIVE_BOOKING_ROOM_CHECKOUT, roomNumber));
        }
        if (!model.getUniqueRoomList().isRoomCheckedIn(roomNumber)) {
            throw new CommandException(String.format(MESSAGE_UNOCCUPIED_ROOM_CHECKOUT, roomNumber));
        }        
        model.checkoutRoom(roomNumber);
        model.commitRoomList();
        return new CommandResult(String.format(MESSAGE_CHECKOUT_ROOM_SUCCESS, roomNumber));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CheckoutCommand // instanceof handles nulls
                && roomNumber.equals(((CheckoutCommand) other).roomNumber)); // state check
    }
}
