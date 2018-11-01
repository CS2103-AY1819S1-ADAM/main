package seedu.address.model.room.booking;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.guest.Guest;

/**
 * Represents a Booking of a room in the hotel.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Booking implements Comparable<Booking> {

    // Identity fields
    private final Guest guest;
    private final BookingPeriod bookingPeriod;
    private final Boolean isCheckedIn;

    /**
     * Guest and BookingPeriod must be present and not null. Constructs a non-checked-in booking by default
     */
    public Booking(Guest guest, BookingPeriod bookingPeriod) {
        this(guest, bookingPeriod, false);
    }

    /**
     * Constructs a booking with specified guest, booking period and checked in status
     */
    public Booking(Guest guest, BookingPeriod bookingPeriod, Boolean isCheckedIn) {
        requireAllNonNull(guest, bookingPeriod, isCheckedIn);
        this.guest = guest;
        this.bookingPeriod = bookingPeriod;
        this.isCheckedIn = isCheckedIn;
    }

    public Guest getGuest() {
        return guest;
    }

    public BookingPeriod getBookingPeriod() {
        return bookingPeriod;
    }

    public Boolean getIsCheckedIn() {
        return isCheckedIn;
    }

    /**
     * Returns an edited booking that has been checked-in
     * This is needed to maintain immutability of objects in Concierge
     */
    public Booking checkIn() {
        return new Booking(this.getGuest(), this.getBookingPeriod(), true);
    }

    /**
     * Checks if the {@code Booking} overlaps with the other.
     */
    public boolean isOverlapping(Booking other) {
        return bookingPeriod.isOverlapping(other.getBookingPeriod());
    }

    /**
     * Checks if this booking is expired.
     */
    public boolean isExpired() {
        return getBookingPeriod().isExpired();
    }

    /**
     * Checks if this booking is active.
     */
    public boolean isActive() {
        return getBookingPeriod().isActive();
    }

    /**
     * Returns true if both bookings have the same guest and booking period.
     * This defines a weaker notion of equality between two rooms, as it does not need checkIn to be the same.
     */
    public boolean isSameBooking(Booking otherBooking) {
        if (otherBooking == this) {
            return true;
        }

        return otherBooking != null
            && otherBooking.getGuest().equals(getGuest())
            && otherBooking.getBookingPeriod().equals(getBookingPeriod());
    }

    /**
     * Returns true if both bookings have the same identity and data fields.
     * This defines a stronger notion of equality between two bookings.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Booking)) {
            return false;
        }

        Booking otherBooking = (Booking) other;
        return otherBooking.getGuest().equals(getGuest())
            && otherBooking.getBookingPeriod().equals(getBookingPeriod())
            && (otherBooking.getIsCheckedIn().equals(getIsCheckedIn()));
    }


    /**
     * Gets the short description of this room, which comprises of
     * 1) Guest's name
     * 2) Booking period
     * 3) Checked-in status
     */
    public String toStringShortDescription() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Booking period: ")
            .append(getBookingPeriod())
            .append("\nGuest: ")
            .append(getGuest().getName())
            .append("\nChecked-in: ")
            .append(getIsCheckedIn() ? "Yes" : "No");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(guest, bookingPeriod);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Booking period: ")
                .append(getBookingPeriod())
                .append("\nGuest: ")
                .append(getGuest())
                .append("\nChecked-in: ")
                .append(getIsCheckedIn() ? "Yes" : "No")
                .append("\n");
        return builder.toString();
    }

    @Override
    public int compareTo(Booking other) {
        int result = bookingPeriod.compareTo(other.getBookingPeriod());
        if (result != 0) {
            return result;
        }
        result = guest.compareTo(other.guest);
        if (result != 0) {
            return result;
        }
        return isCheckedIn.compareTo(other.isCheckedIn);
    }
}
