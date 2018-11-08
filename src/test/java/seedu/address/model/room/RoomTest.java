package seedu.address.model.room;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROOM_NUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HANDICAP;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBookings.LASTWEEK_YESTERDAY_CHECKED_IN;
import static seedu.address.testutil.TypicalBookings.TODAY_TOMORROW;
import static seedu.address.testutil.TypicalBookings.TOMORROW_NEXTWEEK;
import static seedu.address.testutil.TypicalBookings.YESTERDAY_TODAY;
import static seedu.address.testutil.TypicalBookings.getTypicalBookingsLastWeekYesterday;
import static seedu.address.testutil.TypicalBookings.getTypicalBookingsLastWeekYesterdayCheckedIn;
import static seedu.address.testutil.TypicalBookings.getTypicalBookingsTodayTomorrow;
import static seedu.address.testutil.TypicalBookings.getTypicalBookingsTomorrowNextWeek;
import static seedu.address.testutil.TypicalBookings.getTypicalBookingsYesterdayToday;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.expenses.Expense;
import seedu.address.model.room.booking.Booking;
import seedu.address.model.room.booking.exceptions.BookingAlreadyCheckedInException;
import seedu.address.model.room.booking.exceptions.BookingNotFoundException;
import seedu.address.model.room.booking.exceptions.ExpiredBookingException;
import seedu.address.model.room.booking.exceptions.InactiveBookingCheckInException;
import seedu.address.model.room.booking.exceptions.NoBookingException;
import seedu.address.model.room.booking.exceptions.OverlappingBookingException;
import seedu.address.model.room.booking.exceptions.RoomNotCheckedInException;
import seedu.address.testutil.ExpenseBuilder;
import seedu.address.testutil.RoomBuilder;
import seedu.address.testutil.TypicalExpenses;

