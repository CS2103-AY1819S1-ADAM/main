package cs2103.concierge.logic.commands;

import cs2103.concierge.commons.core.EventsCenter;
import cs2103.concierge.commons.events.ui.ExitAppRequestEvent;
import cs2103.concierge.model.Model;
import cs2103.concierge.logic.CommandHistory;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
