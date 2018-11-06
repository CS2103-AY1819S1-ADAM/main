package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedRoom.MESSAGE_NOT_CHECKED_IN;
import static seedu.address.storage.XmlAdaptedRoom.MESSAGE_NO_BOOKING;
import static seedu.address.storage.XmlAdaptedRoom.MISSING_FIELD_MESSAGE_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Menu;
import seedu.address.model.room.Capacity;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomNumber;
import seedu.address.testutil.Assert;
import seedu.address.testutil.ExpenseBuilder;
import seedu.address.testutil.TypicalBookings;
import seedu.address.testutil.TypicalMenu;
import seedu.address.testutil.TypicalRooms;

/**
 * Note: XmlAdaptedRoom contains Capacity, List of XmlAdaptedBooking, List of XmlAdaptedExpense, and other fields.
 * We check List of XmlAdaptedBooking by attempting to call toModelType with a list of overlapping bookings.
 * We do not check XmlAdaptedExpense because there are no restrictions on a list of expenses, unlike bookings.
 * We do not check this field here because JAXB and Unmarshaller can only parse the correct values corresponding to
 * Capacity's enum values, meaning wrong values of Capacity will trigger an exception there, not here.
 * TODO The test for enum Capacity will be covered under XmlUtilTest.
 */
public class XmlAdaptedRoomTest {

    private static final Room VALID_ROOM = TypicalRooms.getTypicalUniqueRoomList().get(0);
    private static final String VALID_ROOM_NUMBER = VALID_ROOM.getRoomNumber().toString();
    private static final Capacity VALID_CAPACITY = VALID_ROOM.getCapacity();
    private static final List<XmlAdaptedBooking> VALID_BOOKINGS = VALID_ROOM.getBookings().getSortedBookingsSet()
            .stream().map(XmlAdaptedBooking::new).collect(Collectors.toList()); // this is empty
    private static final XmlAdaptedExpense VALID_EXPENSE =
            new XmlAdaptedExpense(ExpenseBuilder.DEFAULT_EXPENSE_TYPE.getItemNumber(),
                    ExpenseBuilder.DEFAULT_COST.toString(), ExpenseBuilder.DEFAULT_DATETIME.toString());
    private static final List<XmlAdaptedExpense> VALID_EXPENSES = new ArrayList<>(Arrays.asList(VALID_EXPENSE));
    private static final List<XmlAdaptedExpense> VALID_EXPENSES_EMPTY = new ArrayList<>();
    private static final List<XmlAdaptedTag> VALID_TAGS = VALID_ROOM.getTags()
            .stream().map(XmlAdaptedTag::new).collect(Collectors.toList());

    private static final String INVALID_ROOM_NUMBER = "01";
    private static final String INVALID_TAG = "#friend";
    private static final XmlAdaptedBooking INVALID_BOOKING =
            new XmlAdaptedBooking(VALID_ROOM.getBookings().getFirstBooking());
    private static final XmlAdaptedExpense INVALID_EXPENSE =
            new XmlAdaptedExpense(ExpenseBuilder.DEFAULT_EXPENSE_TYPE.getItemNumber(),
                    "12.345", ExpenseBuilder.DEFAULT_DATETIME.toString());

    private static final Menu VALID_MENU_STUB = TypicalMenu.getTypicalMenu();

    @Test
    public void toModelType_validRoom_returnsRoom() throws Exception {
        XmlAdaptedRoom room =
                new XmlAdaptedRoom(VALID_ROOM_NUMBER, VALID_CAPACITY, VALID_BOOKINGS,
                        VALID_EXPENSES_EMPTY, VALID_TAGS);
        assertEquals(VALID_ROOM, room.toModelType(VALID_MENU_STUB));
    }

    @Test
    public void toModelType_invalidRoomNumber_throwsIllegalValueException() {
        XmlAdaptedRoom room =
                new XmlAdaptedRoom(INVALID_ROOM_NUMBER, VALID_CAPACITY, VALID_BOOKINGS, VALID_EXPENSES, VALID_TAGS);
        String expectedMessage = RoomNumber.MESSAGE_ROOM_NUMBER_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> room.toModelType(VALID_MENU_STUB));
    }

    @Test
    public void toModelType_nullRoomNumber_throwsIllegalValueException() {
        XmlAdaptedRoom room =
                new XmlAdaptedRoom(null, VALID_CAPACITY, VALID_BOOKINGS, VALID_EXPENSES, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, RoomNumber.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> room.toModelType(VALID_MENU_STUB));
    }

    @Test
    public void toModelType_nullCapacity_throwsIllegalValueException() {
        XmlAdaptedRoom room =
                new XmlAdaptedRoom(VALID_ROOM_NUMBER, null, VALID_BOOKINGS, VALID_EXPENSES, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Capacity.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> room.toModelType(VALID_MENU_STUB));
    }

    @Test
    public void toModelType_invalidBookings_throwsIllegalValueException() {
        List<XmlAdaptedBooking> invalidBookings = new ArrayList<>(VALID_BOOKINGS);
        invalidBookings.add(INVALID_BOOKING);
        XmlAdaptedRoom room =
                new XmlAdaptedRoom(VALID_ROOM_NUMBER, VALID_CAPACITY, invalidBookings, VALID_EXPENSES, VALID_TAGS);
        String expectedMessage = XmlAdaptedRoom.MESSAGE_OVERLAPPING_BOOKING;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, () -> room.toModelType(VALID_MENU_STUB));
    }

    @Test
    public void toModelType_invalidTag_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedRoom room =
                new XmlAdaptedRoom(VALID_ROOM_NUMBER, VALID_CAPACITY, VALID_BOOKINGS, VALID_EXPENSES, invalidTags);
        Assert.assertThrows(IllegalValueException.class, () -> room.toModelType(VALID_MENU_STUB));
    }

    @Test
    public void toModelType_invalidExpense_throwsIllegalValueException() {
        List<XmlAdaptedExpense> invalidExpenses = new ArrayList<>(VALID_EXPENSES);
        invalidExpenses.add(INVALID_EXPENSE);
        XmlAdaptedRoom room =
                new XmlAdaptedRoom(VALID_ROOM_NUMBER, VALID_CAPACITY, VALID_BOOKINGS, invalidExpenses, VALID_TAGS);
        Assert.assertThrows(IllegalValueException.class, () -> room.toModelType(VALID_MENU_STUB));
    }

    @Test
    public void toModelType_roomHasNoBookingButHasExpenses_throwsIllegalValueException() {
        XmlAdaptedRoom room =
                new XmlAdaptedRoom(VALID_ROOM_NUMBER, VALID_CAPACITY, new ArrayList<>(), VALID_EXPENSES, VALID_TAGS);
        Assert.assertThrows(IllegalValueException.class,
                MESSAGE_NO_BOOKING, () -> room.toModelType(VALID_MENU_STUB));
    }

    @Test
    public void toModelType_roomNotCheckedInButHasExpenses_throwsIllegalValueException() {
        List<XmlAdaptedBooking> nonEmptyBookings = TypicalBookings
                .getTypicalBookingsTodayTomorrow()
                .stream()
                .map(XmlAdaptedBooking::new)
                .collect(Collectors.toList());
        XmlAdaptedRoom room =
                new XmlAdaptedRoom(VALID_ROOM_NUMBER, VALID_CAPACITY, nonEmptyBookings, VALID_EXPENSES, VALID_TAGS);
        Assert.assertThrows(IllegalValueException.class,
                MESSAGE_NOT_CHECKED_IN, () -> room.toModelType(VALID_MENU_STUB));
    }
}
