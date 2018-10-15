package seedu.address.model.room.booking.exceptions;

/**
 * Signals that the operation is unable to find the specified booking date.
 */
public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException() {
        super("No such booking found.");
    }
}
