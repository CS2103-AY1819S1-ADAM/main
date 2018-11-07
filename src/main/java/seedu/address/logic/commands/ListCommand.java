package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.FLAG_CHECKED_IN_GUEST;
import static seedu.address.logic.parser.CliSyntax.FLAG_GUEST;
import static seedu.address.logic.parser.CliSyntax.FLAG_ROOM;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ListingChangedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;

/**
 * Lists all guests in Concierge to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "List successful!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows a list of guests or rooms. "
            + "Parameters: "
            + FLAG_GUEST + " for archived guests, "
            + FLAG_CHECKED_IN_GUEST + " for checked-in guests. "
            + FLAG_ROOM + " for rooms. \n"
            + "Example: " + COMMAND_WORD + " "
            + FLAG_ROOM;

    private final Prefix flag;

    /**
     * Creates a ListCommand to handle listing of guests/rooms and other flags
     */
    public ListCommand(Prefix flag) {
        requireNonNull(flag);
        this.flag = flag;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        // flag is guaranteed to be valid by the parser (FLAG_GUEST, FLAG_ROOM, OR FLAG_CHECKED_IN_GUEST)
        if (flag.equals(FLAG_GUEST)) {
            model.updateFilteredGuestList(Model.PREDICATE_SHOW_ALL_GUESTS);
        } else if (flag.equals(FLAG_ROOM)) {
            model.updateFilteredRoomList(Model.PREDICATE_SHOW_ALL_ROOMS);
        } else { // no need to check if here, because it is guaranteed to be FLAG_CHECKED_IN_GUEST at this stage
            model.updateFilteredCheckedInGuestList(Model.PREDICATE_SHOW_ALL_GUESTS);
        }
        model.setDisplayedListFlag(flag);
        EventsCenter.getInstance().post(new ListingChangedEvent(flag));
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && flag.equals(((ListCommand) other).flag));
    }

}
