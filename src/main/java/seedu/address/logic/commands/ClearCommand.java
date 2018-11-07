package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.util.SampleDataUtil.getEmptyConcierge;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DeselectGuestListEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Clears Concierge.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Concierge has been cleared!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(getEmptyConcierge());
        EventsCenter.getInstance().post(new DeselectGuestListEvent());

        model.commitConcierge();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean requiresSignIn() {
        return true;
    }
}
