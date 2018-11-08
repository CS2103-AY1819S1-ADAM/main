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

public class RoomHasBookingsExactPredicateTest {

    @Test
    public void equals() {
        boolean firstPredicateHasBookings = true;
        boolean secondPredicateHasBookings = false;

        RoomHasBookingsExactPredicate firstPredicate = new RoomHasBookingsExactPredicate(firstPredicateHasBookings);
        RoomHasBookingsExactPredicate secondPredicate = new RoomHasBookingsExactPredicate(secondPredicateHasBookings);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RoomHasBookingsExactPredicate firstPredicateCopy =
                new RoomHasBookingsExactPredicate(true);
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
    public void test_hasBookings_returnsTrue() {
        TreeSet<Booking> setBooking = new TreeSet<Booking>();
        setBooking.add(new Booking(
                new GuestBuilder().withName("hello").build(), new BookingPeriod("01/02/2018", "02/02/2018")));
        Bookings someBookings = new Bookings(setBooking);
        RoomHasBookingsExactPredicate predicate = new RoomHasBookingsExactPredicate(true);
        assertTrue(predicate.test(new RoomBuilder().withBookings(someBookings.getSortedBookingsSet()).build()));
    }

    @Test
    public void test_noBookings_returnsFalse() {
        RoomHasBookingsExactPredicate predicate =
                new RoomHasBookingsExactPredicate(true);
        assertFalse(predicate.test(new RoomBuilder().build()));
    }
}
