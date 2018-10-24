package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showGuestAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GUEST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_GUEST;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCommand.EditGuestDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.guest.Guest;
import seedu.address.testutil.EditGuestDescriptorBuilder;
import seedu.address.testutil.GuestBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Guest editedGuest = new GuestBuilder().build();
        EditGuestDescriptor descriptor = new EditGuestDescriptorBuilder(editedGuest).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_GUEST, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_GUEST_SUCCESS, editedGuest);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateGuest(model.getFilteredGuestList().get(0), editedGuest);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastGuest = Index.fromOneBased(model.getFilteredGuestList().size());
        Guest lastGuest = model.getFilteredGuestList().get(indexLastGuest.getZeroBased());

        GuestBuilder guestInList = new GuestBuilder(lastGuest);
        Guest editedGuest = guestInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditGuestDescriptor descriptor = new EditGuestDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastGuest, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_GUEST_SUCCESS, editedGuest);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateGuest(lastGuest, editedGuest);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_GUEST, new EditGuestDescriptor());
        Guest editedGuest = model.getFilteredGuestList().get(INDEX_FIRST_GUEST.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_GUEST_SUCCESS, editedGuest);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showGuestAtIndex(model, INDEX_FIRST_GUEST);

        Guest guestInFilteredList = model.getFilteredGuestList().get(INDEX_FIRST_GUEST.getZeroBased());
        Guest editedGuest = new GuestBuilder(guestInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_GUEST,
                new EditGuestDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_GUEST_SUCCESS, editedGuest);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateGuest(model.getFilteredGuestList().get(0), editedGuest);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateGuestUnfilteredList_failure() {
        Guest firstGuest = model.getFilteredGuestList().get(INDEX_FIRST_GUEST.getZeroBased());
        EditGuestDescriptor descriptor = new EditGuestDescriptorBuilder(firstGuest).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_GUEST, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_GUEST);
    }

    @Test
    public void execute_duplicateGuestFilteredList_failure() {
        showGuestAtIndex(model, INDEX_FIRST_GUEST);

        // edit guest in filtered list into a duplicate in address book
        Guest guestInList = model.getAddressBook().getGuestList().get(INDEX_SECOND_GUEST.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_GUEST,
                new EditGuestDescriptorBuilder(guestInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_GUEST);
    }

    @Test
    public void execute_invalidGuestIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGuestList().size() + 1);
        EditGuestDescriptor descriptor = new EditGuestDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_GUEST_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidGuestIndexFilteredList_failure() {
        showGuestAtIndex(model, INDEX_FIRST_GUEST);
        Index outOfBoundIndex = INDEX_SECOND_GUEST;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getGuestList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditGuestDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_GUEST_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Guest editedGuest = new GuestBuilder().build();
        Guest guestToEdit = model.getFilteredGuestList().get(INDEX_FIRST_GUEST.getZeroBased());
        EditGuestDescriptor descriptor = new EditGuestDescriptorBuilder(editedGuest).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_GUEST, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateGuest(guestToEdit, editedGuest);
        expectedModel.commitAddressBook();

        // edit -> first guest edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered guest list to show all guests
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first guest edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGuestList().size() + 1);
        EditGuestDescriptor descriptor = new EditGuestDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_GUEST_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Guest} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited guest in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the guest object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameGuestEdited() throws Exception {
        Guest editedGuest = new GuestBuilder().build();
        EditGuestDescriptor descriptor = new EditGuestDescriptorBuilder(editedGuest).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_GUEST, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showGuestAtIndex(model, INDEX_SECOND_GUEST);
        Guest guestToEdit = model.getFilteredGuestList().get(INDEX_FIRST_GUEST.getZeroBased());
        expectedModel.updateGuest(guestToEdit, editedGuest);
        expectedModel.commitAddressBook();

        // edit -> edits second guest in unfiltered guest list / first guest in filtered guest list
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered guest list to show all guests
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredGuestList().get(INDEX_FIRST_GUEST.getZeroBased()), guestToEdit);
        // redo -> edits same second guest in unfiltered guest list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_GUEST, DESC_AMY);

        // same values -> returns true
        EditGuestDescriptor copyDescriptor = new EditGuestDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_GUEST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_GUEST, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_GUEST, DESC_BOB)));
    }

}
