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
    public void commit_singleGuestList_noStatesRemovedCurrentStateSaved() {
        VersionedGuestList versionedGuestList = prepareGuestListList(emptyGuestList);

        versionedGuestList.commit();
        assertGuestListListStatus(versionedGuestList,
                Collections.singletonList(emptyGuestList),
                emptyGuestList,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleGuestListPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedGuestList versionedGuestList = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);

        versionedGuestList.commit();
        assertGuestListListStatus(versionedGuestList,
                Arrays.asList(emptyGuestList, guestListWithAmy, guestListWithBob),
                guestListWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleGuestListPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedGuestList versionedGuestList = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);
        shiftCurrentStatePointerLeftwards(versionedGuestList, 2);

        versionedGuestList.commit();
        assertGuestListListStatus(versionedGuestList,
                Collections.singletonList(emptyGuestList),
                emptyGuestList,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleGuestListPointerAtEndOfStateList_returnsTrue() {
        VersionedGuestList versionedGuestList = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);

        assertTrue(versionedGuestList.canUndo());
    }

    @Test
    public void canUndo_multipleGuestListPointerAtStartOfStateList_returnsTrue() {
        VersionedGuestList versionedGuestList = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);
        shiftCurrentStatePointerLeftwards(versionedGuestList, 1);

        assertTrue(versionedGuestList.canUndo());
    }

    @Test
    public void canUndo_singleGuestList_returnsFalse() {
        VersionedGuestList versionedGuestList = prepareGuestListList(emptyGuestList);

        assertFalse(versionedGuestList.canUndo());
    }

    @Test
    public void canUndo_multipleGuestListPointerAtStartOfStateList_returnsFalse() {
        VersionedGuestList versionedGuestList = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);
        shiftCurrentStatePointerLeftwards(versionedGuestList, 2);

        assertFalse(versionedGuestList.canUndo());
    }

    @Test
    public void canRedo_multipleGuestListPointerNotAtEndOfStateList_returnsTrue() {
        VersionedGuestList versionedGuestList = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);
        shiftCurrentStatePointerLeftwards(versionedGuestList, 1);

        assertTrue(versionedGuestList.canRedo());
    }

    @Test
    public void canRedo_multipleGuestListPointerAtStartOfStateList_returnsTrue() {
        VersionedGuestList versionedGuestList = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);
        shiftCurrentStatePointerLeftwards(versionedGuestList, 2);

        assertTrue(versionedGuestList.canRedo());
    }

    @Test
    public void canRedo_singleGuestList_returnsFalse() {
        VersionedGuestList versionedGuestList = prepareGuestListList(emptyGuestList);

        assertFalse(versionedGuestList.canRedo());
    }

    @Test
    public void canRedo_multipleGuestListPointerAtEndOfStateList_returnsFalse() {
        VersionedGuestList versionedGuestList = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);

        assertFalse(versionedGuestList.canRedo());
    }

    @Test
    public void undo_multipleGuestListPointerAtEndOfStateList_success() {
        VersionedGuestList versionedGuestList = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);

        versionedGuestList.undo();
        assertGuestListListStatus(versionedGuestList,
                Collections.singletonList(emptyGuestList),
                guestListWithAmy,
                Collections.singletonList(guestListWithBob));
    }

    @Test
    public void undo_multipleGuestListPointerNotAtStartOfStateList_success() {
        VersionedGuestList versionedGuestList = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);
        shiftCurrentStatePointerLeftwards(versionedGuestList, 1);

        versionedGuestList.undo();
        assertGuestListListStatus(versionedGuestList,
                Collections.emptyList(),
                emptyGuestList,
                Arrays.asList(guestListWithAmy, guestListWithBob));
    }

    @Test
    public void undo_singleGuestList_throwsNoUndoableStateException() {
        VersionedGuestList versionedGuestList = prepareGuestListList(emptyGuestList);

        assertThrows(VersionedGuestList.NoUndoableStateException.class, versionedGuestList::undo);
    }

    @Test
    public void undo_multipleGuestListPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedGuestList versionedGuestList = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);
        shiftCurrentStatePointerLeftwards(versionedGuestList, 2);

        assertThrows(VersionedGuestList.NoUndoableStateException.class, versionedGuestList::undo);
    }

    @Test
    public void redo_multipleGuestListPointerNotAtEndOfStateList_success() {
        VersionedGuestList versionedGuestList = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);
        shiftCurrentStatePointerLeftwards(versionedGuestList, 1);

        versionedGuestList.redo();
        assertGuestListListStatus(versionedGuestList,
                Arrays.asList(emptyGuestList, guestListWithAmy),
                guestListWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleGuestListPointerAtStartOfStateList_success() {
        VersionedGuestList versionedGuestList = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);
        shiftCurrentStatePointerLeftwards(versionedGuestList, 2);

        versionedGuestList.redo();
        assertGuestListListStatus(versionedGuestList,
                Collections.singletonList(emptyGuestList),
                guestListWithAmy,
                Collections.singletonList(guestListWithBob));
    }

    @Test
    public void redo_singleGuestList_throwsNoRedoableStateException() {
        VersionedGuestList versionedGuestList = prepareGuestListList(emptyGuestList);

        assertThrows(VersionedGuestList.NoRedoableStateException.class, versionedGuestList::redo);
    }

    @Test
    public void redo_multipleGuestListPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedGuestList versionedGuestList = prepareGuestListList(
                emptyGuestList, guestListWithAmy, guestListWithBob);

        assertThrows(VersionedGuestList.NoRedoableStateException.class, versionedGuestList::redo);
    }

    @Test
    public void equals() {
        VersionedGuestList versionedGuestList = prepareGuestListList(guestListWithAmy, guestListWithBob);

        // same values -> returns true
        VersionedGuestList copy = prepareGuestListList(guestListWithAmy, guestListWithBob);
        assertTrue(versionedGuestList.equals(copy));

        // same object -> returns true
        assertTrue(versionedGuestList.equals(versionedGuestList));

        // null -> returns false
        assertFalse(versionedGuestList.equals(null));

        // different types -> returns false
        assertFalse(versionedGuestList.equals(1));

        // different state list -> returns false
        VersionedGuestList differentGuestListList = prepareGuestListList(guestListWithBob, guestListWithCarl);
        assertFalse(versionedGuestList.equals(differentGuestListList));

        // different current pointer index -> returns false
        VersionedGuestList differentCurrentStatePointer = prepareGuestListList(
                guestListWithAmy, guestListWithBob);
        shiftCurrentStatePointerLeftwards(versionedGuestList, 1);
        assertFalse(versionedGuestList.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedGuestList} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedGuestList#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedGuestList#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertGuestListListStatus(VersionedGuestList versionedGuestList,
                                           List<ReadOnlyGuestList> expectedStatesBeforePointer,
                                           ReadOnlyGuestList expectedCurrentState,
                                           List<ReadOnlyGuestList> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new GuestList(versionedGuestList), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedGuestList.canUndo()) {
            versionedGuestList.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyGuestList expectedGuestList : expectedStatesBeforePointer) {
            assertEquals(expectedGuestList, new GuestList(versionedGuestList));
            versionedGuestList.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyGuestList expectedGuestList : expectedStatesAfterPointer) {
            versionedGuestList.redo();
            assertEquals(expectedGuestList, new GuestList(versionedGuestList));
        }

        // check that there are no more states after pointer
        assertFalse(versionedGuestList.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedGuestList.undo());
    }

    /**
     * Creates and returns a {@code VersionedGuestList} with the {@code guestListStates} added into it, and the
     * {@code VersionedGuestList#currentStatePointer} at the end of list.
     */
    private VersionedGuestList prepareGuestListList(ReadOnlyGuestList... guestListStates) {
        assertFalse(guestListStates.length == 0);

        VersionedGuestList versionedGuestList = new VersionedGuestList(guestListStates[0]);
        for (int i = 1; i < guestListStates.length; i++) {
            versionedGuestList.resetData(guestListStates[i]);
            versionedGuestList.commit();
        }

        return versionedGuestList;
    }

    /**
     * Shifts the {@code versionedGuestList#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedGuestList versionedGuestList, int count) {
        for (int i = 0; i < count; i++) {
            versionedGuestList.undo();
        }
    }
}
