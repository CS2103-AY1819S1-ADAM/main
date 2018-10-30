package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.FLAG_GUEST;
import static seedu.address.logic.parser.CliSyntax.FLAG_ROOM;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GUESTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ROOMS;
import static seedu.address.model.Model.PREDICATE_SHOW_NO_GUESTS;
import static seedu.address.model.Model.PREDICATE_SHOW_NO_ROOMS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ListingChangedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all guests in Concierge to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "List successful!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows a list of guest or rooms. "
            + "Parameters: "
            + FLAG_GUEST + " for guests, "
            + FLAG_ROOM + " for rooms. \n"
            + "Example: " + COMMAND_WORD + " "
            + FLAG_ROOM;

    private final String[] splitString;

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

        if (splitString[0].equals(FLAG_GUEST.toString())) {
            model.updateFilteredGuestList(PREDICATE_SHOW_ALL_GUESTS);
            model.updateFilteredRoomList(PREDICATE_SHOW_NO_ROOMS);
            EventsCenter.getInstance().post(new ListingChangedEvent(FLAG_GUEST.toString()));

        } else if (splitString[0].equals(FLAG_ROOM.toString())) {
            model.updateFilteredGuestList(PREDICATE_SHOW_NO_GUESTS);
            model.updateFilteredRoomList(PREDICATE_SHOW_ALL_ROOMS);
            EventsCenter.getInstance().post(new ListingChangedEvent(FLAG_ROOM.toString()));
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && splitString[0].equals(((ListCommand) other).splitString[0]));
    }

}
