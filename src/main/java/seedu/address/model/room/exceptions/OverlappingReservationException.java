package seedu.address.model.room.exceptions;

/**
 * Signals that the operation will result in overlapping Reservations
 */
public class OverlappingReservationException extends RuntimeException {
    public OverlappingReservationException() {
        super("Operation would result in overlapping reservation dates.");
    }
}
