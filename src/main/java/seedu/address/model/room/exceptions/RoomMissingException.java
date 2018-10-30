package seedu.address.model.room.exceptions;

/**
 * Signals that the operation will result in missing room(s)
 */
public class RoomMissingException extends RuntimeException {
    public RoomMissingException() {
        super("Operation would result in missing rooms.");
    }
}
