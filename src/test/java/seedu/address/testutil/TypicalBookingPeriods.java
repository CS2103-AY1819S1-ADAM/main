package seedu.address.testutil;

import seedu.address.model.room.booking.BookingPeriod;

import java.time.LocalDate;

/**
 * A utility class containing a list of {@code BookingPeriod} objects to be used in tests.
 */
public class TypicalBookingPeriods {
    public static final BookingPeriod JAN09_JAN10 = new BookingPeriod("09/01/2019", "10/01/2019");
    public static final BookingPeriod JAN09_JAN11 = new BookingPeriod("09/01/2019", "11/01/2019");
    public static final BookingPeriod JAN10_JAN11 = new BookingPeriod("10/01/2019", "11/01/2019");
    public static final BookingPeriod FEB09_FEB10 = new BookingPeriod("09/02/2019", "10/02/2019");
    public static final BookingPeriod FEB27_FEB28 = new BookingPeriod("27/02/2019", "28/02/2019");
    public static final BookingPeriod MAR30_MAR31 = new BookingPeriod("30/03/2019", "31/03/2019");
    public static final BookingPeriod TODAY_TOMORROW = new BookingPeriod(LocalDate.now().format(BookingPeriod.FORMAT), 
        LocalDate.now().plusDays(1).format(BookingPeriod.FORMAT));
    public static final BookingPeriod TODAY_NEXTWEEK = new BookingPeriod(LocalDate.now().format(BookingPeriod.FORMAT),
        LocalDate.now().plusWeeks(1).format(BookingPeriod.FORMAT));
    public static final BookingPeriod TOMORROW_NEXTWEEK = new BookingPeriod(
        LocalDate.now().plusDays(1).format(BookingPeriod.FORMAT),
        LocalDate.now().plusWeeks(1).format(BookingPeriod.FORMAT));
}
