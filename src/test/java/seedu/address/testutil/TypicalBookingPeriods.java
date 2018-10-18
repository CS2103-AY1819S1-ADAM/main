package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_END_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_END_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_START_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_START_BOB;

import java.time.LocalDate;

import seedu.address.model.room.booking.BookingPeriod;

/**
 * A utility class containing a list of {@code BookingPeriod} objects to be used in tests.
 */
public class TypicalBookingPeriods {
    public static final BookingPeriod LASTWEEK_YESTERDAY = new BookingPeriod(
        LocalDate.now().minusWeeks(1).format(BookingPeriod.DATE_TO_STRING_FORMAT),
        LocalDate.now().minusDays(1).format(BookingPeriod.DATE_TO_STRING_FORMAT));
    public static final BookingPeriod YESTERDAY_TODAY = new BookingPeriod(
        LocalDate.now().minusDays(1).format(BookingPeriod.DATE_TO_STRING_FORMAT),
        LocalDate.now().format(BookingPeriod.DATE_TO_STRING_FORMAT));
    public static final BookingPeriod TODAY_TOMORROW = new BookingPeriod(
        LocalDate.now().format(BookingPeriod.DATE_TO_STRING_FORMAT),
        LocalDate.now().plusDays(1).format(BookingPeriod.DATE_TO_STRING_FORMAT));
    public static final BookingPeriod TODAY_NEXTWEEK = new BookingPeriod(
        LocalDate.now().format(BookingPeriod.DATE_TO_STRING_FORMAT),
        LocalDate.now().plusWeeks(1).format(BookingPeriod.DATE_TO_STRING_FORMAT));
    public static final BookingPeriod TOMORROW_NEXTWEEK = new BookingPeriod(
        LocalDate.now().plusDays(1).format(BookingPeriod.DATE_TO_STRING_FORMAT),
        LocalDate.now().plusWeeks(1).format(BookingPeriod.DATE_TO_STRING_FORMAT));

    // Manually added - BookingPeriod found in {@code CommandTestUtil}
    public static final BookingPeriod BOOKING_PERIOD_AMY =
            new BookingPeriod(VALID_DATE_START_AMY, VALID_DATE_END_AMY);
    public static final BookingPeriod BOOKING_PERIOD_BOB =
            new BookingPeriod(VALID_DATE_START_BOB, VALID_DATE_END_BOB);
}
