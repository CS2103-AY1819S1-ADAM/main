package seedu.address.model.room.booking;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
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
     * Guest and BookingPeriod must be present and not null.
     */
    public Booking(Guest guest, BookingPeriod bookingPeriod) {
        requireAllNonNull(guest, bookingPeriod);
        this.guest = guest;
        this.bookingPeriod = bookingPeriod;
        this.isCheckedIn = false;
    }

    /**
     * Private constructor used to check-in a booking
     */
    public Booking(Guest guest, BookingPeriod bookingPeriod, boolean isCheckedIn) {
        requireAllNonNull(guest, bookingPeriod);
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
     * Checks if this booking is upcoming.
     */
    public boolean isUpcoming() {
        return getBookingPeriod().isUpcoming();
    }

    /**
     * Checks if this booking includes the given date
     */
    public boolean includesDate(LocalDate date) {
        return getBookingPeriod().includesDate(date);
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
        builder.append("Guest: ")
            .append(getGuest().getName())
            .append("\nBooking period: ")
            .append(getBookingPeriod())
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
        builder.append("Guest: ")
                .append(getGuest())
                .append(" BookingPeriod: ")
                .append(getBookingPeriod())
                .append(" Checked-in: ")
                .append(getIsCheckedIn());
        return builder.toString();
    }

    @Override
    public int compareTo(Booking other) {
        return bookingPeriod.compareTo(other.getBookingPeriod());
    }
}
