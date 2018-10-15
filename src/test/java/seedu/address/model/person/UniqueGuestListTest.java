package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.exceptions.DuplicateGuestException;
import seedu.address.model.person.exceptions.GuestNotFoundException;
import seedu.address.testutil.GuestBuilder;

public class UniqueGuestListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueGuestList uniqueGuestList = new UniqueGuestList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGuestList.contains(null);
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueGuestList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueGuestList.add(ALICE);
        assertTrue(uniqueGuestList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueGuestList.add(ALICE);
        Guest editedAlice = new GuestBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueGuestList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGuestList.add(null);
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueGuestList.add(ALICE);
        thrown.expect(DuplicateGuestException.class);
        uniqueGuestList.add(ALICE);
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGuestList.setGuest(null, ALICE);
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGuestList.setGuest(ALICE, null);
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        thrown.expect(GuestNotFoundException.class);
        uniqueGuestList.setGuest(ALICE, ALICE);
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueGuestList.add(ALICE);
        uniqueGuestList.setGuest(ALICE, ALICE);
        UniqueGuestList expectedUniqueGuestList = new UniqueGuestList();
        expectedUniqueGuestList.add(ALICE);
        assertEquals(expectedUniqueGuestList, uniqueGuestList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueGuestList.add(ALICE);
        Guest editedAlice = new GuestBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueGuestList.setGuest(ALICE, editedAlice);
        UniqueGuestList expectedUniqueGuestList = new UniqueGuestList();
        expectedUniqueGuestList.add(editedAlice);
        assertEquals(expectedUniqueGuestList, uniqueGuestList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueGuestList.add(ALICE);
        uniqueGuestList.setGuest(ALICE, BOB);
        UniqueGuestList expectedUniqueGuestList = new UniqueGuestList();
        expectedUniqueGuestList.add(BOB);
        assertEquals(expectedUniqueGuestList, uniqueGuestList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueGuestList.add(ALICE);
        uniqueGuestList.add(BOB);
        thrown.expect(DuplicateGuestException.class);
        uniqueGuestList.setGuest(ALICE, BOB);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGuestList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(GuestNotFoundException.class);
        uniqueGuestList.remove(ALICE);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueGuestList.add(ALICE);
        uniqueGuestList.remove(ALICE);
        UniqueGuestList expectedUniqueGuestList = new UniqueGuestList();
        assertEquals(expectedUniqueGuestList, uniqueGuestList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGuestList.setGuests((UniqueGuestList) null);
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueGuestList.add(ALICE);
        UniqueGuestList expectedUniqueGuestList = new UniqueGuestList();
        expectedUniqueGuestList.add(BOB);
        uniqueGuestList.setGuests(expectedUniqueGuestList);
        assertEquals(expectedUniqueGuestList, uniqueGuestList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueGuestList.setGuests((List<Guest>) null);
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueGuestList.add(ALICE);
        List<Guest> guestList = Collections.singletonList(BOB);
        uniqueGuestList.setGuests(guestList);
        UniqueGuestList expectedUniqueGuestList = new UniqueGuestList();
        expectedUniqueGuestList.add(BOB);
        assertEquals(expectedUniqueGuestList, uniqueGuestList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
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
