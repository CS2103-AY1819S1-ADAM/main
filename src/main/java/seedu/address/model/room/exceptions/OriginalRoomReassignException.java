package seedu.address.model.room.exceptions;

/**
 * Signals that the operation is attempting to reassign a booking back to the original room
 */
public class OriginalRoomReassignException extends RuntimeException {
    public OriginalRoomReassignException() {
        super("Cannot reassign booking to the original room");
    }
}
