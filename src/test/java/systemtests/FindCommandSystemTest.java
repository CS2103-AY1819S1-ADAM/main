package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_GUESTS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.FLAG_GUEST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.testutil.TypicalGuests.BENSON;
import static seedu.address.testutil.TypicalGuests.CARL;
import static seedu.address.testutil.TypicalGuests.DANIEL;
import static seedu.address.testutil.TypicalGuests.KEYWORD_MATCHING_MEIER;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.guest.Name;
import seedu.address.model.tag.Tag;

public class FindCommandSystemTest extends ConciergeSystemTest {

    @Test
    public void find() {
        /* Case: find multiple guests in Concierge, command with leading spaces and trailing spaces
         * -> 2 guests found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + FLAG_GUEST + " "
                + PREFIX_NAME + KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredGuestList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedGuestCardUnchanged();

        /* Case: repeat previous find command where guest list is displaying the guests we are finding
         * -> 2 guests found
         */
        command = FindCommand.COMMAND_WORD + " " + FLAG_GUEST + " " + PREFIX_NAME + KEYWORD_MATCHING_MEIER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedGuestCardUnchanged();

        /* Case: find guest where guest list is not displaying the guest we are finding -> 1 guest found */
        command = FindCommand.COMMAND_WORD + " " + FLAG_GUEST + " " + PREFIX_NAME + "Carl";
        ModelHelper.setFilteredGuestList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedGuestCardUnchanged();

        /* Case: find multiple guests in Concierge, 2 keywords -> 2 guests found */
        command = FindCommand.COMMAND_WORD + " " + FLAG_GUEST + " " + PREFIX_NAME + "Benson Daniel";
        ModelHelper.setFilteredGuestList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedGuestCardUnchanged();

        /* Case: find multiple guests in Concierge, 2 keywords in reversed order -> 2 guests found */
        command = FindCommand.COMMAND_WORD + " " + FLAG_GUEST + " " + PREFIX_NAME + "Daniel Benson";
        assertCommandSuccess(command, expectedModel);
        assertSelectedGuestCardUnchanged();

        /* Case: find multiple guests in Concierge, 2 keywords with 1 repeat -> 2 guests found */
        command = FindCommand.COMMAND_WORD + " " + FLAG_GUEST + " " + PREFIX_NAME + "Daniel Benson Daniel";
        assertCommandSuccess(command, expectedModel);
        assertSelectedGuestCardUnchanged();

        /* Case: find multiple guests in Concierge, 2 matching keywords and 1 non-matching keyword
         * -> 2 guests found
         */
        command = FindCommand.COMMAND_WORD + " " + FLAG_GUEST + " " + PREFIX_NAME + "Daniel Benson NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedGuestCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find guest in Concierge, keyword is same as name but of different case -> 1 guest found */
        command = FindCommand.COMMAND_WORD + " " + FLAG_GUEST + " " + PREFIX_NAME + "MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedGuestCardUnchanged();

        /* Case: find guest in Concierge, keyword is substring of name -> 0 guests found */
        command = FindCommand.COMMAND_WORD + " " + FLAG_GUEST + " " + PREFIX_NAME + "Mei";
        ModelHelper.setFilteredGuestList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedGuestCardUnchanged();

        /* Case: find guest in Concierge, name is substring of keyword -> 0 guests found */
        command = FindCommand.COMMAND_WORD + " " + FLAG_GUEST + " " + PREFIX_NAME + "Meiers";
        ModelHelper.setFilteredGuestList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedGuestCardUnchanged();

        /* Case: find guest not in Concierge -> 0 guests found */
        command = FindCommand.COMMAND_WORD + " " + FLAG_GUEST + " " + PREFIX_NAME + "Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedGuestCardUnchanged();

        /* Case: find phone number of guest in Concierge using name prefix -> 0 guests found */
        command = FindCommand.COMMAND_WORD + " " + FLAG_GUEST + " " + PREFIX_NAME + DANIEL.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedGuestCardUnchanged();

        /* Case: find tags of guest in Concierge -> 0 guests found */
        List<Tag> tags = new ArrayList<>(DANIEL.getTags());
        command = FindCommand.COMMAND_WORD + " " + FLAG_GUEST + " " + PREFIX_NAME + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedGuestCardUnchanged();

        /* Case: find while a guest is selected -> selected card deselected */
        showAllGuests();
        selectGuest(Index.fromOneBased(1));
        assertFalse(getGuestListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName));
        command = FindCommand.COMMAND_WORD + " " + FLAG_GUEST + " " + PREFIX_NAME + "Daniel";
        ModelHelper.setFilteredGuestList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedGuestCardDeselected();

        /* Case: find guest in empty Concierge -> 0 guests found */
        deleteAllGuests();
        command = FindCommand.COMMAND_WORD + " " + FLAG_GUEST + " " + PREFIX_NAME + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredGuestList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedGuestCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
        assertSelectedGuestCardUnchanged();

        /* Case: find email of guest in Concierge using name prefix -> ParseException thrown */
        command = FindCommand.COMMAND_WORD + " " + FLAG_GUEST + " " + PREFIX_NAME + DANIEL.getEmail().value;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);
        assertSelectedGuestCardUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code ConciergeSystemTest#assertApplicationDisplaysExpectedGuest(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see ConciergeSystemTest#assertApplicationDisplaysExpectedGuest(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_GUESTS_LISTED_OVERVIEW,
                expectedModel.getFilteredGuestList().size());

        executeCommand(command);
        assertApplicationDisplaysExpectedGuest("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code ConciergeSystemTest#assertApplicationDisplaysExpectedGuest(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see ConciergeSystemTest#assertApplicationDisplaysExpectedGuest(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpectedGuest(command, expectedResultMessage, expectedModel);
        assertSelectedGuestCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
