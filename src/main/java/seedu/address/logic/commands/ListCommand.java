package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GUEST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GUESTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ROOMS;
import static seedu.address.model.Model.PREDICATE_SHOW_NO_GUESTS;
import static seedu.address.model.Model.PREDICATE_SHOW_NO_ROOMS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all guests in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "List successful!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows a list of guest or rooms. "
            + "Parameters: "
            + PREFIX_GUEST + " for guests, "
            + PREFIX_ROOM + " for rooms. \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ROOM;

    private String[] splitString;

    /**
     * Creates a ListCommand to handle listing of guests/rooms and other flags
     */
    public ListCommand(String[] splitString) {
        //If statement for listing guests/rooms, switch statement not allowed
        requireNonNull(splitString);
        this.splitString = splitString;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        if (splitString[0].equals(PREFIX_GUEST.toString())) {
            model.updateFilteredGuestList(PREDICATE_SHOW_ALL_GUESTS);
            model.updateFilteredRoomList(PREDICATE_SHOW_NO_ROOMS);
        } else if (splitString[0].equals(PREFIX_ROOM.toString())) {
            model.updateFilteredGuestList(PREDICATE_SHOW_NO_GUESTS);
            model.updateFilteredRoomList(PREDICATE_SHOW_ALL_ROOMS);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
