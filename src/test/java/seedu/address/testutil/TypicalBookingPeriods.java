package seedu.address.testutil;

import java.time.LocalDate;

import seedu.address.model.room.booking.BookingPeriod;

/**
 * A utility class containing a list of {@code BookingPeriod} objects to be used in tests.
 */
public class TypicalBookingPeriods {
    public static final BookingPeriod LASTWEEK_YESTERDAY = new BookingPeriod(
        LocalDate.now().minusWeeks(1).format(BookingPeriod.FORMAT),
        LocalDate.now().minusDays(1).format(BookingPeriod.FORMAT));
    public static final BookingPeriod YESTERDAY_TODAY = new BookingPeriod(
        LocalDate.now().minusDays(1).format(BookingPeriod.FORMAT),
        LocalDate.now().format(BookingPeriod.FORMAT));
    public static final BookingPeriod TODAY_TOMORROW = new BookingPeriod(
        LocalDate.now().format(BookingPeriod.FORMAT),
        LocalDate.now().plusDays(1).format(BookingPeriod.FORMAT));
    public static final BookingPeriod TODAY_NEXTWEEK = new BookingPeriod(
        LocalDate.now().format(BookingPeriod.FORMAT),
        LocalDate.now().plusWeeks(1).format(BookingPeriod.FORMAT));
    public static final BookingPeriod TOMORROW_NEXTWEEK = new BookingPeriod(
        LocalDate.now().plusDays(1).format(BookingPeriod.FORMAT),
        LocalDate.now().plusWeeks(1).format(BookingPeriod.FORMAT));
}
