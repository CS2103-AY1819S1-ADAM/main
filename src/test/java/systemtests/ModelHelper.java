package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.person.Guest;
import seedu.address.model.room.Room;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {

    private static final Predicate<Guest> PREDICATE_MATCHING_NO_PERSONS = unused -> false;
    private static final Predicate<Room> PREDICATE_MATCHING_NO_ROOMS = unused -> false;

    /**
     * Updates {@code model}'s filtered guest list to display only {@code
     * toDisplay}.
     */
    public static void setFilteredGuestList(Model model, List<Guest> toDisplay) {
        Optional<Predicate<Guest>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatchingGuest).reduce(Predicate::or);
        model.updateFilteredPersonList(predicate.orElse(PREDICATE_MATCHING_NO_PERSONS));
    }

    /**
     * @see ModelHelper#setFilteredGuestList(Model, List)
     */
    public static void setFilteredGuestList(Model model, Guest... toDisplay) {
        setFilteredGuestList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Guest} equals to {@code other}.
     */
    private static Predicate<Guest> getPredicateMatchingGuest(Guest other) {
        return person -> person.equals(other);
    }

    /**
     * Updates {@code model}'s filtered room list to display only
     * {@code roomsToDisplay}.
     */
    public static void setFilteredRoomList(Model model,
                                           List<Room> roomsToDisplay) {
        Optional<Predicate<Room>> predicate =
                roomsToDisplay.stream().map(ModelHelper::getPredicateMatchingRoom).reduce(Predicate::or);
        model.updateFilteredRoomList(predicate.orElse(PREDICATE_MATCHING_NO_ROOMS));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Room} equals to
     * {@code other}.
     */
    private static Predicate<Room> getPredicateMatchingRoom(Room other) {
        return room -> room.equals(other);
    }
}
