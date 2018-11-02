package seedu.address.model.room.booking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalBookings.LASTWEEK_YESTERDAY;
import static seedu.address.testutil.TypicalBookings.TODAY_NEXTWEEK;
import static seedu.address.testutil.TypicalBookings.TODAY_TOMORROW;
import static seedu.address.testutil.TypicalBookings.TOMORROW_NEXTWEEK;
import static seedu.address.testutil.TypicalGuests.BOB;

import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.room.booking.exceptions.BookingNotFoundException;
import seedu.address.model.room.booking.exceptions.NoBookingException;
import seedu.address.model.room.booking.exceptions.OverlappingBookingException;
import seedu.address.testutil.BookingBuilder;

public class BookingsTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Bookings bookings = new Bookings();

    @Test
    public void constructor_sameBookingPeriod_throwsOverlappingBookingException() {
        SortedSet<Booking> bookingsSet = new TreeSet<>();
        bookingsSet.add(TODAY_TOMORROW);
        bookingsSet.add(new BookingBuilder(TODAY_TOMORROW).withGuest(BOB).build());
        thrown.expect(OverlappingBookingException.class);
        new Bookings(bookingsSet);
    }

    @Test
    public void constructor_overlappingBooking_throwsOverlappingBookingException() {
        SortedSet<Booking> bookingsSet = new TreeSet<>();
        bookingsSet.add(TODAY_TOMORROW);
        bookingsSet.add(TODAY_NEXTWEEK);
        thrown.expect(OverlappingBookingException.class);
        new Bookings(bookingsSet);
    }

    @Test
    public void getFirstBooking_noBooking_throwsNoBookingException() {
        thrown.expect(NoBookingException.class);
        bookings.getFirstBooking();
    }

    @Test
    public void getActiveBooking_returnsCorrectOptional_success() {
        Optional<Booking> optionalActiveBooking = bookings.getFirstActiveBooking();
        assertFalse(optionalActiveBooking.isPresent());

        optionalActiveBooking = bookings.add(LASTWEEK_YESTERDAY).getFirstActiveBooking();
        assertFalse(optionalActiveBooking.isPresent());

        optionalActiveBooking = bookings.add(TODAY_TOMORROW).getFirstActiveBooking();
        assertTrue(optionalActiveBooking.isPresent());
    }

    @Test
    public void add_nullBooking_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        bookings.add(null);
    }

    @Test
    public void add_overlappingBookingSame_throwsOverlappingBookingException() {
        Bookings editedBookings = bookings.add(TODAY_TOMORROW);
        thrown.expect(OverlappingBookingException.class);
        editedBookings.add(TODAY_TOMORROW);
    }

    @Test
    public void add_overlappingBookingDifferent_throwsOverlappingBookingException() {
        Bookings editedBookings = bookings.add(TODAY_TOMORROW);
        thrown.expect(OverlappingBookingException.class);
        editedBookings.add(TODAY_NEXTWEEK);
    }

    @Test
    public void updateBooking_nullTargetBooking_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        bookings.updateBooking(null, TODAY_TOMORROW);
    }

    @Test
    public void updateBooking_nullEditedBooking_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        bookings.updateBooking(TODAY_TOMORROW, null);
    }

    @Test
    public void updateBooking_targetBookingNotInList_throwsBookingNotFoundException() {
        thrown.expect(BookingNotFoundException.class);
        bookings.updateBooking(TODAY_TOMORROW, TODAY_TOMORROW);
    }

    @Test
    public void updateBooking_editedBookingIsSameBooking_success() {
        Bookings editedBookings = bookings.add(TODAY_TOMORROW).updateBooking(TODAY_TOMORROW, TODAY_TOMORROW);
        Bookings expectedbookings = new Bookings().add(TODAY_TOMORROW);
        assertEquals(expectedbookings, editedBookings);
    }

    @Test
    public void updateBooking_editedBookingHasSameIdentity_success() {
        Booking editedBookingPeriod = new BookingBuilder(TODAY_TOMORROW).withCheckIn(true).build();
        Bookings editedBookings = bookings.add(TODAY_TOMORROW).updateBooking(TODAY_TOMORROW, editedBookingPeriod);
        Bookings expectedbookings = new Bookings().add(editedBookingPeriod);
        assertEquals(expectedbookings, editedBookings);
    }

    @Test
    public void updateBooking_editedBookingHasDifferentIdentity_success() {
        Booking editedBookingPeriod = new BookingBuilder(TOMORROW_NEXTWEEK).build();
        Bookings editedBookings = bookings.add(TODAY_TOMORROW).updateBooking(TODAY_TOMORROW, editedBookingPeriod);
        Bookings expectedbookings = new Bookings().add(editedBookingPeriod);
        assertEquals(expectedbookings, editedBookings);
    }

    @Test
    public void updateBooking_editedBookingHasNonUniqueIdentity_throwsOverlappingBookingException() {
        Bookings editedBookings = bookings.add(TODAY_TOMORROW).add(TOMORROW_NEXTWEEK);
        thrown.expect(OverlappingBookingException.class);
        editedBookings.updateBooking(TODAY_TOMORROW, TOMORROW_NEXTWEEK);
    }

    @Test
    public void remove_nullBooking_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        bookings.remove(null);
    }

    @Test
    public void remove_bookingDoesNotExist_throwsBookingNotFoundException() {
        thrown.expect(BookingNotFoundException.class);
        bookings.remove(TODAY_TOMORROW);
    }

    @Test
    public void remove_existingBooking_removesBooking() {
        Bookings editedBookings = bookings.add(TODAY_TOMORROW).remove(TODAY_TOMORROW);
        Bookings expectedbookings = new Bookings();
        assertEquals(expectedbookings, editedBookings);
    }

}
