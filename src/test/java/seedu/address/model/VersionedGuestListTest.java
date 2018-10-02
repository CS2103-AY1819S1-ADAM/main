package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.GuestListBuilder;

public class VersionedGuestListTest {

    private final ReadOnlyGuestList guestListWithAmy = new GuestListBuilder().withPerson(AMY).build();
    private final ReadOnlyGuestList guestListWithBob = new GuestListBuilder().withPerson(BOB).build();
    private final ReadOnlyGuestList guestListWithCarl = new GuestListBuilder().withPerson(CARL).build();
    private final ReadOnlyGuestList emptyGuestList = new GuestListBuilder().build();

    @Test
    public void commit_singleAddressBook_noStatesRemovedCurrentStateSaved() {
        VersionedGuestList versionedGuestList = prepareGuestListList(emptyGuestList);

        versionedGuestList.commit();
        assertGuestListListStatus(versionedGuestList,
                Collections.singletonList(emptyGuestList),
                emptyGuestList,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedGuestList versionedAddressBook = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);

        versionedAddressBook.commit();
        assertGuestListListStatus(versionedAddressBook,
                Arrays.asList(emptyGuestList, guestListWithAmy, guestListWithBob),
                guestListWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAddressBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedGuestList versionedAddressBook = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        versionedAddressBook.commit();
        assertGuestListListStatus(versionedAddressBook,
                Collections.singletonList(emptyGuestList),
                emptyGuestList,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtEndOfStateList_returnsTrue() {
        VersionedGuestList versionedAddressBook = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);

        assertTrue(versionedAddressBook.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedGuestList versionedAddressBook = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);

        assertTrue(versionedAddressBook.canUndo());
    }

    @Test
    public void canUndo_singleAddressBook_returnsFalse() {
        VersionedGuestList versionedAddressBook = prepareGuestListList(emptyGuestList);

        assertFalse(versionedAddressBook.canUndo());
    }

    @Test
    public void canUndo_multipleAddressBookPointerAtStartOfStateList_returnsFalse() {
        VersionedGuestList versionedAddressBook = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        assertFalse(versionedAddressBook.canUndo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedGuestList versionedAddressBook = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);

        assertTrue(versionedAddressBook.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtStartOfStateList_returnsTrue() {
        VersionedGuestList versionedAddressBook = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        assertTrue(versionedAddressBook.canRedo());
    }

    @Test
    public void canRedo_singleAddressBook_returnsFalse() {
        VersionedGuestList versionedAddressBook = prepareGuestListList(emptyGuestList);

        assertFalse(versionedAddressBook.canRedo());
    }

    @Test
    public void canRedo_multipleAddressBookPointerAtEndOfStateList_returnsFalse() {
        VersionedGuestList versionedAddressBook = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);

        assertFalse(versionedAddressBook.canRedo());
    }

    @Test
    public void undo_multipleAddressBookPointerAtEndOfStateList_success() {
        VersionedGuestList versionedAddressBook = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);

        versionedAddressBook.undo();
        assertGuestListListStatus(versionedAddressBook,
                Collections.singletonList(emptyGuestList),
                guestListWithAmy,
                Collections.singletonList(guestListWithBob));
    }

    @Test
    public void undo_multipleAddressBookPointerNotAtStartOfStateList_success() {
        VersionedGuestList versionedAddressBook = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);

        versionedAddressBook.undo();
        assertGuestListListStatus(versionedAddressBook,
                Collections.emptyList(),
                emptyGuestList,
                Arrays.asList(guestListWithAmy, guestListWithBob));
    }

    @Test
    public void undo_singleAddressBook_throwsNoUndoableStateException() {
        VersionedGuestList versionedAddressBook = prepareGuestListList(emptyGuestList);

        assertThrows(VersionedGuestList.NoUndoableStateException.class, versionedAddressBook::undo);
    }

    @Test
    public void undo_multipleAddressBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedGuestList versionedAddressBook = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        assertThrows(VersionedGuestList.NoUndoableStateException.class, versionedAddressBook::undo);
    }

