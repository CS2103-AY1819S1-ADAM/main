package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.GuestList;
import seedu.address.model.Model;

/**
 * Clears the guest list.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Guest list has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(new GuestList());
        model.commitGuestList();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
