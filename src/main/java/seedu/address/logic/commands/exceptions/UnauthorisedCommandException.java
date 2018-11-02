package seedu.address.logic.commands.exceptions;

import seedu.address.logic.commands.LogInCommand;

/**
 * Represents the exception where the user is not authorised to execute the
 * command. That is, the user needs to sign in but is not signed in.
 */
public class UnauthorisedCommandException extends CommandException {

    public UnauthorisedCommandException() {
        super(LogInCommand.MESSAGE_NOT_SIGNED_IN);
    }
}
