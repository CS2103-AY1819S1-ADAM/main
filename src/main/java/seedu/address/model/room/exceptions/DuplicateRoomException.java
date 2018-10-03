package seedu.address.model.room.exceptions;

/**
 * Signals that the operation will result in duplicate Rooms
 */
public class DuplicateRoomException extends RuntimeException {
    public DuplicateRoomException() {
        super("Operation would result in duplicate rooms.");
    }
}
