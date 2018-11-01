package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.commands.LogInCommand;

/**
 * Allows testing to use this typical log in to access "restricted" commands.
 * Currently, the restricted commands which require sign-in are:
 * {@code AddCommand}, {@code EditCommand}, {@code CheckInCommand},
 * {@code CheckOutCommand} and {@code ClearCommand}
 */
public class LogInUtil {

    private static final String validUsername = "testuser";
    private static final String validPassword = "password1";

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
        return getLogInCommand(validUsername, validPassword);
    }
}
