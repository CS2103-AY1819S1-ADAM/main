package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.guest.Guest;
import seedu.address.model.room.Room;

/**
 * Selects a guest identified using it's displayed index from Concierge.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the guest identified by the index number used in the displayed guest list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_GUEST_SUCCESS = "Selected Guest: %1$s";
    public static final String MESSAGE_SELECT_CHECKED_IN_GUEST_SUCCESS = "Selected Checked-in Guest: %1$s";
    public static final String MESSAGE_SELECT_ROOM_SUCCESS = "Selected Room: %1$s";

    private final Index targetIndex;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Prefix displayedListFlag = model.getDisplayedListFlag();
        String successMessage;

        if (displayedListFlag.equals(CliSyntax.FLAG_GUEST)) {
            List<Guest> filteredGuestList = model.getFilteredGuestList();

            if (targetIndex.getZeroBased() >= filteredGuestList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_GUEST_DISPLAYED_INDEX);
            }
            successMessage = MESSAGE_SELECT_GUEST_SUCCESS;

        } else if (displayedListFlag.equals(CliSyntax.FLAG_CHECKED_IN_GUEST)) {
            List<Guest> filteredCheckedInGuestList = model.getFilteredCheckedInGuestList();

            if (targetIndex.getZeroBased() >= filteredCheckedInGuestList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_CHECKED_IN_GUEST_DISPLAYED_INDEX);
            }
            successMessage = MESSAGE_SELECT_CHECKED_IN_GUEST_SUCCESS;

        } else if (displayedListFlag.equals(CliSyntax.FLAG_ROOM)) {
            List<Room> filteredRoomList = model.getFilteredRoomList();

            if (targetIndex.getZeroBased() >= filteredRoomList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_ROOM_DISPLAYED_INDEX);
            }
            successMessage = MESSAGE_SELECT_ROOM_SUCCESS;

        } else {
            throw new CommandException(Messages.MESSAGE_NO_LIST_DISPLAYED);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex, displayedListFlag));
        return new CommandResult(String.format(successMessage, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
