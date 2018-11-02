package seedu.address.model.room.booking.exceptions;

/**
 * Signals that there was an attempt to check-in an inactive booking.
 */
public class InactiveBookingCheckInException extends RuntimeException {
    public InactiveBookingCheckInException() {
        super("Cannot check-in a booking that is not active.");
    }
}
