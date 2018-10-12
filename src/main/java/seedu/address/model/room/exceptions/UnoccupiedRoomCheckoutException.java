package seedu.address.model.room.exceptions;

/**
 * Signals that the operation checked out an unoccupied room
 */
public class UnoccupiedRoomCheckoutException extends RuntimeException {
    public UnoccupiedRoomCheckoutException() {
        super("Operation would result in an unoccupied room being checked out.");
    }
}
