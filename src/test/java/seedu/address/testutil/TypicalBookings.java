package seedu.address.testutil;

import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.model.room.booking.Booking;

/**
 * A utility class containing a list of {@code Booking} objects to be used in tests.
 */
public class TypicalBookings {

    public static final Booking LASTWEEK_YESTERDAY = new BookingBuilder()
        .withBookingPeriod(TypicalBookingPeriods.LASTWEEK_YESTERDAY).build();

    public static final Booking LASTWEEK_YESTERDAY_CHECKED_IN = new BookingBuilder()
        .withBookingPeriod(TypicalBookingPeriods.LASTWEEK_YESTERDAY)
        .withCheckIn(true)
        .build();

    public static final Booking YESTERDAY_TODAY = new BookingBuilder()
        .withBookingPeriod(TypicalBookingPeriods.YESTERDAY_TODAY).build();

    public static final Booking TODAY_TOMORROW = new BookingBuilder()
        .withBookingPeriod(TypicalBookingPeriods.TODAY_TOMORROW).build();

    public static final Booking TODAY_TOMORROW_CHECKED_IN = new BookingBuilder()
        .withBookingPeriod(TypicalBookingPeriods.TODAY_TOMORROW)
        .withCheckIn(true)
        .build();

    public static final Booking TODAY_NEXTWEEK = new BookingBuilder()
        .withBookingPeriod(TypicalBookingPeriods.TODAY_NEXTWEEK).build();

    public static final Booking TOMORROW_NEXTWEEK = new BookingBuilder()
        .withBookingPeriod(TypicalBookingPeriods.TOMORROW_NEXTWEEK).build();

    private static Booking[] getTypicalBookings() {
        return new Booking[] {
            LASTWEEK_YESTERDAY,
            LASTWEEK_YESTERDAY_CHECKED_IN,
            YESTERDAY_TODAY,
            TODAY_TOMORROW_CHECKED_IN,
            TODAY_TOMORROW,
            TODAY_NEXTWEEK,
            TOMORROW_NEXTWEEK
        };
    }

    /* ============== Bookings with single bookings ========================================================== */
    public static SortedSet<Booking> getTypicalBookingsLastWeekYesterday() {
        SortedSet<Booking> bookings = new TreeSet<>();
        bookings.add(LASTWEEK_YESTERDAY);
        return bookings;
    }

    public static SortedSet<Booking> getTypicalBookingsLastWeekYesterdayCheckedIn() {
        SortedSet<Booking> bookings = new TreeSet<>();
        bookings.add(LASTWEEK_YESTERDAY_CHECKED_IN);
        return bookings;
    }

    public static SortedSet<Booking> getTypicalBookingsYesterdayToday() {
        SortedSet<Booking> bookings = new TreeSet<>();
        bookings.add(YESTERDAY_TODAY);
        return bookings;
    }

    public static SortedSet<Booking> getTypicalBookingsTodayTomorrow() {
        SortedSet<Booking> bookings = new TreeSet<>();
        bookings.add(TODAY_TOMORROW);
        return bookings;
    }

    public static SortedSet<Booking> getTypicalBookingsTodayTomorrowCheckedIn() {
        SortedSet<Booking> bookings = new TreeSet<>();
        bookings.add(TODAY_TOMORROW_CHECKED_IN);
        return bookings;
    }

    public static SortedSet<Booking> getTypicalBookingsTodayNextWeek() {
        SortedSet<Booking> bookings = new TreeSet<>();
        bookings.add(TODAY_NEXTWEEK);
        return bookings;
    }

    public static SortedSet<Booking> getTypicalBookingsTomorrowNextWeek() {
        SortedSet<Booking> bookings = new TreeSet<>();
        bookings.add(TOMORROW_NEXTWEEK);
        return bookings;
    }

    /* ============== Bookings with multiple bookings ========================================================== */
    public static SortedSet<Booking> getMultipleBookingsSet1() {
        SortedSet<Booking> bookings = new TreeSet<>();
        bookings.add(LASTWEEK_YESTERDAY);
        bookings.add(YESTERDAY_TODAY);
        bookings.add(TODAY_TOMORROW);
        bookings.add(TOMORROW_NEXTWEEK);
        return bookings;
    }

    public static SortedSet<Booking> getMultipleBookingsSet2() {
        SortedSet<Booking> bookings = new TreeSet<>();
        bookings.add(LASTWEEK_YESTERDAY);
        bookings.add(YESTERDAY_TODAY);
        bookings.add(TODAY_TOMORROW_CHECKED_IN);
        bookings.add(TOMORROW_NEXTWEEK);
        return bookings;
    }

}
