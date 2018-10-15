package seedu.address.testutil;

import seedu.address.model.room.booking.Booking;
import seedu.address.model.room.booking.Bookings;

/**
 * A utility class containing a list of {@code Booking} objects to be used in tests.
 */
public class TypicalBookings {
    public static final Booking LASTWEEK_YESTERDAY = new BookingBuilder()
        .withBookingPeriod(TypicalBookingPeriods.LASTWEEK_YESTERDAY).build();

    public static final Booking LASTWEEK_YESTERDAY_CHECKED_IN = new BookingBuilder()
        .withBookingPeriod(TypicalBookingPeriods.LASTWEEK_YESTERDAY)
        .withCheckIn(true).build();

    public static final Booking YESTERDAY_TODAY = new BookingBuilder()
        .withBookingPeriod(TypicalBookingPeriods.YESTERDAY_TODAY).build();

    public static final Booking TODAY_TOMORROW = new BookingBuilder()
        .withBookingPeriod(TypicalBookingPeriods.TODAY_TOMORROW).build();

    public static final Booking TODAY_NEXTWEEK = new BookingBuilder()
        .withBookingPeriod(TypicalBookingPeriods.TODAY_NEXTWEEK).build();

    public static final Booking TOMORROW_NEXTWEEK = new BookingBuilder()
        .withBookingPeriod(TypicalBookingPeriods.TOMORROW_NEXTWEEK).build();

    public static Bookings getTypicalBookingsLastWeekYesterday() {
        Bookings bookings = new Bookings();
        bookings.add(LASTWEEK_YESTERDAY);
        return bookings;
    }

    public static Bookings getTypicalBookingsLastWeekYesterdayCheckedIn() {
        Bookings bookings = new Bookings();
        bookings.add(LASTWEEK_YESTERDAY_CHECKED_IN);
        return bookings;
    }

    public static Bookings getTypicalBookingsYesterdayToday() {
        Bookings bookings = new Bookings();
        bookings.add(YESTERDAY_TODAY);
        return bookings;
    }

    public static Bookings getTypicalBookingsTodayTomorrow() {
        Bookings bookings = new Bookings();
        bookings.add(TODAY_TOMORROW);
        return bookings;
    }

    public static Bookings getTypicalBookingsTodayNextWeek() {
        Bookings bookings = new Bookings();
        bookings.add(TODAY_NEXTWEEK);
        return bookings;
    }

    public static Bookings getTypicalBookingsTomorrowNextWeek() {
        Bookings bookings = new Bookings();
        bookings.add(TOMORROW_NEXTWEEK);
        return bookings;
    }
}
