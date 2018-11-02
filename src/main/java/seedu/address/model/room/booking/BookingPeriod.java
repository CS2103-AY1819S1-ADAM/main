package seedu.address.model.room.booking;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Objects;

/**
 * Represents a Room's booking period in Concierge.
 * Guarantees: immutable; is valid as declared in
 * {@link #isValidBookingPeriod(String testStartDate,String testEndDate)}
 */
public class BookingPeriod implements Comparable<BookingPeriod> {

    public static final String MESSAGE_BOOKING_PERIOD_CONSTRAINTS =
            "Booking period takes in 2 dates, each of which should be in the form day/month/year. "
                + "day can be 1 or 2 digits, month can be 1 or 2 digits, year can be 2 or 4 digits."
                + "\nAlso, dates should be valid dates according to the Gregorian calendar,"
                + "and should not be blank or the same date.";

    /**
     * Standard date format used for this hotel's bookings.
     */
    public static final DateTimeFormatter STRING_TO_DATE_FORMAT = DateTimeFormatter.ofPattern("d/M/[uuuu][uu]")
            .withResolverStyle(ResolverStyle.STRICT);
    public static final DateTimeFormatter DATE_TO_STRING_FORMAT = DateTimeFormatter.ofPattern("d/M/u")
            .withResolverStyle(ResolverStyle.STRICT);

    public final LocalDate startDate;
    public final LocalDate endDate;

    /**
     * Constructs a {@code BookingPeriod} that encapsulates the period from start through end date (inclusive).
     */
    public BookingPeriod(String startDate, String endDate) {
        requireAllNonNull(startDate, endDate);
        checkArgument(isValidBookingPeriod(startDate, endDate), MESSAGE_BOOKING_PERIOD_CONSTRAINTS);
        this.startDate = parseDate(startDate);
        this.endDate = parseDate(endDate);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getStartDateAsFormattedString() {
        return startDate.format(DATE_TO_STRING_FORMAT);
    }

    public String getEndDateAsFormattedString() {
        return endDate.format(DATE_TO_STRING_FORMAT);
    }

    /**
     * Returns true if given string can be parsed into LocalDates using the formatter,
     * and if startDate is strictly before endDate. Thus, startDate CANNOT BE EQUALS to endDate
     */
    public static boolean isValidBookingPeriod(String startDate, String endDate) {
        try {
            LocalDate start = parseDate(startDate);
            LocalDate end = parseDate(endDate);
            return start.isBefore(end);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Parses the given date to {@code }LocalDate} object.
     * @param date A date of the correct format.
     * @return {@code LocalDate} object representing the date.
     */
    private static LocalDate parseDate(String date) {
        return LocalDate.parse(date, STRING_TO_DATE_FORMAT);
    }

    /**
     * Checks if the start and end dates of this {@code BookingPeriod} overlaps with the other.
     * Note: There is NO OVERLAPPING if this period's start date coincides with another's end date.
     * @param other Other booking period to be compared to.
     * @return True if there is any overlap, false otherwise.
     */
    public boolean isOverlapping(BookingPeriod other) {
        return this.equals(other)
                || (startDate.isBefore(other.endDate) && other.startDate.isBefore(this.endDate));
    }

    /**
     * Checks if this booking period is expired.
     */
    public boolean isExpired() {
        return endDate.isBefore(LocalDate.now());
    }

    /**
     * Checks if this booking period is active now.
     */
    public boolean isActive() {
        LocalDate today = LocalDate.now();
        return includesDate(today);
    }

    /**
     * Checks if the given date is within this booking period's start and end dates, inclusively
     */
    public boolean includesDate(LocalDate date) {
        return date.compareTo(startDate) >= 0
            && date.compareTo(endDate) <= 0;
    }

    @Override
    public String toString() {
        String start = startDate.format(DATE_TO_STRING_FORMAT);
        String end = endDate.format(DATE_TO_STRING_FORMAT);
        return String.format("%s - %s", start, end);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookingPeriod // instanceof handles nulls
                && startDate.equals(((BookingPeriod) other).startDate) // state check
                && endDate.equals(((BookingPeriod) other).endDate));
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }

    @Override
    public int compareTo(BookingPeriod other) {
        int result = startDate.compareTo(other.startDate);
        if (result != 0) {
            return result;
        }
        return endDate.compareTo(other.endDate);
    }
}
