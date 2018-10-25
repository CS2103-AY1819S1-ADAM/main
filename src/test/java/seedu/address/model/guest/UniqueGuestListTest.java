package seedu.address.model.guest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalGuests.ALICE;
import static seedu.address.testutil.TypicalGuests.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.guest.exceptions.DuplicateGuestException;
import seedu.address.model.guest.exceptions.GuestNotFoundException;
import seedu.address.testutil.GuestBuilder;

public class UniqueGuestListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueGuestList uniqueGuestList = new UniqueGuestList();

    @Test
    public void contains_nullGuest_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGuestList.contains(null);
    }

    @Test
    public void contains_guestNotInList_returnsFalse() {
        assertFalse(uniqueGuestList.contains(ALICE));
    }

    @Test
    public void contains_guestInList_returnsTrue() {
        uniqueGuestList.add(ALICE);
        assertTrue(uniqueGuestList.contains(ALICE));
    }

    @Test
    public void contains_guestWithSameIdentityFieldsInList_returnsTrue() {
        uniqueGuestList.add(ALICE);
        Guest editedAlice = new GuestBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueGuestList.contains(editedAlice));
    }

    @Test
    public void add_nullGuest_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGuestList.add(null);
    }

    @Test
    public void add_duplicateGuest_throwsDuplicateGuestException() {
        uniqueGuestList.add(ALICE);
        thrown.expect(DuplicateGuestException.class);
        uniqueGuestList.add(ALICE);
    }

    @Test
    public void setGuest_nullTargetGuest_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGuestList.setGuest(null, ALICE);
    }

    @Test
    public void setGuest_nullEditedGuest_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGuestList.setGuest(ALICE, null);
    }

    @Test
    public void setGuest_targetGuestNotInList_throwsGuestNotFoundException() {
        thrown.expect(GuestNotFoundException.class);
        uniqueGuestList.setGuest(ALICE, ALICE);
    }

    @Test
    public void setGuest_editedGuestIsSameGuest_success() {
        uniqueGuestList.add(ALICE);
        uniqueGuestList.setGuest(ALICE, ALICE);
        UniqueGuestList expectedUniqueGuestList = new UniqueGuestList();
        expectedUniqueGuestList.add(ALICE);
        assertEquals(expectedUniqueGuestList, uniqueGuestList);
    }

    @Test
    public void setGuest_editedGuestHasSameIdentity_success() {
        uniqueGuestList.add(ALICE);
        Guest editedAlice = new GuestBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueGuestList.setGuest(ALICE, editedAlice);
        UniqueGuestList expectedUniqueGuestList = new UniqueGuestList();
        expectedUniqueGuestList.add(editedAlice);
        assertEquals(expectedUniqueGuestList, uniqueGuestList);
    }

    @Test
    public void setGuest_editedGuestHasDifferentIdentity_success() {
        uniqueGuestList.add(ALICE);
        uniqueGuestList.setGuest(ALICE, BOB);
        UniqueGuestList expectedUniqueGuestList = new UniqueGuestList();
        expectedUniqueGuestList.add(BOB);
        assertEquals(expectedUniqueGuestList, uniqueGuestList);
    }

    @Test
    public void setGuest_editedGuestHasNonUniqueIdentity_throwsDuplicateGuestException() {
        uniqueGuestList.add(ALICE);
        uniqueGuestList.add(BOB);
        thrown.expect(DuplicateGuestException.class);
        uniqueGuestList.setGuest(ALICE, BOB);
    }

    @Test
    public void remove_nullGuest_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGuestList.remove(null);
    }

    @Test
    public void remove_guestDoesNotExist_throwsGuestNotFoundException() {
        thrown.expect(GuestNotFoundException.class);
        uniqueGuestList.remove(ALICE);
    }

    @Test
    public void remove_existingGuest_removesGuest() {
        uniqueGuestList.add(ALICE);
        uniqueGuestList.remove(ALICE);
        UniqueGuestList expectedUniqueGuestList = new UniqueGuestList();
        assertEquals(expectedUniqueGuestList, uniqueGuestList);
    }

    @Test
    public void setGuests_nullUniqueGuestList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGuestList.setGuests((UniqueGuestList) null);
    }

    @Test
    public void setGuests_uniqueGuestList_replacesOwnListWithProvidedUniqueGuestList() {
        uniqueGuestList.add(ALICE);
        UniqueGuestList expectedUniqueGuestList = new UniqueGuestList();
        expectedUniqueGuestList.add(BOB);
        uniqueGuestList.setGuests(expectedUniqueGuestList);
        assertEquals(expectedUniqueGuestList, uniqueGuestList);
    }

    @Test
    public void setGuests_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGuestList.setGuests((List<Guest>) null);
    }

    @Test
    public void setGuests_list_replacesOwnListWithProvidedList() {
        uniqueGuestList.add(ALICE);
        List<Guest> guestList = Collections.singletonList(BOB);
        uniqueGuestList.setGuests(guestList);
        UniqueGuestList expectedUniqueGuestList = new UniqueGuestList();
        expectedUniqueGuestList.add(BOB);
        assertEquals(expectedUniqueGuestList, uniqueGuestList);
    }

    @Test
    public void setGuests_listWithDuplicateGuests_throwsDuplicateGuestException() {
        List<Guest> listWithDuplicateGuests = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateGuestException.class);
        uniqueGuestList.setGuests(listWithDuplicateGuests);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueGuestList.asUnmodifiableObservableList().remove(0);
    }
}
