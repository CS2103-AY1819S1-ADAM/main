package seedu.address.model.room.exceptions;

/**
 * Signals that the operation is attempting to check in a guest into an occupied room
 */
public class OccupiedRoomCheckinException extends RuntimeException {
    public OccupiedRoomCheckinException() {
        super("Operation is attempting to checkin an occupied room.");
    }
}
