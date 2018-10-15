package seedu.address.model.room.booking;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Objects;

import seedu.address.model.person.Guest;

/**
 * Represents a Booking of a room in the hotel.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Booking implements Comparable<Booking> {

    // Identity fields
    private final Guest guest;
    private final BookingPeriod bookingPeriod;
    private final Boolean checkIn;

    /**
     * Guest and BookingPeriod must be present and not null.
     */
    public Booking(Guest guest, BookingPeriod bookingPeriod) {
        requireAllNonNull(guest, bookingPeriod);
        this.guest = guest;
        this.bookingPeriod = bookingPeriod;
        this.checkIn = false;
    }

    /**
     * Every field must be present and not null.
     */
    public Booking(Guest guest, BookingPeriod bookingPeriod, boolean checkin) {
        requireAllNonNull(guest, bookingPeriod);
        this.guest = guest;
        this.bookingPeriod = bookingPeriod;
        this.checkIn = checkin;
    }

    /**
     * Copies the given Booking to this one
     */
    public Booking(Booking toBeCopied) {
        this.guest = toBeCopied.getGuest();
        this.bookingPeriod = toBeCopied.getBookingPeriod();
        this.checkIn = toBeCopied.isCheckedIn();
    }

    /**
     * Private constructor used to check in a booking
     * @param toBeCheckedIn Copy of the original booking
     * @param checkIn Flag for setting room check-in
     */
    private Booking(Booking toBeCheckedIn, boolean checkIn) {
        this.guest = toBeCheckedIn.getGuest();
        this.bookingPeriod = toBeCheckedIn.getBookingPeriod();
        this.checkIn = checkIn;
    }

    public Guest getGuest() {
        return guest;
    }

    public BookingPeriod getBookingPeriod() {
        return bookingPeriod;
    }

    public Boolean isCheckedIn() {
        return checkIn;
    }

    /**
     * Returns an edited booking that has been checked-in
     * This is needed to maintain immutability of objects in Concierge
     */
    public Booking checkIn() {
        return new Booking(this, true);
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
     * Checks if this booking is active or expired
     */
    public boolean isActiveOrExpired() {
        return isActive() || isExpired();
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
                && (otherBooking.isCheckedIn().equals(isCheckedIn()));
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
                .append(isCheckedIn());
        return builder.toString();
    }

    @Override
    public int compareTo(Booking other) {
        return bookingPeriod.compareTo(other.getBookingPeriod());
    }
}
