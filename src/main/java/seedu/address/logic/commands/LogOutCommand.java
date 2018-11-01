package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.login.InvalidLogOutException;

/**
 * Allows the user to sign out of Concierge.
 * The command history is erased and the user cannot undo or redo beyond
 * logout.
 */
public class LogOutCommand extends Command {

    public static final String COMMAND_WORD = "logout";
    public static final String MESSAGE_SUCCESS = "Successfully signed out of Concierge.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        try {
            model.signOut();
        } catch (InvalidLogOutException e) {
            throw new CommandException(e.getMessage());
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
