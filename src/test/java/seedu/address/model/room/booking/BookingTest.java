package seedu.address.model.room.booking;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalBookingPeriods.YESTERDAY_TODAY;
import static seedu.address.testutil.TypicalBookings.LASTWEEK_YESTERDAY;
import static seedu.address.testutil.TypicalBookings.TODAY_TOMORROW;
import static seedu.address.testutil.TypicalGuests.BOB;

import org.junit.Test;

import seedu.address.testutil.Assert;
import seedu.address.testutil.BookingBuilder;

public class BookingTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Booking(null, null));
        Assert.assertThrows(NullPointerException.class, () -> new Booking(null, null, null));
    }

    @Test
    public void checkIn() {
        Booking checkedInBooking = TODAY_TOMORROW.checkIn();
        assertTrue(checkedInBooking.getIsCheckedIn());

        checkedInBooking = LASTWEEK_YESTERDAY.checkIn();
        assertTrue(checkedInBooking.getIsCheckedIn());
    }

    @Test
    public void isSameBooking() {
        assertTrue(LASTWEEK_YESTERDAY.isSameBooking(LASTWEEK_YESTERDAY));
        assertFalse(LASTWEEK_YESTERDAY.isSameBooking(null));
        assertFalse(LASTWEEK_YESTERDAY.isSameBooking(TODAY_TOMORROW));

        // diff guest -> false
        Booking editedBooking = new BookingBuilder(LASTWEEK_YESTERDAY).withGuest(BOB).build();
        assertFalse(LASTWEEK_YESTERDAY.isSameBooking(editedBooking));

        // diff booking period -> false
        editedBooking = new BookingBuilder(LASTWEEK_YESTERDAY).withBookingPeriod(YESTERDAY_TODAY).build();
        assertFalse(LASTWEEK_YESTERDAY.isSameBooking(editedBooking));

        // diff check-in status -> true
        editedBooking = new BookingBuilder(LASTWEEK_YESTERDAY).withCheckIn(true).build();
        assertTrue(LASTWEEK_YESTERDAY.isSameBooking(editedBooking));
    }

    @Test
    public void equals() {
        assertTrue(LASTWEEK_YESTERDAY.equals(LASTWEEK_YESTERDAY));
        assertFalse(LASTWEEK_YESTERDAY.equals(null));
        assertFalse(LASTWEEK_YESTERDAY.equals(TODAY_TOMORROW));

        Booking editedBooking = new BookingBuilder(LASTWEEK_YESTERDAY).withGuest(BOB).build();
        assertFalse(LASTWEEK_YESTERDAY.equals(editedBooking));

        editedBooking = new BookingBuilder(LASTWEEK_YESTERDAY).withBookingPeriod(YESTERDAY_TODAY).build();
        assertFalse(LASTWEEK_YESTERDAY.equals(editedBooking));

        // diff check-in status -> false
        editedBooking = new BookingBuilder(LASTWEEK_YESTERDAY).withCheckIn(true).build();
        assertFalse(LASTWEEK_YESTERDAY.equals(editedBooking));
    }
}
