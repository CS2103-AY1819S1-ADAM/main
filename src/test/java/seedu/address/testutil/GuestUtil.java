package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.guest.Guest;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.BookingPeriod;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Guest.
 */
public class GuestUtil {

    /**
     * Returns an add command string for adding the {@code guest}.
     */
    public static String getAddCommand(Guest guest, RoomNumber roomNumber,
                                       BookingPeriod bookingPeriod) {
        return AddCommand.COMMAND_WORD + " " + getGuestDetails(guest) + " "
                + getRoomDesc(roomNumber) + " " + getBookingPeriodDesc(bookingPeriod);
    }

    /**
     * Returns the part of command string for the given {@code roomNumber}.
     */
    public static String getRoomDesc(RoomNumber roomNumber) {
        return PREFIX_ROOM + " " + roomNumber.value;
    }
    /**
     * Returns the part of command string for the given {@code bookingPeriod}.
     */
    public static String getBookingPeriodDesc(BookingPeriod bookingPeriod) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DATE_START + bookingPeriod.getStartDateAsFormattedString());
        sb.append(" " + PREFIX_DATE_END + bookingPeriod.getEndDateAsFormattedString());
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code guest}'s details.
     */
    public static String getGuestDetails(Guest guest) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + guest.getName().fullName + " ");
        sb.append(PREFIX_PHONE + guest.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + guest.getEmail().value + " ");
        guest.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditGuestDescriptor}'s details.
     */
    public static String getEditGuestDescriptorDetails(EditCommand.EditGuestDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
