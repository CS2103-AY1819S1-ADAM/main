package seedu.address.model.room.booking.exceptions;

/**
 * Signals that the reassignment operations involves:
 * 1) New booking starts before old booking
 * 2) Old booking is already checked-in
 */
public class NewBookingStartsBeforeOldBookingCheckedIn extends RuntimeException {
    public NewBookingStartsBeforeOldBookingCheckedIn() {
        super("Cannot reassign booking to new room, because new booking starts before old booking"
                + "and old room is currently checked in!");
    }
}
