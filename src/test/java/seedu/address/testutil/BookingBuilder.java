package seedu.address.testutil;

import java.time.LocalDate;

import seedu.address.model.person.Guest;
import seedu.address.model.room.booking.Booking;
import seedu.address.model.room.booking.BookingPeriod;

/**
 * A utility class to help with building Booking objects.
 */
public class BookingBuilder {

    public static final Guest DEFAULT_GUEST = new GuestBuilder().build();
    public static final BookingPeriod DEFAULT_BOOKING_PERIOD_TODAY_TOMORROW =
        new BookingPeriod(LocalDate.now().format(BookingPeriod.DATE_TO_STRING_FORMAT),
            LocalDate.now().plusDays(1).format(BookingPeriod.DATE_TO_STRING_FORMAT));
    public static final Boolean DEFAULT_CHECKIN = false;

    private Guest guest;
    private BookingPeriod bookingPeriod;
    private Boolean checkIn;

    public BookingBuilder() {
        guest = DEFAULT_GUEST;
        bookingPeriod = DEFAULT_BOOKING_PERIOD_TODAY_TOMORROW;
        checkIn = DEFAULT_CHECKIN;
    }

    /**
     * Initializes the BookingBuilder with the data of {@code bookingToCopy}.
     */
    public BookingBuilder(Booking bookingToCopy) {
        guest = bookingToCopy.getGuest();
        bookingPeriod = bookingToCopy.getBookingPeriod();
        checkIn = bookingToCopy.isCheckedIn();
    }

    /**
     * Sets the {@code Guest} of the {@code Booking} that we are building.
     */
    public BookingBuilder withGuest(Guest guest) {
        this.guest = new Guest(guest);
        return this;
    }

    /**
     * Sets the {@code BookingPeriod} of the {@code Booking} that we are building.
     */
    public BookingBuilder withBookingPeriod(BookingPeriod bookingPeriod) {
        this.bookingPeriod = new BookingPeriod(bookingPeriod);
        return this;
    }

    /**
     * Sets the {@code Boolean} of the {@code Booking} that we are building.
     */
    public BookingBuilder withCheckIn(boolean checkIn) {
        this.checkIn = checkIn;
        return this;
    }

    public Booking build() {
        return new Booking(guest, bookingPeriod, checkIn);
    }
}
