package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalGuests.ALICE;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.expenses.ExpenseType;
import seedu.address.model.guest.Guest;
import seedu.address.model.guest.exceptions.DuplicateGuestException;
import seedu.address.model.room.Room;
import seedu.address.testutil.GuestBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getGuestList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateGuests_throwsDuplicateGuestException() {
        // Two guests with the same identity fields
        Guest editedAlice = new GuestBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Guest> newGuests = Arrays.asList(ALICE, editedAlice);

        AddressBookStub newData = new AddressBookStub(newGuests, null);

        thrown.expect(DuplicateGuestException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasGuest_nullGuest_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasGuest(null);
    }

    @Test
    public void hasGuest_guestNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasGuest(ALICE));
    }

    @Test
    public void hasGuest_guestInAddressBook_returnsTrue() {
        addressBook.addGuest(ALICE);
        assertTrue(addressBook.hasGuest(ALICE));
    }

    @Test
    public void hasGuest_guestWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addGuest(ALICE);
        Guest editedAlice = new GuestBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasGuest(editedAlice));
    }

    @Test
    public void getGuestList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getGuestList().remove(0);
    }

    @Test
    public void getMenuMap_modifyMap_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getMenuMap().put("1", new ExpenseType("1", "-", 0));
    }

    /**
     * A stub ReadOnlyAddressBook whose guests list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Guest> guests = FXCollections.observableArrayList();
        private final ObservableList<Room> rooms = FXCollections.observableArrayList();

        AddressBookStub(Collection<Guest> guests, Collection<Room> rooms) {
            this.guests.setAll(guests);
        }

        @Override
        public ObservableList<Guest> getGuestList() {
            return guests;
        }

        @Override
        public ObservableList<Room> getRoomList() {
            return rooms;
        }

        @Override
        public Menu getMenu() {
            return new Menu();
        }

        @Override
        public Map<String, ExpenseType> getMenuMap() {
            return new HashMap<>();
        }
    }

}
