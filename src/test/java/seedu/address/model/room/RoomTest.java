package seedu.address.model.room;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAPACITY_DOUBLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROOM_NUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HANDICAP;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.room.booking.Booking;
import seedu.address.model.room.booking.exceptions.NoActiveBookingException;
import seedu.address.model.room.booking.exceptions.NoActiveOrExpiredBookingException;
import seedu.address.model.room.booking.exceptions.NoBookingException;
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
        Room editedRoom = testRoomWithoutBooking.addBooking(bookingToAdd);
        assertTrue(editedRoom.equals(testRoomWithTodayTomorrowBooking));
    }

    @Test
    public void updateBooking() {
        Booking originalBooking = testRoomWithTodayTomorrowBooking.getBookings().getFirstBooking();
        Booking editedBooking = testRoomWithTomorrowNextWeekBooking.getBookings().getFirstBooking();
        Room editedRoom = testRoomWithTodayTomorrowBooking.updateBooking(originalBooking, editedBooking);
        assertTrue(editedRoom.equals(testRoomWithTomorrowNextWeekBooking));
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
    public void isCheckedIn_throwsNoBookingException() {
        thrown.expect(NoBookingException.class);
        testRoomWithoutBooking.isCheckedIn();
    }

    @Test
    public void hasBookings() {
        assertTrue(testRoomWithLastWeekYesterdayBooking.hasBookings());
        assertTrue(testRoomWithYesterdayTodayBooking.hasBookings());
        assertTrue(testRoomWithTodayTomorrowBooking.hasBookings());
        assertTrue(testRoomWithTomorrowNextWeekBooking.hasBookings());
        assertFalse(testRoomWithoutBooking.hasBookings());
    }

    @Test
    public void hasActiveBooking() {
        assertFalse(testRoomWithLastWeekYesterdayBooking.hasActiveBooking());
        assertTrue(testRoomWithYesterdayTodayBooking.hasActiveBooking());
        assertTrue(testRoomWithTodayTomorrowBooking.hasActiveBooking());
        assertFalse(testRoomWithTomorrowNextWeekBooking.hasActiveBooking());
    }

    @Test
    public void hasActiveBooking_throwsNoBookingException() {
        thrown.expect(NoBookingException.class);
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
    public void hasActiveOrExpiredBooking_throwsNoBookingException() {
        thrown.expect(NoBookingException.class);
        testRoomWithoutBooking.hasActiveOrExpiredBooking();
    }

    @Test
    public void checkIn_expiredBooking_throwsNoActiveBookingException() {
        thrown.expect(NoActiveBookingException.class);
        testRoomWithLastWeekYesterdayBooking.checkIn();
    }

    @Test
    public void checkIn_yesterdayToday_success() {
        Room editedRoom = testRoomWithYesterdayTodayBooking.checkIn();
        assertTrue(editedRoom.isCheckedIn());
    }

    @Test
    public void checkIn_todayTomorrow_success() {
        Room editedRoom = testRoomWithTodayTomorrowBooking.checkIn();
        assertTrue(editedRoom.isCheckedIn());
    }

    @Test
    public void checkIn_upcomingBooking_throwsNoActiveBookingException() {
        thrown.expect(NoActiveBookingException.class);
        testRoomWithTomorrowNextWeekBooking.checkIn();
    }

    @Test
    public void checkIn_occupiedRoomCheckin_throwsOccupiedRoomCheckinException() {
        Room editedRoom = testRoomWithTodayTomorrowBooking.checkIn();
        thrown.expect(OccupiedRoomCheckinException.class);
        editedRoom.checkIn();
    }

    @Test
    public void checkIn_noBooking_throwsNoBookingException() {
        thrown.expect(NoBookingException.class);
        testRoomWithoutBooking.checkIn();
    }

    @Test
    public void checkOut_lastweekYesterday_success() {
        Room editedRoom = testRoomWithLastWeekYesterdayBookingCheckedIn.checkout();
        assertTrue(editedRoom.equals(testRoomWithoutBooking));
    }

    @Test
    public void checkOut_yesterdayTodaysuccess() {
        Room editedRoom = testRoomWithYesterdayTodayBooking.checkIn().checkout();
        assertTrue(editedRoom.equals(testRoomWithoutBooking));
    }

    @Test
    public void checkOut_todayTomorrowsuccess() {
        Room editedRoom = testRoomWithTodayTomorrowBooking.checkIn().checkout();
        assertTrue(editedRoom.equals(testRoomWithoutBooking));
    }

    @Test
    public void checkOut_upcomingBooking_throwsNoActiveBookingException() {
        thrown.expect(NoActiveOrExpiredBookingException.class);
        testRoomWithTomorrowNextWeekBooking.checkout();
    }

    @Test
    public void checkOut_noBooking_throwsNoBookingException() {
        thrown.expect(NoBookingException.class);
        testRoomWithoutBooking.checkout();
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
        editedRoom = new RoomBuilder(testRoomWithYesterdayTodayBooking).withCapacity(VALID_CAPACITY_DOUBLE).build();
        assertTrue(testRoomWithYesterdayTodayBooking.isSameRoom(editedRoom));

        /* KIV Expenses
        // same room number, different expenses -> returns true
        editedSingle001 = new RoomBuilder(testRoomWithYesterdayTodayBooking).withExpenses(new Expenses()).build();
        assertTrue(testRoomWithYesterdayTodayBooking.isSameRoom(editedSingle001));
        */

        // same room number, different bookings -> returns true
        editedRoom = new RoomBuilder(testRoomWithYesterdayTodayBooking)
            .withBookings(TypicalBookings.getTypicalBookingsTodayTomorrow()).build();
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
        Room editedRoom = new RoomBuilder(testRoomWithYesterdayTodayBooking).withRoomNumber(VALID_ROOM_NUMBER_BOB)
            .build();
        assertFalse(testRoomWithYesterdayTodayBooking.equals(editedRoom));

        // different capacity -> returns false
        editedRoom = new RoomBuilder(testRoomWithYesterdayTodayBooking).withCapacity(VALID_CAPACITY_DOUBLE)
            .build();
        assertFalse(testRoomWithYesterdayTodayBooking.equals(editedRoom));

        /* KIV Expenses
        // different capacity -> returns false
        editedSingle001 = new RoomBuilder(testRoomWithYesterdayTodayBooking).withExpenses(new Expenses()).build();
        assertFalse(testRoomWithYesterdayTodayBooking.equals(editedSingle001));
        */

        // different bookings -> returns false
        editedRoom = new RoomBuilder(testRoomWithYesterdayTodayBooking)
            .withBookings(TypicalBookings.getTypicalBookingsTodayTomorrow()).build();
        assertFalse(testRoomWithYesterdayTodayBooking.equals(editedRoom));

        // different tags -> returns false
        editedRoom = new RoomBuilder(testRoomWithYesterdayTodayBooking).withTags(VALID_TAG_HANDICAP).build();
        assertFalse(testRoomWithYesterdayTodayBooking.equals(editedRoom));
    }
}
