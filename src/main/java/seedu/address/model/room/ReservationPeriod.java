package seedu.address.model.room;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * Represents a Room's reservation period in the address book.
 * Guarantees: immutable; is valid as declared in
 * {@link #isValidReservationPeriod(String testStartDate,String testEndDate)}
 */
public class ReservationPeriod {

    public static final String MESSAGE_RESERVATION_PERIOD_CONSTRAINTS =
            "Reservation dates should only contain two 8-digit numbers, each in the form dd/MM/yyyy, "
                + "should be correct dates according to the calendar, and should not be blank.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String RESERVATION_PERIOD_VALIDATION_REGEX =
        "^(0[1-9]|[1-2]\\d|3[0-1])\\/(0[1-9]|1[0-2])\\/(\\d\\d\\d\\d)$";

    /**
     * Standard date format used for this hotel.
     */
    public static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public final LocalDate startDate;
    public final LocalDate endDate;

    /**
     * Constructs a {@code ReservationPeriod} that encapsulates the period from start through end date (inclusive).
     */
    public ReservationPeriod(String startDate, String endDate) {
        requireNonNull(startDate);
        requireNonNull(endDate);
        checkArgument(isValidReservationPeriod(startDate, endDate), MESSAGE_RESERVATION_PERIOD_CONSTRAINTS);
        this.startDate = parseDate(startDate);
        this.endDate = parseDate(endDate);
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidReservationPeriod(String testStartDate, String testEndDate) {
        return (testStartDate.matches(RESERVATION_PERIOD_VALIDATION_REGEX)
            && testEndDate.matches(RESERVATION_PERIOD_VALIDATION_REGEX))
            && parsableDate(testStartDate)
            && parsableDate(testEndDate);
    }

    /**
     * Checks whether the given date can be constructed into {@code LocalDate} object.
     * @param date A date of the correct format.
     * @return True if given date is of correct format and can be constructed into LocalDate object, false otherwise.
     */
    private static boolean parsableDate(String date) {
        try {
            parseDate(date);
            return true;
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
        return LocalDate.parse(date, FORMAT);
    }

    /**
     * Checks if the start and end dates of this {@code ReservationPeriod} overlaps with the other.
     * @param other Other reservation period to be compared to.
     * @return True if there is any overlap, false otherwise.
     */
    public boolean isOverlapping(ReservationPeriod other) {
        return (isLessThanOrEqual(this.startDate, other.endDate)
            && isLessThanOrEqual(other.startDate, this.endDate));
    }

    /**
     * Checks if {@code LocalDate} date1 <= date2.
     * @param date1 First date to compare.
     * @param date2 Second date to compare.
     * @return True if date1 <= date2, false otherwise.
     */
    private static boolean isLessThanOrEqual(LocalDate date1, LocalDate date2) {
        return date1.compareTo(date2) <= 0;
    }

    @Override
    public String toString() {
        return String.format("%s - %s", startDate, endDate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReservationPeriod // instanceof handles nulls
                && startDate.equals(((ReservationPeriod) other).startDate) // state check
                && endDate.equals(((ReservationPeriod) other).endDate));
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }

}
