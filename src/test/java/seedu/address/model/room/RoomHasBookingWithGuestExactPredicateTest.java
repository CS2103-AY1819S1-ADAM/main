package seedu.address.model.room;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import org.junit.Test;

import seedu.address.model.guest.Name;
import seedu.address.model.room.booking.Booking;
import seedu.address.model.room.booking.BookingPeriod;
import seedu.address.model.room.booking.Bookings;
import seedu.address.testutil.GuestBuilder;
import seedu.address.testutil.RoomBuilder;

public class RoomHasBookingWithGuestExactPredicateTest {

    @Test
    public void equals() {
        List<Name> firstPredicateKeywordList = Collections.singletonList(new Name("first"));
        List<Name> secondPredicateKeywordList = Arrays.asList(new Name("first"), new Name("second"));

        RoomHasBookingWithGuestExactPredicate firstPredicate =
                new RoomHasBookingWithGuestExactPredicate(firstPredicateKeywordList);
        RoomHasBookingWithGuestExactPredicate secondPredicate =
                new RoomHasBookingWithGuestExactPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RoomHasBookingWithGuestExactPredicate firstPredicateCopy =
                new RoomHasBookingWithGuestExactPredicate(Collections.singletonList(new Name("first")));
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
    public void test_hasGuestInBooking_returnsTrue() {
        TreeSet<Booking> setBooking = new TreeSet<Booking>();
        Booking currentBooking = new Booking(
                new GuestBuilder().withName("Tim").build(), new BookingPeriod("01/02/2018", "02/02/2018"));
        setBooking.add(currentBooking);
        Bookings someBookings = new Bookings(setBooking);
        RoomHasBookingWithGuestExactPredicate predicate =
                new RoomHasBookingWithGuestExactPredicate(Collections.singletonList(new Name("Tim")));
        assertTrue(predicate.test(new RoomBuilder().withBookings(someBookings.getSortedBookingsSet()).build()));
    }

    @Test
    public void test_guestNotFoundInBooking_returnsFalse() {
        TreeSet<Booking> setBooking = new TreeSet<Booking>();
        Booking currentBooking = new Booking(
                new GuestBuilder().withName("Tom").build(), new BookingPeriod("01/02/2018", "02/02/2018"));
        setBooking.add(currentBooking);
        Bookings someBookings = new Bookings(setBooking);
        RoomHasBookingWithGuestExactPredicate predicate =
                new RoomHasBookingWithGuestExactPredicate(Collections.singletonList(new Name("Tim")));
        assertFalse(predicate.test(new RoomBuilder().withBookings(someBookings.getSortedBookingsSet()).build()));
    }
}
