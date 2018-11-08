package seedu.address.model.room.booking.exceptions;

/**
 * Signals that there was an attempt to do an invalid operation on an expired booking.
 */
public class ExpiredBookingException extends RuntimeException {
    public ExpiredBookingException() {
        super("Invalid operation, as the booking has already expired.");
    }
}
