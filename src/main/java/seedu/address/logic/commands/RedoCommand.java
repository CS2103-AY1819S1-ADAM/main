package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GUESTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ROOMS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DeselectGuestListEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reverts the {@code model}'s Concierge to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoConcierge()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        EventsCenter.getInstance().post(new DeselectGuestListEvent());

        model.redoConcierge();
        model.updateFilteredGuestList(PREDICATE_SHOW_ALL_GUESTS);
        model.updateFilteredCheckedInGuestList(PREDICATE_SHOW_ALL_GUESTS);
        model.updateFilteredRoomList(PREDICATE_SHOW_ALL_ROOMS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
