package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalConcierge.getTypicalConciergeClean;
import static seedu.address.testutil.TypicalGuests.ALICE;

import java.util.ArrayList;
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
import seedu.address.model.expenses.Expenses;
import seedu.address.model.expenses.Money;
import seedu.address.model.guest.Guest;
import seedu.address.model.guest.exceptions.DuplicateGuestException;
import seedu.address.model.room.Room;
import seedu.address.model.room.booking.exceptions.NoBookingException;
import seedu.address.model.room.booking.exceptions.RoomNotCheckedInException;
import seedu.address.testutil.GuestBuilder;
import seedu.address.testutil.TypicalExpenses;
import seedu.address.testutil.TypicalRoomNumbers;
import seedu.address.testutil.TypicalRooms;

public class ConciergeTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Concierge concierge = new Concierge();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), concierge.getGuestList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        concierge.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyConcierge_replacesData() {
        Concierge newData = getTypicalConciergeClean();
        concierge.resetData(newData);
        assertEquals(newData, concierge);
    }

    @Test
    public void resetData_withDuplicateGuests_throwsDuplicateGuestException() {
        // Two guests with the same identity fields
        Guest editedAlice = new GuestBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Guest> newGuests = Arrays.asList(ALICE, editedAlice);

        ConciergeStub newData = new ConciergeStub(newGuests, new ArrayList<>());

        thrown.expect(DuplicateGuestException.class);
        concierge.resetData(newData);
    }

    /*===================== Guests Test =========================================================== */

    @Test
    public void hasGuest_nullGuest_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        concierge.hasGuest(null);
    }

    @Test
    public void hasGuest_guestNotInConcierge_returnsFalse() {
        assertFalse(concierge.hasGuest(ALICE));
    }

    @Test
    public void hasGuest_guestInConcierge_returnsTrue() {
        concierge.addGuest(ALICE);
        assertTrue(concierge.hasGuest(ALICE));
    }

    @Test
    public void hasGuest_guestWithSameIdentityFieldsInConcierge_returnsTrue() {
        concierge.addGuest(ALICE);
        Guest editedAlice = new GuestBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(concierge.hasGuest(editedAlice));
    }

    @Test
    public void getGuestList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        concierge.getGuestList().remove(0);
    }

    /*===================== Rooms Test =========================================================== */

    // Note: no need to test the other room methods, because they only call the methods that belong to the following
    // class, which have all already been tested in the classes' own tests.

    @Test
    public void getRoomList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        concierge.getRoomList().remove(0);
    }

    /*===================== Menu Test =========================================================== */

    @Test
    public void getMenuMap_modifyMap_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        concierge.getMenuMap().put("1", new ExpenseType("1", "-", new Money(0, 0)));
    }

    //===================== Expenses Test ======================================================

    @Test
    public void addExpense_noBookings_throwsNoBookingException() {
        concierge.setRooms(TypicalRooms.getTypicalUniqueRoomListClean().asUnmodifiableObservableList());
        thrown.expect(NoBookingException.class);
        concierge.addExpense(concierge.getRoomList().get(0).roomNumber, TypicalExpenses.EXPENSE_RS01);
    }

    @Test
    public void addExpense_notCheckedIn_throwsNotCheckedInException() {
        concierge.setRooms(TypicalRooms.getTypicalUniqueRoomList());
        // get room 011, which is not checked in, based on TypicalRooms
        Room notCheckedInRoom = concierge.getRoomList().stream()
                .filter(r -> r.getRoomNumber().equals(TypicalRoomNumbers.ROOM_NUMBER_011))
                .findFirst().get();
        thrown.expect(RoomNotCheckedInException.class);
        concierge.addExpense(notCheckedInRoom.getRoomNumber(), TypicalExpenses.EXPENSE_RS01);
    }

    @Test
    public void addExpense_hasBookingAndCheckedIn_success() {
        concierge.setRooms(TypicalRooms.getTypicalUniqueRoomList());
        // get room 011, which is not checked in, based on TypicalRooms
        Room room = concierge.getRoomList().stream()
                .filter(r -> r.getRoomNumber().equals(TypicalRoomNumbers.ROOM_NUMBER_011))
                .findFirst().get();
        concierge.checkInRoom(room.getRoomNumber());
        concierge.addExpense(room.getRoomNumber(), TypicalExpenses.EXPENSE_RS01);
        Expenses actualExpenses = concierge.getRoomList().stream()
                .filter(r -> r.getRoomNumber().equals(TypicalRoomNumbers.ROOM_NUMBER_011))
                .findFirst().get().getExpenses();
        Expenses expectedExpenses = new Expenses(Arrays.asList(TypicalExpenses.EXPENSE_RS01));
        assertEquals(actualExpenses, expectedExpenses);
    }

    /**
     * A stub ReadOnlyConcierge whose guests list can violate interface constraints.
     */
    private static class ConciergeStub implements ReadOnlyConcierge {
        private final ObservableList<Guest> guests = FXCollections.observableArrayList();
        private final ObservableList<Guest> checkedInGuests = FXCollections.observableArrayList();
        private final ObservableList<Room> rooms = FXCollections.observableArrayList();

        ConciergeStub(Collection<Guest> guests, Collection<Room> rooms) {
            this.guests.setAll(guests);
            this.rooms.setAll(rooms);
        }

        @Override
        public ObservableList<Guest> getGuestList() {
            return guests;
        }

        @Override
        public ObservableList<Guest> getCheckedInGuestList() {
            return checkedInGuests;
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
