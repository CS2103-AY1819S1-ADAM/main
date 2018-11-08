package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBookings.LASTWEEK_YESTERDAY;
import static seedu.address.testutil.TypicalBookings.TODAY_NEXTWEEK;
import static seedu.address.testutil.TypicalBookings.TODAY_TOMORROW;
import static seedu.address.testutil.TypicalBookings.TOMORROW_NEXTWEEK;
import static seedu.address.testutil.TypicalBookings.YESTERDAY_TODAY;
import static seedu.address.testutil.TypicalConcierge.getTypicalConciergeClean;
import static seedu.address.testutil.TypicalExpenses.EXPENSE_RS01;
import static seedu.address.testutil.TypicalGuests.ALICE;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_050;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_051;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_052;

import java.time.LocalDate;
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
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.Booking;
import seedu.address.model.room.booking.exceptions.BookingNotFoundException;
import seedu.address.model.room.booking.exceptions.ExpiredBookingException;
import seedu.address.model.room.booking.exceptions.NewBookingStartsBeforeOldBookingCheckedIn;
import seedu.address.model.room.booking.exceptions.NoBookingException;
import seedu.address.model.room.booking.exceptions.OldBookingStartsBeforeNewBookingCheckedIn;
import seedu.address.model.room.booking.exceptions.OverlappingBookingException;
import seedu.address.model.room.booking.exceptions.RoomNotCheckedInException;
import seedu.address.model.room.exceptions.OriginalRoomReassignException;
import seedu.address.testutil.GuestBuilder;

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

    /*===================== Archived Guests Test =========================================================== */

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
        Guest editedAlice = new GuestBuilder(ALICE)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(concierge.hasGuest(editedAlice));
    }

    @Test
    public void getGuestList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        concierge.getGuestList().remove(0);
    }

    /*===================== Checked-in Guests Test =========================================================== */


    @Test
    public void hasCheckedInGuest_nullGuest_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        concierge.hasCheckedInGuest(null);
    }

    @Test
    public void hasCheckedInGuest_guestNotInConcierge_returnsFalse() {
        assertFalse(concierge.hasCheckedInGuest(ALICE));
    }

    @Test
    public void hasCheckedInGuest_guestInConcierge_returnsTrue() {
        concierge.addCheckedInGuestIfNotPresent(ALICE);
        assertTrue(concierge.hasCheckedInGuest(ALICE));
    }

    @Test
    public void hasCheckedInGuest_guestWithSameIdentityFieldsInConcierge_returnsTrue() {
        concierge.addCheckedInGuestIfNotPresent(ALICE);
        Guest editedAlice = new GuestBuilder(ALICE)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(concierge.hasCheckedInGuest(editedAlice));
    }

    @Test
    public void getCheckedInGuestList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        concierge.getGuestList().remove(0);
    }

    /*===================== Rooms Test =========================================================== */

    @Test
    public void getRoomList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        concierge.getRoomList().remove(0);
    }

    @Test
    public void checkInRoom_guestNotCheckedIn_addGuestToCheckedInList() {
        RoomNumber roomNumber = ROOM_NUMBER_050;
        Booking booking = TODAY_TOMORROW;
        Guest guest = booking.getGuest();
        assertFalse(concierge.hasCheckedInGuest(guest));

        concierge.addBooking(roomNumber, booking);
        concierge.checkInRoom(roomNumber);
        assertTrue(concierge.hasCheckedInGuest(guest));
    }

    @Test
    public void checkInRoom_guestCheckedIn_doNothing() {
        RoomNumber roomNumber = ROOM_NUMBER_050;
        RoomNumber secondRoomNumber = ROOM_NUMBER_051;
        Booking booking = TODAY_TOMORROW;

        concierge.addBooking(roomNumber, booking);
        concierge.checkInRoom(roomNumber);
        assertEquals(1, concierge.getCheckedInGuestList().size());

        concierge.addBooking(secondRoomNumber, booking);
        concierge.checkInRoom(secondRoomNumber);
        assertEquals(1, concierge.getCheckedInGuestList().size());
    }

    @Test
    public void checkout_noBooking_throwNoBookingException() {
        assertThrows(NoBookingException.class, () -> concierge.checkoutRoom(ROOM_NUMBER_050));
    }

    @Test
    public void checkout_guestNotCheckedIn_bothGuestListsNotModified() {
        RoomNumber roomNumber = ROOM_NUMBER_050;
        Booking booking = TODAY_TOMORROW;

        concierge.addBooking(roomNumber, booking);
        assertEquals(0, concierge.getCheckedInGuestList().size());
        assertEquals(0, concierge.getGuestList().size());

        concierge.checkoutRoom(roomNumber);

        assertEquals(0, concierge.getCheckedInGuestList().size());
        assertEquals(0, concierge.getGuestList().size());
    }

    @Test
    public void checkout_guestHasOtherCheckedInBookings_checkedInGuestListNotModified() {
        RoomNumber roomNumber = ROOM_NUMBER_050;
        RoomNumber secondRoomNumber = ROOM_NUMBER_051;
        Booking booking = TODAY_TOMORROW;

        concierge.addBooking(roomNumber, booking);
        concierge.checkInRoom(roomNumber);
        assertEquals(1, concierge.getCheckedInGuestList().size());
        assertEquals(0, concierge.getGuestList().size());

        concierge.addBooking(secondRoomNumber, booking);
        concierge.checkInRoom(secondRoomNumber);

        concierge.checkoutRoom(roomNumber);

        assertEquals(1, concierge.getCheckedInGuestList().size());
        assertEquals(1, concierge.getGuestList().size());
    }

    @Test
    public void checkout_guestExistsInArchivedGuestList_archivedGuestListNotModified() {
        RoomNumber roomNumber = ROOM_NUMBER_050;
        Booking booking = TODAY_TOMORROW;
        Guest guest = booking.getGuest();

        concierge.addGuest(guest);
        concierge.addBooking(roomNumber, booking);
        concierge.checkInRoom(roomNumber);
        assertEquals(1, concierge.getCheckedInGuestList().size());
        assertEquals(1, concierge.getGuestList().size());

        concierge.checkoutRoom(roomNumber);

        assertEquals(0, concierge.getCheckedInGuestList().size());
        assertEquals(1, concierge.getGuestList().size());
    }

    @Test
    public void checkout_guestHasOtherCheckedInBookingsAndExistsInArchivedGuestList_bothGuestListsModified() {
        RoomNumber roomNumber = ROOM_NUMBER_050;
        RoomNumber secondRoomNumber = ROOM_NUMBER_051;
        Booking booking = TODAY_TOMORROW;
        Guest guest = booking.getGuest();

        concierge.addGuest(guest);
        concierge.addBooking(roomNumber, booking);
        concierge.checkInRoom(roomNumber);
        assertEquals(1, concierge.getCheckedInGuestList().size());
        assertEquals(1, concierge.getGuestList().size());

        concierge.addBooking(secondRoomNumber, booking);
        concierge.checkInRoom(secondRoomNumber);

        concierge.checkoutRoom(roomNumber);

        assertEquals(1, concierge.getCheckedInGuestList().size());
        assertEquals(1, concierge.getGuestList().size());
    }

    @Test
    public void reassignRoom_newRoomSameAsOriginal_throwOriginalRoomReassignException() {
        RoomNumber roomNumber = ROOM_NUMBER_050;
        Booking booking = TODAY_TOMORROW;
        concierge.addBooking(roomNumber, booking);

        RoomNumber newRoomNumber = ROOM_NUMBER_050;
        LocalDate startDate = booking.getBookingPeriod().getStartDate();

        assertThrows(
            OriginalRoomReassignException.class, () ->
                concierge.reassignRoom(roomNumber, startDate, newRoomNumber));
    }

    @Test
    public void reassignRoom_nonExistentBooking_throwBookingNotFoundException() {
        RoomNumber roomNumber = ROOM_NUMBER_050;
        Booking booking = TODAY_TOMORROW;
        concierge.addBooking(roomNumber, booking);

        RoomNumber newRoomNumber = ROOM_NUMBER_051;
        Booking nonExistentBooking = YESTERDAY_TODAY;
        LocalDate startDate = nonExistentBooking.getBookingPeriod().getStartDate();

        assertThrows(
                BookingNotFoundException.class, () ->
                concierge.reassignRoom(roomNumber, startDate, newRoomNumber));
    }

    @Test
    public void reassignRoom_expiredBooking_throwExpiredBookingException() {
        RoomNumber roomNumber = ROOM_NUMBER_050;
        Booking booking = LASTWEEK_YESTERDAY;
        concierge.addBooking(roomNumber, booking);

        RoomNumber newRoomNumber = ROOM_NUMBER_051;
        LocalDate startDate = booking.getBookingPeriod().getStartDate();

        assertThrows(
                ExpiredBookingException.class, () ->
                concierge.reassignRoom(roomNumber, startDate, newRoomNumber));
    }

    @Test
    public void reassignRoom_overlappingBooking_throwOverlappingBookingException() {
        RoomNumber roomNumber = ROOM_NUMBER_050;
        Booking booking = TODAY_TOMORROW;
        concierge.addBooking(roomNumber, booking);

        RoomNumber newRoomNumber = ROOM_NUMBER_051;
        Booking newRoomBooking = TODAY_NEXTWEEK;
        concierge.addBooking(newRoomNumber, newRoomBooking);

        LocalDate startDate = booking.getBookingPeriod().getStartDate();

        assertThrows(
                OverlappingBookingException.class, () ->
                concierge.reassignRoom(roomNumber, startDate, newRoomNumber));
    }

    @Test
    public void reassignRoom_newBookingCheckedIn_throwOldBookingStartsBeforeNewBookingCheckedIn() {
        RoomNumber roomNumber = ROOM_NUMBER_050;
        Booking booking = YESTERDAY_TODAY;
        concierge.addBooking(roomNumber, booking);

        RoomNumber newRoomNumber = ROOM_NUMBER_051;
        Booking newRoomBooking = TODAY_TOMORROW;
        concierge.addBooking(newRoomNumber, newRoomBooking);
        concierge.checkInRoom(newRoomNumber);

        LocalDate startDate = booking.getBookingPeriod().getStartDate();

        assertThrows(
                OldBookingStartsBeforeNewBookingCheckedIn.class, () ->
                concierge.reassignRoom(roomNumber, startDate, newRoomNumber));
    }

    @Test
    public void reassignRoom_oldBookingCheckedIn_throwNewBookingStartsBeforeOldBookingCheckedIn() {
        RoomNumber roomNumber = ROOM_NUMBER_050;
        Booking booking = TODAY_TOMORROW;
        concierge.addBooking(roomNumber, booking);
        concierge.checkInRoom(roomNumber);

        RoomNumber newRoomNumber = ROOM_NUMBER_051;
        Booking newRoomBooking = YESTERDAY_TODAY;
        concierge.addBooking(newRoomNumber, newRoomBooking);
        concierge.checkInRoom(newRoomNumber);

        LocalDate startDate = booking.getBookingPeriod().getStartDate();

        assertThrows(
                NewBookingStartsBeforeOldBookingCheckedIn.class, () ->
                concierge.reassignRoom(roomNumber, startDate, newRoomNumber));
    }

    @Test
    public void reassignRoom_success() {
        RoomNumber roomNumber = ROOM_NUMBER_050;
        Booking booking = TODAY_TOMORROW;
        concierge.addBooking(roomNumber, booking);

        RoomNumber newRoomNumber = ROOM_NUMBER_051;
        Booking newRoomBooking = TOMORROW_NEXTWEEK;
        concierge.addBooking(newRoomNumber, newRoomBooking);

        LocalDate startDate = booking.getBookingPeriod().getStartDate();
        concierge.reassignRoom(roomNumber, startDate, newRoomNumber);

        assertFalse(getRoom(roomNumber).getBookings().contains(booking));
        assertTrue(getRoom(newRoomNumber).getBookings().contains(booking));

        RoomNumber emptyRoom = ROOM_NUMBER_052;
        concierge.reassignRoom(newRoomNumber, startDate, emptyRoom);

        assertTrue(getRoom(emptyRoom).getBookings().contains(booking));
    }

    @Test
    public void reassignRoom_oldBookingCheckedIn_successExpensesCarriedOver() {
        RoomNumber roomNumber = ROOM_NUMBER_050;
        Booking booking = TODAY_TOMORROW;
        concierge.addBooking(roomNumber, booking);
        concierge.checkInRoom(roomNumber);
        concierge.addExpense(roomNumber, EXPENSE_RS01);

        RoomNumber newRoomNumber = ROOM_NUMBER_051;
        Booking newRoomBooking = TOMORROW_NEXTWEEK;
        concierge.addBooking(newRoomNumber, newRoomBooking);

        LocalDate startDate = booking.getBookingPeriod().getStartDate();
        concierge.reassignRoom(roomNumber, startDate, newRoomNumber);

        assertFalse(getRoom(roomNumber).getBookings().contains(booking));

        Booking checkedInBooking = booking.checkIn();
        assertTrue(getRoom(newRoomNumber).getBookings().contains(checkedInBooking));
        assertTrue(getRoom(newRoomNumber).getExpenses().getExpensesList().contains(EXPENSE_RS01));
    }

    @Test
    public void reassignRoom_newBookingCheckedIn_successExpensesNotCarriedOver() {
        RoomNumber roomNumber = ROOM_NUMBER_050;
        Booking booking = TOMORROW_NEXTWEEK;
        concierge.addBooking(roomNumber, booking);

        RoomNumber newRoomNumber = ROOM_NUMBER_051;
        Booking newRoomBooking = TODAY_TOMORROW;
        concierge.addBooking(newRoomNumber, newRoomBooking);
        concierge.checkInRoom(newRoomNumber);
        concierge.addExpense(newRoomNumber, EXPENSE_RS01);

        LocalDate startDate = booking.getBookingPeriod().getStartDate();
        concierge.reassignRoom(roomNumber, startDate, newRoomNumber);

        assertFalse(getRoom(roomNumber).getBookings().contains(booking));
        assertTrue(getRoom(newRoomNumber).getBookings().contains(booking));
        assertEquals(Arrays.asList(EXPENSE_RS01), getRoom(newRoomNumber).getExpenses().getExpensesList());
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
        thrown.expect(NoBookingException.class);
        concierge.addExpense(ROOM_NUMBER_050, EXPENSE_RS01);
    }

    @Test
    public void addExpense_notCheckedIn_throwsNotCheckedInException() {
        concierge.addBooking(ROOM_NUMBER_050, TODAY_TOMORROW);
        thrown.expect(RoomNotCheckedInException.class);
        concierge.addExpense(ROOM_NUMBER_050, EXPENSE_RS01);
    }

    @Test
    public void addExpense_hasBookingAndCheckedIn_success() {
        RoomNumber roomNumber = ROOM_NUMBER_050;
        concierge.addBooking(roomNumber, TODAY_TOMORROW);
        concierge.checkInRoom(roomNumber);
        concierge.addExpense(roomNumber, EXPENSE_RS01);
        Expenses actualExpenses = getRoom(roomNumber).getExpenses();
        Expenses expectedExpenses = new Expenses(Arrays.asList(EXPENSE_RS01));
        assertEquals(actualExpenses, expectedExpenses);
    }

    /**
     * A test utility method to retrieve the room using its room number from the room list.
     */
    private Room getRoom(RoomNumber roomNumber) {
        return concierge
                .getRoomList()
                .stream()
                .filter(room -> room.getRoomNumber().equals(roomNumber))
                .findFirst()
                .get();
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
