package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_GUESTS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_ROOMS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.FLAG_GUEST;
import static seedu.address.logic.parser.CliSyntax.FLAG_ROOM;
import static seedu.address.testutil.TypicalConcierge.getTypicalConciergeClean;
import static seedu.address.testutil.TypicalGuests.CARL;
import static seedu.address.testutil.TypicalGuests.ELLE;
import static seedu.address.testutil.TypicalGuests.FIONA;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.guest.Guest;
import seedu.address.model.guest.GuestNameContainsKeywordsPredicate;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomNumberExactPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalConciergeClean(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalConciergeClean(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        List<Predicate<Guest>> firstGuestPredicates = new LinkedList<>();
        List<Predicate<Guest>> secondGuestPredicates = new LinkedList<>();
        List<Predicate<Guest>> emptyGuestPredicates = new LinkedList<>();
        List<Predicate<Room>> firstRoomPredicates = new LinkedList<>();
        List<Predicate<Room>> secondRoomPredicates = new LinkedList<>();
        List<Predicate<Room>> emptyRoomPredicates = new LinkedList<>();

        firstGuestPredicates.add(new GuestNameContainsKeywordsPredicate(Collections.singletonList("first")));
        secondGuestPredicates.add(new GuestNameContainsKeywordsPredicate(Collections.singletonList("second")));
        firstRoomPredicates.add(new RoomNumberExactPredicate("001"));
        secondRoomPredicates.add(new RoomNumberExactPredicate("002"));

        FindCommand findFirstGuestCommand = new FindCommand(FLAG_GUEST.toString(),
                firstGuestPredicates, emptyRoomPredicates);
        FindCommand findSecondGuestCommand = new FindCommand(FLAG_GUEST.toString(),
                secondGuestPredicates, emptyRoomPredicates);
        FindCommand findFirstRoomCommand = new FindCommand(FLAG_ROOM.toString(),
                emptyGuestPredicates, firstRoomPredicates);
        FindCommand findSecondRoomCommand = new FindCommand(FLAG_ROOM.toString(),
                emptyGuestPredicates, secondRoomPredicates);

        // same object -> returns true
        assertTrue(findFirstGuestCommand.equals(findFirstGuestCommand));

        // same values -> returns true
        FindCommand findFirstGuestCommandCopy = new FindCommand(FLAG_GUEST.toString(),
                firstGuestPredicates, emptyRoomPredicates);
        assertTrue(findFirstGuestCommand.equals(findFirstGuestCommandCopy));

        // different types -> returns false
        assertFalse(findFirstGuestCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstGuestCommand.equals(null));

        // different guest -> returns false
        assertFalse(findFirstGuestCommand.equals(findSecondRoomCommand));

        // same object -> returns true
        assertTrue(findFirstRoomCommand.equals(findFirstRoomCommand));

        // same values -> returns true
        FindCommand findFirstRoomCommandCopy = new FindCommand(FLAG_ROOM.toString(),
                emptyGuestPredicates, firstRoomPredicates);
        assertTrue(findFirstRoomCommand.equals(findFirstRoomCommandCopy));

        // different types -> returns false
        assertFalse(findFirstRoomCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstRoomCommand.equals(null));

        // different guest -> returns false
        assertFalse(findFirstRoomCommand.equals(findSecondRoomCommand));
    }

    @Test
    public void execute_zeroKeywords_noGuestFound() {
        String expectedMessage = String.format(MESSAGE_GUESTS_LISTED_OVERVIEW, 0);
        GuestNameContainsKeywordsPredicate predicate = prepareGuestNamePredicate(" ");
        List<Predicate<Guest>> guestListPredicates = new LinkedList<>();
        guestListPredicates.add(predicate);
        FindCommand command = new FindCommand(FLAG_GUEST.toString(), guestListPredicates, null);
        expectedModel.updateFilteredGuestList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredGuestList());
    }

    @Test
    public void execute_multipleKeywords_multipleGuestsFound() {
        String expectedMessage = String.format(MESSAGE_GUESTS_LISTED_OVERVIEW, 3);
        GuestNameContainsKeywordsPredicate predicate = prepareGuestNamePredicate("Kurz Elle Kunz");
        List<Predicate<Guest>> guestListPredicates = new LinkedList<>();
        guestListPredicates.add(predicate);
        FindCommand command = new FindCommand(FLAG_GUEST.toString(), guestListPredicates, null);
        expectedModel.updateFilteredGuestList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredGuestList());
    }

    @Test
    public void execute_roomFlagAndNumber_roomFound() {
        String expectedMessage = String.format(MESSAGE_ROOMS_LISTED_OVERVIEW, 1);
        RoomNumberExactPredicate predicate = new RoomNumberExactPredicate("001");
        List<Predicate<Room>> roomListPredicates = new LinkedList<>();
        roomListPredicates.add(predicate);
        FindCommand command = new FindCommand(FLAG_ROOM.toString(), null, roomListPredicates);
        expectedModel.updateFilteredRoomList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals("001", model.getFilteredRoomList().get(0).roomNumber.value);
    }

    /**
     * Parses {@code userInput} into a {@code GuestNameContainsKeywordsPredicate}.
     */
    private GuestNameContainsKeywordsPredicate prepareGuestNamePredicate(String userInput) {
        return new GuestNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

}
