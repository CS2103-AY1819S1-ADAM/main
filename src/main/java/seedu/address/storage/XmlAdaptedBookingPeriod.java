package seedu.address.storage;

import java.time.LocalDate;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.room.booking.BookingPeriod;

/**
 * JAXB-friendly version of the BookingPeriod.
 */
public class XmlAdaptedBookingPeriod {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "BookingPeriod's %s field is missing!";

    @XmlElement(required = true)
    private String startDate;
    @XmlElement(required = true)
    private String endDate;

    /**
     * Constructs an XmlAdaptedBookingPeriod.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedBookingPeriod() {}

    /**
     * Converts a given booking into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedBookingPeriod
     */
    public XmlAdaptedBookingPeriod(BookingPeriod source) {
        startDate = source.getStartDateAsFormattedString();
        endDate = source.getEndDateAsFormattedString();
    }

    /**
     * Converts this jaxb-friendly adapted booking object into the model's booking object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted booking
     */
    public BookingPeriod toModelType() throws IllegalValueException {

        if (startDate == null || endDate == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, LocalDate.class.getSimpleName()));
        }
        if (!BookingPeriod.parsableDate(startDate) || !BookingPeriod.parsableDate(endDate)) {
            throw new IllegalValueException(BookingPeriod.MESSAGE_BOOKING_PERIOD_CONSTRAINTS);
        }

        return new BookingPeriod(startDate, endDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedBookingPeriod)) {
            return false;
        }

        XmlAdaptedBookingPeriod otherbookingPeriod = (XmlAdaptedBookingPeriod) other;
        return Objects.equals(startDate, otherbookingPeriod.startDate)
                && Objects.equals(endDate, otherbookingPeriod.endDate);
    }
}
