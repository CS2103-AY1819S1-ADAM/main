package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.commands.LogInCommand;
import seedu.address.logic.commands.LogOutCommand;

/**
 * Allows testing to use this typical log in to access "restricted" commands.
 * Currently, the restricted commands which require sign-in are:
 * {@code AddCommand}, {@code CheckInCommand}, {@code CheckOutCommand}
 * {@code ReassignCommand}, {@code ServiceCommand} and {@code ClearCommand}.
 */
public class LogInUtil {

    public static final String VALID_USERNAME = "testuser";
    public static final String VALID_PASSWORD = "passw0rd1";

    public static String getLogInCommand(String username, String validPassword) {
        StringBuilder sb = new StringBuilder();
        sb.append(LogInCommand.COMMAND_WORD);
        sb.append(" ");
        sb.append(PREFIX_USERNAME);
        sb.append(username);
        sb.append(" ");
        sb.append(PREFIX_PASSWORD);
        sb.append(validPassword);

        return sb.toString();
    }

    public static String getValidLogInCommand() {
        return getLogInCommand(VALID_USERNAME, VALID_PASSWORD);
    }

    public static String getLogOutCommand() {
        return LogOutCommand.COMMAND_WORD;
    }
}
