package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedBooking.MISSING_FIELD_MESSAGE_FORMAT;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.guest.Guest;
import seedu.address.model.room.booking.Booking;
import seedu.address.model.room.booking.BookingPeriod;
import seedu.address.testutil.Assert;
import seedu.address.testutil.TypicalBookings;

/**
 * Note: XmlAdaptedBooking contains 3 fields:  XmlAdaptedGuest, XmlAdaptedBookingPeriod, and Boolean.
 * We do not test XmlAdaptedGuest and XmlAdaptedBookingPeriod because they are each tested in their own tests.
 * Furthermore, JAXB and Unmarshaller ensures for us that at the point of constructing XmlAdaptedBooking,
 * both XmlAdaptedGuest and XmlAdaptedBookingPeriod must already be valid.
 * We also do not test invalid isCheckedIn values, because JAXB and Unmarshaller can only parse true or false values
 * into Boolean, meaning all other values will trigger an exception there, not here.
 * TODO The test for Boolean isCheckedIn will be covered under XmlUtilTest.
 */
public class XmlAdaptedBookingTest {

    private static final Booking VALID_BOOKING = TypicalBookings.TODAY_TOMORROW;
    private static final Guest VALID_GUEST = VALID_BOOKING.getGuest();
    private static final BookingPeriod VALID_BOOKING_PERIOD = VALID_BOOKING.getBookingPeriod();
    private static final Boolean VALID_CHECK_IN = VALID_BOOKING.getIsCheckedIn();

    @Test
    public void toModelType_validBookingDetails_returnsBooking() throws Exception {
        XmlAdaptedBooking booking = new XmlAdaptedBooking(VALID_GUEST, VALID_BOOKING_PERIOD, VALID_CHECK_IN);
        assertEquals(VALID_BOOKING, booking.toModelType());
    }

    @Test
    public void toModelType_nullIsCheckedIn_throwsIllegalValueException() {
        XmlAdaptedBooking booking = new XmlAdaptedBooking(VALID_GUEST, VALID_BOOKING_PERIOD, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Boolean.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, booking::toModelType);
    }

}
