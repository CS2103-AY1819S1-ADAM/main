package seedu.address.model.room.booking.exceptions;

/**
 * Signals that the reassignment operations involves:
 * 1) Old booking starts before new booking
 * 2) New booking is already checked-in
 */
public class OldBookingStartsBeforeNewBookingCheckedIn extends RuntimeException {
    public OldBookingStartsBeforeNewBookingCheckedIn() {
        super("Cannot reassign booking to new room, because old booking starts before new booking"
                + "and new room is currently checked in!");
    }
}
