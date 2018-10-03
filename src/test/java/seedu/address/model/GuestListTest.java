package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalGuests.ALICE;
import static seedu.address.testutil.TypicalGuests.getTypicalGuestList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.guest.Guest;
import seedu.address.model.guest.exceptions.DuplicateGuestException;
import seedu.address.testutil.GuestBuilder;

public class GuestListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final GuestList guestList = new GuestList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), guestList.getListOfGuests());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        guestList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        GuestList newData = getTypicalGuestList();
        guestList.resetData(newData);
        assertEquals(newData, guestList);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two guests with the same identity fields
        Guest editedAlice = new GuestBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Guest> newGuests = Arrays.asList(ALICE, editedAlice);
        GuestListStub newData = new GuestListStub(newGuests);

        thrown.expect(DuplicateGuestException.class);
        guestList.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        guestList.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(guestList.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        guestList.addPerson(ALICE);
        assertTrue(guestList.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        guestList.addPerson(ALICE);
        Guest editedAlice = new GuestBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(guestList.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        guestList.getListOfGuests().remove(0);
    }

    /**
     * A stub ReadOnlyGuestList whose guests list can violate interface
     * constraints.
     */
    private static class GuestListStub implements ReadOnlyGuestList {
        private final ObservableList<Guest> guests =
                FXCollections.observableArrayList();

        GuestListStub(Collection<Guest> guests) {
            this.guests.setAll(guests);
        }

        @Override
        public ObservableList<Guest> getListOfGuests() {
            return guests;
        }
    }

}
