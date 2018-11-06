package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.FLAG_CHECKED_IN_GUEST;
import static seedu.address.logic.parser.CliSyntax.FLAG_GUEST;
import static seedu.address.logic.parser.CliSyntax.FLAG_ROOM;

import seedu.address.logic.commands.ListCommand;

/**
 * A utility class for listing.
 */
public class ListUtil {

    /**
     * Returns a list command string to list guests.
     */
    public static String getListGuestCommand() {
        return ListCommand.COMMAND_WORD + " " + FLAG_GUEST;
    }

    /**
     * Returns a list command string to list rooms.
     */
    public static String getListRoomCommand() {
        return ListCommand.COMMAND_WORD + " " + FLAG_ROOM;
    }

    /**
     * Returns a list command string to list checked-in guests.
     */
    public static String getListCheckedInGuestCommand() {
        return ListCommand.COMMAND_WORD + " " + FLAG_CHECKED_IN_GUEST;
    }
}
