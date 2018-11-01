package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.login.InvalidLogInException;

/**
 * Allows the user to sign in to Concierge. The username and passwords are
 * case-sensitive.
 */
public class LogInCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Signs in to Concierge. Both username and password are case-sensitive."
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "themythz "
            + PREFIX_PASSWORD + "two1o3b3stmod.k";

    public static final String MESSAGE_SUCCESS = "Successfully signed in as: %1$s";
    public static final String MESSAGE_SIGNED_IN_ALREADY = "You are already signed in.";
    public static final String MESSAGE_NOT_SIGNED_IN = "This command requires you to sign in.";

    public final String userName;
    private final String hashedPassword;

    /**
     * Creates a LogInCommand to sign in as {@code userName} with password
     * {@code hashedPassword}.
     */
    public LogInCommand(String userName, String hashedPassword) {
        this.userName = userName;
        this.hashedPassword = hashedPassword;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.isSignedIn()) {
            throw new CommandException(MESSAGE_SIGNED_IN_ALREADY);
        }

        try {
            model.signIn(userName, hashedPassword);
        } catch (InvalidLogInException e) {
            throw new CommandException(e.getMessage());
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, userName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LogInCommand // instanceof handles nulls
                && userName.equals(((LogInCommand) other).userName));
    }
}
