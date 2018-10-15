package seedu.address.model.room;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAPACITY_DOUBLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROOM_NUMBER_074;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HANDICAP;
import static seedu.address.testutil.TypicalRooms.DOUBLE_002;
import static seedu.address.testutil.TypicalRooms.SINGLE_001;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.room.booking.Booking;
import seedu.address.model.room.booking.exceptions.NoActiveBookingException;
import seedu.address.model.room.booking.exceptions.NoActiveOrExpiredBookingException;
import seedu.address.model.room.exceptions.OccupiedRoomCheckinException;
import seedu.address.testutil.RoomBuilder;
import seedu.address.testutil.TypicalBookings;

public class RoomTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Room testRoomWithLastWeekYesterdayBooking = new RoomBuilder()
        .withBookings(TypicalBookings.getTypicalBookingsLastWeekYesterday()).build();
    private final Room testRoomWithLastWeekYesterdayBookingCheckedIn = new RoomBuilder()
        .withBookings(TypicalBookings.getTypicalBookingsLastWeekYesterdayCheckedIn()).build();
    private final Room testRoomWithYesterdayTodayBooking = new RoomBuilder()
        .withBookings(TypicalBookings.getTypicalBookingsYesterdayToday()).build();
    private final Room testRoomWithTodayTomorrowBooking = new RoomBuilder()
        .withBookings(TypicalBookings.getTypicalBookingsTodayTomorrow()).build();
    private final Room testRoomWithTomorrowNextWeekBooking = new RoomBuilder()
        .withBookings(TypicalBookings.getTypicalBookingsTomorrowNextWeek()).build();
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
        testRoomWithoutBooking.addBooking(bookingToAdd);
        assertTrue(testRoomWithoutBooking.equals(testRoomWithTodayTomorrowBooking));
    }

    @Test
    public void updateBooking() {
        Booking originalBooking = testRoomWithTodayTomorrowBooking.getBookings().getFirstBooking();
        Booking editedBooking = testRoomWithTomorrowNextWeekBooking.getBookings().getFirstBooking();
        testRoomWithTodayTomorrowBooking.updateBooking(originalBooking, editedBooking);
        assertTrue(testRoomWithTodayTomorrowBooking.equals(testRoomWithTomorrowNextWeekBooking));
    }

    @Test
    public void setBookings() {
        testRoomWithTodayTomorrowBooking.setBookings(testRoomWithYesterdayTodayBooking.getBookings());
        assertTrue(testRoomWithTodayTomorrowBooking.equals(testRoomWithYesterdayTodayBooking));
    }

    @Test
    public void isCheckedIn() {
        assertFalse(testRoomWithLastWeekYesterdayBooking.isCheckedIn());
        assertTrue(testRoomWithLastWeekYesterdayBookingCheckedIn.isCheckedIn());
        assertFalse(testRoomWithYesterdayTodayBooking.isCheckedIn());
        assertFalse(testRoomWithTodayTomorrowBooking.isCheckedIn());
        assertFalse(testRoomWithTomorrowNextWeekBooking.isCheckedIn());
    }

    @Test
    public void isCheckedIn_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        testRoomWithoutBooking.isCheckedIn();
    }

    @Test
    public void hasBooking() {
        assertTrue(testRoomWithLastWeekYesterdayBooking.hasBooking());
        assertTrue(testRoomWithYesterdayTodayBooking.hasBooking());
        assertTrue(testRoomWithTodayTomorrowBooking.hasBooking());
        assertTrue(testRoomWithTomorrowNextWeekBooking.hasBooking());
        assertFalse(testRoomWithoutBooking.hasBooking());
    }

    @Test
    public void hasActiveBooking() {
        assertFalse(testRoomWithLastWeekYesterdayBooking.hasActiveBooking());
        assertTrue(testRoomWithYesterdayTodayBooking.hasActiveBooking());
        assertTrue(testRoomWithTodayTomorrowBooking.hasActiveBooking());
        assertFalse(testRoomWithTomorrowNextWeekBooking.hasActiveBooking());
    }

    @Test
    public void hasActiveBooking_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        testRoomWithoutBooking.hasActiveBooking();
    }

    @Test
    public void hasActiveOrExpiredBooking() {
        assertTrue(testRoomWithLastWeekYesterdayBooking.hasActiveOrExpiredBooking());
        assertTrue(testRoomWithYesterdayTodayBooking.hasActiveOrExpiredBooking());
        assertTrue(testRoomWithTodayTomorrowBooking.hasActiveOrExpiredBooking());
        assertFalse(testRoomWithTomorrowNextWeekBooking.hasActiveOrExpiredBooking());
    }

    @Test
    public void hasActiveOrExpiredBooking_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        testRoomWithoutBooking.hasActiveOrExpiredBooking();
    }

    @Test
    public void checkIn_expiredBooking_throwsNoActiveBookingException() {
        thrown.expect(NoActiveBookingException.class);
        testRoomWithLastWeekYesterdayBooking.checkIn();
    }

    @Test
    public void checkIn_yesterdayToday_success() {
        testRoomWithYesterdayTodayBooking.checkIn();
        assertTrue(testRoomWithYesterdayTodayBooking.isCheckedIn());
    }

    @Test
    public void checkIn_todayTomorrow_success() {
        testRoomWithTodayTomorrowBooking.checkIn();
        assertTrue(testRoomWithTodayTomorrowBooking.isCheckedIn());
    }

    @Test
    public void checkIn_upcomingBooking_throwsNoActiveBookingException() {
        thrown.expect(NoActiveBookingException.class);
        testRoomWithTomorrowNextWeekBooking.checkIn();
    }

    @Test
    public void checkIn_occupiedRoomCheckin_throwsOccupiedRoomCheckinException() {
        testRoomWithTodayTomorrowBooking.checkIn();
        thrown.expect(OccupiedRoomCheckinException.class);
        testRoomWithTodayTomorrowBooking.checkIn();
    }

    @Test
    public void checkIn_noBooking_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        testRoomWithoutBooking.checkIn();
    }

    @Test
    public void checkOut_lastweekYesterday_success() {
        testRoomWithLastWeekYesterdayBookingCheckedIn.checkout();
        assertTrue(testRoomWithLastWeekYesterdayBookingCheckedIn.equals(testRoomWithoutBooking));
    }

    @Test
    public void checkOut_yesterdayTodaysuccess() {
        testRoomWithYesterdayTodayBooking.checkIn();
        testRoomWithYesterdayTodayBooking.checkout();
        assertTrue(testRoomWithYesterdayTodayBooking.equals(testRoomWithoutBooking));
    }

    @Test
    public void checkOut_todayTomorrowsuccess() {
        testRoomWithTodayTomorrowBooking.checkIn();
        testRoomWithTodayTomorrowBooking.checkout();
        assertTrue(testRoomWithTodayTomorrowBooking.equals(testRoomWithoutBooking));
    }

    @Test
    public void checkOut_upcomingBooking_throwsNoActiveBookingException() {
        thrown.expect(NoActiveOrExpiredBookingException.class);
        testRoomWithTomorrowNextWeekBooking.checkout();
    }

    @Test
    public void checkOut_noBooking_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        testRoomWithoutBooking.checkout();
    }

    @Test
    public void isSameRoom() {
        // same object -> returns true
        assertTrue(SINGLE_001.isSameRoom(SINGLE_001));

        // null -> returns false
        assertFalse(SINGLE_001.isSameRoom(null));

        // different room number -> returns false
        Room editedSingle001 = new RoomBuilder(SINGLE_001).withRoomNumber("002").build();
        assertFalse(SINGLE_001.isSameRoom(editedSingle001));

        // same room number, different capacity -> returns true
        editedSingle001 = new RoomBuilder(SINGLE_001).withCapacity(VALID_CAPACITY_DOUBLE).build();
        assertTrue(SINGLE_001.isSameRoom(editedSingle001));

        /* KIV Expenses
        // same room number, different expenses -> returns true
        editedSingle001 = new RoomBuilder(SINGLE_001).withExpenses(new Expenses()).build();
        assertTrue(SINGLE_001.isSameRoom(editedSingle001));
        */

        // same room number, different bookings -> returns true
        editedSingle001 = new RoomBuilder(SINGLE_001).withBookings(TypicalBookings.getTypicalBookingsTodayTomorrow())
            .build();
        assertTrue(SINGLE_001.isSameRoom(editedSingle001));

        // same room number, different tags -> return true
        editedSingle001 = new RoomBuilder(SINGLE_001).withTags(VALID_TAG_HANDICAP).build();
        assertTrue(SINGLE_001.isSameRoom(editedSingle001));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Room roomCopy = new RoomBuilder(SINGLE_001).build();
        assertTrue(SINGLE_001.equals(roomCopy));

        // same object -> returns true
        assertTrue(SINGLE_001.equals(SINGLE_001));

        // null -> returns false
        assertFalse(SINGLE_001.equals(null));

        // different type -> returns false
        assertFalse(SINGLE_001.equals(5));

        // different guest -> returns false
        assertFalse(SINGLE_001.equals(DOUBLE_002));

        // different room number -> returns false
        Room editedSingle001 = new RoomBuilder(SINGLE_001).withRoomNumber(VALID_ROOM_NUMBER_074).build();
        assertFalse(SINGLE_001.equals(editedSingle001));

        // different capacity -> returns false
        editedSingle001 = new RoomBuilder(SINGLE_001).withCapacity(VALID_CAPACITY_DOUBLE).build();
        assertFalse(SINGLE_001.equals(editedSingle001));

        /* KIV Expenses
        // different capacity -> returns false
        editedSingle001 = new RoomBuilder(SINGLE_001).withExpenses(new Expenses()).build();
        assertFalse(SINGLE_001.equals(editedSingle001));
        */

        // different bookings -> returns false
        editedSingle001 = new RoomBuilder(SINGLE_001).withBookings(TypicalBookings.getTypicalBookingsTodayTomorrow())
            .build();
        assertFalse(SINGLE_001.equals(editedSingle001));

        // different tags -> returns false
        editedSingle001 = new RoomBuilder(SINGLE_001).withTags(VALID_TAG_HANDICAP).build();
        assertFalse(SINGLE_001.equals(editedSingle001));
    }
}
