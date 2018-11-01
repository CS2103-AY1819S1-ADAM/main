package seedu.address.model.room.booking.exceptions;

/**
 * Signals that there was an attempt to check-in an expired booking
 */
public class ExpiredBookingCheckInException extends RuntimeException {
    public ExpiredBookingCheckInException() {
        super("Cannot check-in an expired booking.");
    }
}
