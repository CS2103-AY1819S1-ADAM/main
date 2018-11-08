package seedu.address.model.room;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.TreeSet;

import org.junit.Test;

import seedu.address.model.room.booking.Booking;
import seedu.address.model.room.booking.BookingPeriod;
import seedu.address.model.room.booking.Bookings;
import seedu.address.testutil.GuestBuilder;
import seedu.address.testutil.RoomBuilder;

public class RoomBookingDateRangePredicateTest {

    @Test
    public void equals() {
        BookingPeriod firstPredicateBookingPeriod = new BookingPeriod("01/02/2018", "02/02/2018");
        BookingPeriod secondPredicateBookingPeriod = new BookingPeriod("03/03/2018", "04/03/2018");

        RoomBookingsDateRangePredicate firstPredicate =
                new RoomBookingsDateRangePredicate(firstPredicateBookingPeriod);
        RoomBookingsDateRangePredicate secondPredicate =
                new RoomBookingsDateRangePredicate(secondPredicateBookingPeriod);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RoomBookingsDateRangePredicate firstPredicateCopy =
                new RoomBookingsDateRangePredicate(new BookingPeriod("01/02/2018", "02/02/2018"));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different guest -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // Same hashcode
        assertTrue(firstPredicate.hashCode() == firstPredicate.hashCode());

        // Different hashcode
        assertFalse(firstPredicate.hashCode() == secondPredicate.hashCode());
    }

    @Test
    public void test_sameBookingPeriod_returnsTrue() {
        TreeSet<Booking> setBooking = new TreeSet<Booking>();
        setBooking.add(new Booking(
                new GuestBuilder().withName("hello").build(), new BookingPeriod("01/02/2018", "02/02/2018")));
        Bookings someBookings = new Bookings(setBooking);
        RoomBookingsDateRangePredicate predicate =
                new RoomBookingsDateRangePredicate(new BookingPeriod("01/02/2018", "02/02/2018"));
        assertTrue(predicate.test(new RoomBuilder().withBookings(someBookings.getSortedBookingsSet()).build()));
    }

    @Test
    public void test_differentBookingPeriod_returnsFalse() {
        TreeSet<Booking> setBooking = new TreeSet<Booking>();
        setBooking.add(new Booking(
                new GuestBuilder().withName("hello").build(), new BookingPeriod("03/03/2018", "04/03/2018")));
        Bookings someBookings = new Bookings(setBooking);
        RoomBookingsDateRangePredicate predicate =
                new RoomBookingsDateRangePredicate(new BookingPeriod("01/02/2018", "02/02/2018"));
        assertFalse(predicate.test(new RoomBuilder().withBookings(someBookings.getSortedBookingsSet()).build()));
    }
}