    @Test
    public void redo_multipleAddressBookPointerNotAtEndOfStateList_success() {
        VersionedGuestList versionedAddressBook = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);

        versionedAddressBook.redo();
        assertGuestListListStatus(versionedAddressBook,
                Arrays.asList(emptyGuestList, guestListWithAmy),
                guestListWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleAddressBookPointerAtStartOfStateList_success() {
        VersionedGuestList versionedAddressBook = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 2);

        versionedAddressBook.redo();
        assertGuestListListStatus(versionedAddressBook,
                Collections.singletonList(emptyGuestList),
                guestListWithAmy,
                Collections.singletonList(guestListWithBob));
    }

    @Test
    public void redo_singleAddressBook_throwsNoRedoableStateException() {
        VersionedGuestList versionedAddressBook = prepareGuestListList(emptyGuestList);

        assertThrows(VersionedGuestList.NoRedoableStateException.class, versionedAddressBook::redo);
    }

    @Test
    public void redo_multipleAddressBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedGuestList versionedAddressBook = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);

        assertThrows(VersionedGuestList.NoRedoableStateException.class, versionedAddressBook::redo);
    }

    @Test
    public void equals() {
        VersionedGuestList versionedAddressBook = prepareGuestListList(guestListWithAmy, guestListWithBob);

        // same values -> returns true
        VersionedGuestList copy = prepareGuestListList(guestListWithAmy, guestListWithBob);
        assertTrue(versionedAddressBook.equals(copy));

        // same object -> returns true
        assertTrue(versionedAddressBook.equals(versionedAddressBook));

        // null -> returns false
        assertFalse(versionedAddressBook.equals(null));

        // different types -> returns false
        assertFalse(versionedAddressBook.equals(1));

        // different state list -> returns false
        VersionedGuestList differentAddressBookList = prepareGuestListList(guestListWithBob, guestListWithCarl);
        assertFalse(versionedAddressBook.equals(differentAddressBookList));

        // different current pointer index -> returns false
        VersionedGuestList differentCurrentStatePointer = prepareGuestListList(
                guestListWithAmy, guestListWithBob);
        shiftCurrentStatePointerLeftwards(versionedAddressBook, 1);
        assertFalse(versionedAddressBook.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedAddressBook} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedAddressBook#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedAddressBook#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertGuestListListStatus(VersionedGuestList versionedAddressBook,
                                           List<ReadOnlyGuestList> expectedStatesBeforePointer,
                                           ReadOnlyGuestList expectedCurrentState,
                                           List<ReadOnlyGuestList> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new GuestList(versionedAddressBook), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedAddressBook.canUndo()) {
            versionedAddressBook.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyGuestList expectedAddressBook : expectedStatesBeforePointer) {
            assertEquals(expectedAddressBook, new GuestList(versionedAddressBook));
            versionedAddressBook.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyGuestList expectedAddressBook : expectedStatesAfterPointer) {
            versionedAddressBook.redo();
            assertEquals(expectedAddressBook, new GuestList(versionedAddressBook));
        }

        // check that there are no more states after pointer
        assertFalse(versionedAddressBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedAddressBook.undo());
    }

    /**
     * Creates and returns a {@code VersionedGuestList} with the {@code addressBookStates} added into it, and the
     * {@code VersionedGuestList#currentStatePointer} at the end of list.
     */
    private VersionedGuestList prepareGuestListList(ReadOnlyGuestList... addressBookStates) {
        assertFalse(addressBookStates.length == 0);

        VersionedGuestList versionedAddressBook = new VersionedGuestList(addressBookStates[0]);
        for (int i = 1; i < addressBookStates.length; i++) {
            versionedAddressBook.resetData(addressBookStates[i]);
            versionedAddressBook.commit();
        }

        return versionedAddressBook;
    }

    /**
     * Shifts the {@code versionedAddressBook#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedGuestList versionedAddressBook, int count) {
        for (int i = 0; i < count; i++) {
            versionedAddressBook.undo();
        }
    }
}
