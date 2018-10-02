package cs2103.concierge.logic.commands;

import cs2103.concierge.commons.core.EventsCenter;
import cs2103.concierge.commons.events.ui.ShowHelpRequestEvent;
import cs2103.concierge.model.Model;
import cs2103.concierge.logic.CommandHistory;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }
}
