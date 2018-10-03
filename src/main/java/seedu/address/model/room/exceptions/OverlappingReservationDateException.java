package seedu.address.model.room.exceptions;

/**
 * Signals that the operation will result in overlapping ReservationDates
 */
public class OverlappingReservationDateException extends RuntimeException {
    public OverlappingReservationDateException() {
        super("Operation would result in overlapping reservation dates.");
    }
}
