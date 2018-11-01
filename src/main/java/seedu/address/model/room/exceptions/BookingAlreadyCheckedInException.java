package seedu.address.model.room.exceptions;

/**
 * Signals that the operation is attempting to check-in a guest into a booking that is already checked-in
 */
public class BookingAlreadyCheckedInException extends RuntimeException {
    public BookingAlreadyCheckedInException() {
        super("Cannot check-in a booking that is already checked-in.");
    }
}
