package seedu.address.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.Concierge;
import seedu.address.model.ReadOnlyConcierge;
import seedu.address.model.expenses.Expense;
import seedu.address.model.expenses.ExpenseType;
import seedu.address.model.expenses.Money;
import seedu.address.model.guest.Email;
import seedu.address.model.guest.Guest;
import seedu.address.model.guest.Name;
import seedu.address.model.guest.Phone;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.UniqueRoomList;
import seedu.address.model.room.booking.Booking;
import seedu.address.model.room.booking.BookingPeriod;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Concierge} with sample data.
 */
public class SampleDataUtil {
    public static Guest[] getSampleGuests() {
        return new Guest[] {
            new Guest(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    getTagSet("friends")),
            new Guest(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    getTagSet("colleagues", "friends")),
            new Guest(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    getTagSet("neighbours")),
            new Guest(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    getTagSet("family"))
        };
    }

    /**
     * Returns a room list initialized with the maximum number of rooms as set in RoomNumber class
     */
    public static List<Room> getSampleRooms() {
        return new UniqueRoomList().asUnmodifiableObservableList();
    }

    /**
     * Returns a room list initialized with the maximum number of rooms, and a few sample bookings
     * Use to see if XML file reflects changes
     * TODO DELETE WHEN TESTED IN UNIT TESTS
     */
    public static List<Room> getSampleRoomsWithBookingsExpenses() {
        UniqueRoomList uniqueRoomList = new UniqueRoomList();

        Room oldRoom001 = uniqueRoomList.getRoom(new RoomNumber("001"));
        Room newRoom001 = oldRoom001
                .addBooking(new Booking(getSampleGuests()[0], new BookingPeriod(
                        LocalDate.now().format(BookingPeriod.DATE_TO_STRING_FORMAT),
                        LocalDate.now().plusDays(1).format(BookingPeriod.DATE_TO_STRING_FORMAT))))
                .addExpense(new Expense(getSampleExpenseTypes()[0]));
        uniqueRoomList.setRoom(oldRoom001, newRoom001);

        Room oldRoom002 = uniqueRoomList.getRoom(new RoomNumber("002"));
        Room newRoom002 = oldRoom002
                .addBooking(new Booking(getSampleGuests()[1], new BookingPeriod(
                        LocalDate.now().format(BookingPeriod.DATE_TO_STRING_FORMAT),
                        LocalDate.now().plusDays(2).format(BookingPeriod.DATE_TO_STRING_FORMAT))));
        uniqueRoomList.setRoom(oldRoom002, newRoom002);

        Room oldRoom003 = uniqueRoomList.getRoom(new RoomNumber("003"));
        Room newRoom003 = oldRoom003
                .addBooking(new Booking(getSampleGuests()[2], new BookingPeriod(
                        LocalDate.now().plusDays(1).format(BookingPeriod.DATE_TO_STRING_FORMAT),
                        LocalDate.now().plusDays(2).format(BookingPeriod.DATE_TO_STRING_FORMAT))));
        uniqueRoomList.setRoom(oldRoom003, newRoom003);

        Room oldRoom004 = uniqueRoomList.getRoom(new RoomNumber("004"));
        Room newRoom004 = oldRoom004
                .addBooking(new Booking(getSampleGuests()[3], new BookingPeriod(
                        LocalDate.now().minusDays(1).format(BookingPeriod.DATE_TO_STRING_FORMAT),
                        LocalDate.now().plusDays(2).format(BookingPeriod.DATE_TO_STRING_FORMAT))));
        uniqueRoomList.setRoom(oldRoom004, newRoom004);

        return uniqueRoomList.asUnmodifiableObservableList();
    }

    public static ExpenseType[] getSampleExpenseTypes() {
        return new ExpenseType[] {
            new ExpenseType("RS01", "Room service: Red wine", new Money(50, 0)),
            new ExpenseType("RS02", "Room service: Beef steak", new Money(70, 0)),
            new ExpenseType("RS03", "Room service: Thai massage", new Money(100, 0)),
            new ExpenseType("SP01", "Swimming pool: Entry", new Money(5, 0)),
            new ExpenseType("MB01", "Minibar: Coca cola", new Money(3, 0)),
            new ExpenseType("MB02", "Minibar: Sprite", new Money(3, 0)),
            new ExpenseType("MB03", "Minibar: Tiger beer", new Money(6, 0)),
            new ExpenseType("MB04", "Minibar: Mineral water", new Money(3, 0)),
        };
    }

    public static Map<String, ExpenseType> getSampleMenuMap() {
        HashMap<String, ExpenseType> sampleMenuMap = new HashMap<>();
        for (ExpenseType expenseType : getSampleExpenseTypes()) {
            sampleMenuMap.put(expenseType.getItemNumber(), expenseType);
        }
        return sampleMenuMap;
    }

    public static ReadOnlyConcierge getEmptyConcierge() {
        Concierge sampleAb = new Concierge();
        sampleAb.setRooms(getSampleRooms());
        sampleAb.setMenu(getSampleMenuMap());
        return sampleAb;
    }

    public static ReadOnlyConcierge getSampleConcierge() {
        Concierge sampleAb = new Concierge();
        for (Guest sampleGuest : getSampleGuests()) {
            sampleAb.addGuest(sampleGuest);
        }
        sampleAb.setRooms(getSampleRoomsWithBookingsExpenses());
        sampleAb.setMenu(getSampleMenuMap());
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
