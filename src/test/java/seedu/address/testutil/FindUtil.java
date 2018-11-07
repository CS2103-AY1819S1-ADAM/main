package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.FLAG_GUEST;
import static seedu.address.logic.parser.CliSyntax.FLAG_ROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM_CAPACITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.FindCommand;

/**
 * A utility class for finding.
 * @author adamwth
 */
public class FindUtil {

    /**
     * Returns a find command string to find guests by their name.
     */
    public static String getFindGuestCommand(String name) {
        return FindCommand.COMMAND_WORD
                + " " + FLAG_GUEST
                + " " + PREFIX_NAME + name;
    }

    /**
     * Returns a find command string to find rooms by the given specified keyword(s).
     */
    public static String getFindRoomCommand(String roomNumber, String capacity, String tag,
                                            String hasBookingFlag, String noBookingFlag,
                                            String startDate, String endDate) {
        return FindCommand.COMMAND_WORD
                + " " + FLAG_ROOM
                + " " + (roomNumber != null ? PREFIX_ROOM + roomNumber : "")
                + " " + (capacity != null ? PREFIX_ROOM_CAPACITY + capacity : "")
                + " " + (tag != null ? PREFIX_TAG + tag : "")
                + " " + (hasBookingFlag != null ? hasBookingFlag : "")
                + " " + (noBookingFlag != null ? noBookingFlag : "")
                + " " + (startDate != null ? PREFIX_DATE_START + startDate : "")
                + " " + (endDate != null ? PREFIX_DATE_END + endDate : "");
    }

}
