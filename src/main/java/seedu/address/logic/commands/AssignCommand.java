package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Guest;
import seedu.address.model.room.RoomNumber;

/**
 * Assigns a guest to a room.
 */
public class AssignCommand extends Command {

    public static final String COMMAND_WORD = "assign";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Assigns a guest to a room.\n"
            + "Parameters: GUEST_NAME ROOM_NUMBER\n"
            + "Example: " + COMMAND_WORD + " Thomas 056";

    public static final String MESSAGE_ASSIGN_GUEST_SUCCESS =
            "Assigned Guest: %1$s to Room %1$s";

    private final Index targetIndex;
    private final RoomNumber roomNumber;

    public AssignCommand(Index targetIndex, RoomNumber roomNumber) {
        this.targetIndex = targetIndex;
        this.roomNumber = roomNumber;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Guest> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Guest guestToAssign = lastShownList.get(targetIndex.getZeroBased());
        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_ASSIGN_GUEST_SUCCESS, guestToAssign));
    }

    @Override
    public boolean equals(Object other) {

        if (other == this) {
            return true;
        }

        if (!(other instanceof AssignCommand)) {
            return false;
        }

        AssignCommand otherAssignCommand = (AssignCommand) other;
        return otherAssignCommand.targetIndex.equals(targetIndex)
                && otherAssignCommand.roomNumber.equals(roomNumber);
    }
}
