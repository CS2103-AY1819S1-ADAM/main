package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.FLAG_GUEST;
import static seedu.address.logic.parser.CliSyntax.FLAG_ROOM;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.ListingChangedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.guest.Guest;
import seedu.address.model.room.Room;

/**
 * Finds and lists all rooms or guests in Concierge whose attributes matches
 * those keywords that are specified by the user. Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all guests or rooms with the specified "
            + "attributes and displays them as a list. There must be at least one attribute present.\n"
            + "Parameters: GUEST/ROOMFLAG ATTRIBUTES [MORE ATTRIBUTES]...\n"
            + "Example (Guest): " + COMMAND_WORD + " " + FLAG_GUEST + " n/Alex p/82542133 e/alex@gmail.com t/friend "
            + "t/colleagues \n"
            + "Example (Room): " + COMMAND_WORD + " " + FLAG_ROOM + " r/001 c/2 t/vacant -hb from/ 01/11/2018 to/ "
            + "03/11/2018 \n";

    private final List<Predicate<Guest>> guestPredicates;
    private final List<Predicate<Room>> roomPredicates;

    private final String flag;

    public FindCommand(String flag, List<Predicate<Guest>> guestPredicates,
                       List<Predicate<Room>> roomPredicates) {
        this.flag = flag;
        this.guestPredicates = guestPredicates;
        this.roomPredicates = roomPredicates;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        if (flag.equals(FLAG_GUEST.toString())) {
            Predicate<Guest> combinedGuestPredicate = combineGuestFilters();
            model.updateFilteredGuestList(combinedGuestPredicate);
            EventsCenter.getInstance().post(new ListingChangedEvent(FLAG_GUEST));

            return new CommandResult(
                    String.format(Messages.MESSAGE_GUESTS_LISTED_OVERVIEW, model.getFilteredGuestList().size()));
        } else {
            Predicate<Room> combinedRoomPredicate = combineRoomFilters();
            model.updateFilteredRoomList(combinedRoomPredicate);
            EventsCenter.getInstance().post(new ListingChangedEvent(FLAG_ROOM));

            return new CommandResult(
                    String.format(Messages.MESSAGE_ROOMS_LISTED_OVERVIEW, model.getFilteredRoomList().size()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && guestPredicates.equals(((FindCommand) other).guestPredicates)
                && roomPredicates.equals(((FindCommand) other).roomPredicates)); // state check
    }

    /**
     * Function to combine all the predicates in list of {@code guestPredicates}.
     */
    public Predicate<Guest> combineGuestFilters() {
        Predicate<Guest> predicates = guestPredicates.stream().reduce(Predicate::and).orElse(x-> true);
        return predicates;
    }

    /**
     * Function to combine all the predicates in list of {@code roomPredicates}.
     */
    public Predicate<Room> combineRoomFilters() {
        Predicate<Room> predicates = roomPredicates.stream().reduce(Predicate::and).orElse(x-> true);
        return predicates;
    }
}
