package seedu.address.model.room.booking.exceptions;

/**
 * Exception to indicate that the room is not checked in.
 */
public class RoomNotCheckedInException extends RuntimeException {
    public RoomNotCheckedInException() {
        super("There are no guests checked in to the room.");
    }
}
