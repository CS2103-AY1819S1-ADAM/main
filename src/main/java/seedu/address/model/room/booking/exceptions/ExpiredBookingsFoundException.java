package seedu.address.model.room.booking.exceptions;

/**
 * Signals that there was an expired booking.
 */
public class ExpiredBookingsFoundException extends RuntimeException {
    public ExpiredBookingsFoundException() {
        super("Expired bookings were found.");
    }
}
