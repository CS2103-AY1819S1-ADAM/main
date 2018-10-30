package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_GUESTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.FLAG_GUEST;
import static seedu.address.testutil.TypicalConcierge.getTypicalConcierge;
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


/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalConcierge(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalConcierge(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        List<Predicate<Guest>> firstList = new LinkedList<Predicate<Guest>>();
        List<Predicate<Guest>> secondList = new LinkedList<>();
        List<Predicate<Room>> emptyRoomPredicates = new LinkedList<>();
        firstList.add(new GuestNameContainsKeywordsPredicate(Collections.singletonList("first")));
        secondList.add(new GuestNameContainsKeywordsPredicate(Collections.singletonList("second")));

        FindCommand findFirstCommand = new FindCommand(FLAG_GUEST.toString(), firstList, emptyRoomPredicates);
        FindCommand findSecondCommand = new FindCommand(FLAG_GUEST.toString(), secondList, emptyRoomPredicates);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(FLAG_GUEST.toString(), firstList, emptyRoomPredicates);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different guest -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noGuestFound() {
        String expectedMessage = String.format(MESSAGE_GUESTS_LISTED_OVERVIEW, 0);
        GuestNameContainsKeywordsPredicate predicate = preparePredicate(" ");
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
        GuestNameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        List<Predicate<Guest>> guestListPredicates = new LinkedList<>();
        guestListPredicates.add(predicate);
        FindCommand command = new FindCommand(FLAG_GUEST.toString(), guestListPredicates, null);
        expectedModel.updateFilteredGuestList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredGuestList());
    }

    /**
     * Parses {@code userInput} into a {@code GuestNameContainsKeywordsPredicate}.
     */
    private GuestNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new GuestNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
