package seedu.address.logic.autocomplete;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_ROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CheckInCommand;
import seedu.address.logic.commands.CheckoutCommand;
import seedu.address.logic.commands.LogInCommand;
import seedu.address.logic.commands.ReassignCommand;
import seedu.address.logic.commands.ServiceCommand;
import seedu.address.logic.parser.Prefix;

/**
 * Contains command Syntax definitions for multiple commands
 */
public class CommandParameterSyntaxHandler {
    public static final ArrayList<Prefix> ADD_COMMAND_PREFIXES = getListOfPrefix(PREFIX_NAME,
        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ROOM, PREFIX_DATE_START, PREFIX_DATE_END, PREFIX_TAG);

    public static final ArrayList<Prefix> CHECKIN_COMMAND_PREFIXES = getListOfPrefix(PREFIX_ROOM);

    public static final ArrayList<Prefix> CHECKOUT_COMMAND_PREFIXES = getListOfPrefix(PREFIX_ROOM,
        PREFIX_DATE_START, PREFIX_DATE_END);

    public static final ArrayList<Prefix> REASSIGN_COMMAND_PREFIXES = getListOfPrefix(PREFIX_ROOM,
        PREFIX_DATE_START, PREFIX_NEW_ROOM);

    public static final ArrayList<Prefix> LOGIN_COMMAND_PREFIXES = getListOfPrefix(PREFIX_USERNAME,
        PREFIX_PASSWORD);

    public static final ArrayList<Prefix> SERVICE_COMMAND_PREFIXES = getListOfPrefix(PREFIX_ROOM,
        PREFIX_ITEM_NUMBER, PREFIX_COST);

    /**
     * Returns ArrayList of prefixes from given prefixes
     */
    private static ArrayList<Prefix> getListOfPrefix(Prefix... prefixes) {
        return new ArrayList<>(Arrays.asList(prefixes));
    }

    /**
     * Returns ArrayList of missing prefixes based on a String Command and current user text input
     */
    public ArrayList<Prefix> getMissingPrefixes(String command, String input) {
        ArrayList<Prefix> missingPrefixes = new ArrayList<>();

        switch (command) {

        case AddCommand.COMMAND_WORD:
            ADD_COMMAND_PREFIXES.forEach(prefix -> {
                if (!input.contains(prefix.getPrefix())) {
                    missingPrefixes.add(prefix);
                }
            });
            break;

        case CheckInCommand.COMMAND_WORD:
            CHECKIN_COMMAND_PREFIXES.forEach(prefix -> {
                if (!input.contains(prefix.getPrefix())) {
                    missingPrefixes.add(prefix);
                }
            });
            break;

        case CheckoutCommand.COMMAND_WORD:
            CHECKOUT_COMMAND_PREFIXES.forEach(prefix -> {
                if (!input.contains(prefix.getPrefix())) {
                    missingPrefixes.add(prefix);
                }
            });
            break;

        case LogInCommand.COMMAND_WORD:
            LOGIN_COMMAND_PREFIXES.forEach(prefix -> {
                if (!input.contains(prefix.getPrefix())) {
                    missingPrefixes.add(prefix);
                }
            });
            break;

        case ReassignCommand.COMMAND_WORD:
            REASSIGN_COMMAND_PREFIXES.forEach(prefix -> {
                if (!input.contains(prefix.getPrefix())) {
                    missingPrefixes.add(prefix);
                }
            });
            break;

        case ServiceCommand.COMMAND_WORD:
            SERVICE_COMMAND_PREFIXES.forEach(prefix -> {
                if (!input.contains(prefix.getPrefix())) {
                    missingPrefixes.add(prefix);
                }
            });
            break;

        default:
            break;
        }
        return missingPrefixes;
    }

}
