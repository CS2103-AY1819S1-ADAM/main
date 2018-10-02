package cs2103.concierge.logic.commands;

import static java.util.Objects.requireNonNull;

import cs2103.concierge.model.AddressBook;
import cs2103.concierge.model.Model;
import cs2103.concierge.logic.CommandHistory;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(new AddressBook());
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