public class RoomTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Room testRoomWithLastWeekYesterdayBooking = new RoomBuilder()
            .withBookings(getTypicalBookingsLastWeekYesterday()).build();
    private final Room testRoomWithLastWeekYesterdayBookingCheckedIn = new RoomBuilder()
            .withBookings(getTypicalBookingsLastWeekYesterdayCheckedIn()).build();
    private final Room testRoomWithYesterdayTodayBooking = new RoomBuilder()
            .withBookings(getTypicalBookingsYesterdayToday()).build();
    private final Room testRoomWithTodayTomorrowBooking = new RoomBuilder()
            .withBookings(getTypicalBookingsTodayTomorrow()).build();
    private final Room testRoomWithTomorrowNextWeekBooking = new RoomBuilder()
            .withBookings(getTypicalBookingsTomorrowNextWeek()).build();
    private final Room testRoomWithoutBooking = new RoomBuilder().build();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Room room = new RoomBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        room.getTags().remove(0);
    }

    @Test
    public void addBooking() {
        Booking bookingToAdd = testRoomWithTodayTomorrowBooking.getBookings().getFirstBooking();
        Room editedRoom = testRoomWithoutBooking.addBooking(bookingToAdd);
        assertEquals(testRoomWithTodayTomorrowBooking, editedRoom);

        // add overlapping booking -> throw exception
        assertThrows(OverlappingBookingException.class, () -> editedRoom.addBooking(bookingToAdd));
    }

    @Test
    public void checkIn_expiredBooking_throwsNoActiveBookingException() {
        thrown.expect(ExpiredBookingException.class);
        testRoomWithLastWeekYesterdayBooking.checkIn();
    }

    @Test
    public void checkIn_yesterdayToday_success() {
        Room editedRoom = testRoomWithYesterdayTodayBooking.checkIn();
        assertTrue(editedRoom.getBookings().getFirstBooking().getIsCheckedIn());
    }

    @Test
    public void checkIn_todayTomorrow_success() {
        Room editedRoom = testRoomWithTodayTomorrowBooking.checkIn();
        assertTrue(editedRoom.getBookings().getFirstBooking().getIsCheckedIn());
    }

    @Test
    public void checkIn_upcomingBooking_throwsNoActiveBookingException() {
        thrown.expect(InactiveBookingCheckInException.class);
        testRoomWithTomorrowNextWeekBooking.checkIn();
    }

    @Test
    public void checkIn_occupiedRoomCheckin_throwsOccupiedRoomCheckinException() {
        Room editedRoom = testRoomWithTodayTomorrowBooking.checkIn();
        thrown.expect(BookingAlreadyCheckedInException.class);
        editedRoom.checkIn();
    }

    @Test
    public void checkIn_noBooking_throwsNoBookingException() {
        thrown.expect(NoBookingException.class);
        testRoomWithoutBooking.checkIn();
    }

    @Test
    public void checkout_noBooking_throwsBookingNotFoundException() {
        thrown.expect(BookingNotFoundException.class);
        testRoomWithLastWeekYesterdayBooking.checkout(TODAY_TOMORROW);
    }

    @Test
    public void checkOutExpiredBooking_lastweekYesterday_success() {
        Room editedRoom = testRoomWithLastWeekYesterdayBookingCheckedIn.checkout(LASTWEEK_YESTERDAY_CHECKED_IN);
        assertEquals(testRoomWithoutBooking, editedRoom);
    }

    @Test
    public void checkOutActiveBooking_yesterdayToday_success() {
        Room editedRoom = testRoomWithYesterdayTodayBooking.checkout(YESTERDAY_TODAY);
        assertEquals(testRoomWithoutBooking, editedRoom);
    }

    @Test
    public void checkOutUpcomingBooking_tomorrowNextWeek_success() {
        Room editedRoom = testRoomWithTomorrowNextWeekBooking.checkout(TOMORROW_NEXTWEEK);
        assertEquals(testRoomWithoutBooking, editedRoom);
    }

    @Test
    public void addExpense() {
        Expense expenseToAdd = new ExpenseBuilder().build();
        Room editedRoom = testRoomWithTodayTomorrowBooking.checkIn().addExpense(expenseToAdd);
        List<Expense> actualExpenseList = editedRoom.getExpenses().getExpensesList();
        assertEquals(1, actualExpenseList.size());
        assertEquals(expenseToAdd, actualExpenseList.get(0));
    }

    @Test
    public void addExpense_noBookings_throwsNoBookingException() {
        Expense expenseToAdd = new ExpenseBuilder().build();
        thrown.expect(NoBookingException.class);
        testRoomWithoutBooking.addExpense(expenseToAdd);
    }

    @Test
    public void addExpense_notCheckedIn_throwsRoomNotCheckedInException() {
        Expense expenseToAdd = new ExpenseBuilder().build();
        thrown.expect(RoomNotCheckedInException.class);
        testRoomWithTodayTomorrowBooking.addExpense(expenseToAdd);
    }

    @Test
    public void hasBookings() {
        // has booking -> true
        assertTrue(testRoomWithLastWeekYesterdayBooking.hasBookings());

        // has no bookings -> false
        assertFalse(testRoomWithoutBooking.hasBookings());

        // has no bookings after checkout
        assertFalse(testRoomWithYesterdayTodayBooking.checkout(YESTERDAY_TODAY).hasBookings());
    }

    @Test
    public void isSameRoom() {
        // same object -> returns true
        assertTrue(testRoomWithYesterdayTodayBooking.isSameRoom(testRoomWithTodayTomorrowBooking));

        // null -> returns false
        assertFalse(testRoomWithYesterdayTodayBooking.isSameRoom(null));

        // different room number -> returns false
        Room editedRoom = new RoomBuilder(testRoomWithYesterdayTodayBooking).withRoomNumber("002").build();
        assertFalse(testRoomWithYesterdayTodayBooking.isSameRoom(editedRoom));

        // same room number, different capacity -> returns true
        editedRoom = new RoomBuilder(testRoomWithYesterdayTodayBooking).withCapacity(Capacity.DOUBLE).build();
        assertTrue(testRoomWithYesterdayTodayBooking.isSameRoom(editedRoom));

        // same room number, different expenses -> returns true
        editedRoom = new RoomBuilder(testRoomWithLastWeekYesterdayBookingCheckedIn)
                .withExpenses(TypicalExpenses.getTypicalExpenses().getExpensesList()).build();
        assertTrue(testRoomWithLastWeekYesterdayBookingCheckedIn.isSameRoom(editedRoom));

        // same room number, different bookings -> returns true
        editedRoom = new RoomBuilder(testRoomWithYesterdayTodayBooking)
                .withBookings(getTypicalBookingsTodayTomorrow()).build();
        assertTrue(testRoomWithYesterdayTodayBooking.isSameRoom(editedRoom));

        // same room number, different tags -> return true
        editedRoom = new RoomBuilder(testRoomWithYesterdayTodayBooking).withTags(VALID_TAG_HANDICAP).build();
        assertTrue(testRoomWithYesterdayTodayBooking.isSameRoom(editedRoom));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Room roomCopy = new RoomBuilder(testRoomWithYesterdayTodayBooking).build();
        assertTrue(testRoomWithYesterdayTodayBooking.equals(roomCopy));

        // same object -> returns true
        assertTrue(testRoomWithYesterdayTodayBooking.equals(testRoomWithYesterdayTodayBooking));

        // null -> returns false
        assertFalse(testRoomWithYesterdayTodayBooking.equals(null));

        // different type -> returns false
        assertFalse(testRoomWithYesterdayTodayBooking.equals(5));

        // different guest -> returns false
        assertFalse(testRoomWithYesterdayTodayBooking.equals(testRoomWithTodayTomorrowBooking));

        // different room number -> returns false
        Room editedRoom = new RoomBuilder(testRoomWithYesterdayTodayBooking)
                .withRoomNumber(VALID_ROOM_NUMBER_BOB).build();

        assertFalse(testRoomWithYesterdayTodayBooking.equals(editedRoom));

        // different capacity -> returns false
        editedRoom = new RoomBuilder(testRoomWithYesterdayTodayBooking)
                .withCapacity(Capacity.DOUBLE).build();
        assertFalse(testRoomWithYesterdayTodayBooking.equals(editedRoom));

        // different expenses -> returns false
        editedRoom = new RoomBuilder(testRoomWithLastWeekYesterdayBookingCheckedIn)
                .withExpenses(TypicalExpenses.getTypicalExpenses().getExpensesList()).build();
        assertFalse(testRoomWithLastWeekYesterdayBookingCheckedIn.equals(editedRoom));

        // different bookings -> returns false
        editedRoom = new RoomBuilder(testRoomWithYesterdayTodayBooking)
                .withBookings(getTypicalBookingsTodayTomorrow()).build();
        assertFalse(testRoomWithYesterdayTodayBooking.equals(editedRoom));

        // different tags -> returns false
        editedRoom = new RoomBuilder(testRoomWithYesterdayTodayBooking)
                .withTags(VALID_TAG_HANDICAP).build();
        assertFalse(testRoomWithYesterdayTodayBooking.equals(editedRoom));
    }

}
