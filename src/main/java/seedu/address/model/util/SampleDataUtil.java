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
                    getTagSet("family")),
            new Guest(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    getTagSet("classmates")),
            new Guest(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    getTagSet("colleagues"))
        };
    }

    /**
     * Returns a room list initialized with the maximum number of rooms as set in RoomNumber class
     */
    public static List<Room> getSampleRooms() {
        return new UniqueRoomList(RoomNumber.MAX_ROOM_NUMBER).asUnmodifiableObservableList();
    }

    /**
     * Returns a room list initialized with the maximum number of rooms, and 1 sample booking for testing
     * Use to see if xml file reflects changes
     * DELETE WHEN TESTED IN UNIT TESTS
     */
    public static List<Room> getSampleRoomsWithBookingsExpenses() {
        UniqueRoomList uniqueRoomList = new UniqueRoomList(RoomNumber.MAX_ROOM_NUMBER);
        uniqueRoomList.getRoom(new RoomNumber("001"))
            .addBooking(
                new Booking(getSampleGuests()[0],
                new BookingPeriod(
                    LocalDate.now().format(BookingPeriod.DATE_TO_STRING_FORMAT),
                    LocalDate.now().plusDays(1).format(BookingPeriod.DATE_TO_STRING_FORMAT))));
        uniqueRoomList.getRoom(new RoomNumber("001")).addExpense(new Expense(getSampleExpenseTypes()[0]));
        return uniqueRoomList.asUnmodifiableObservableList();
    }

    public static ExpenseType[] getSampleExpenseTypes() {
        return new ExpenseType[] {
            new ExpenseType("RS01", "Room service: Red wine", 50),
            new ExpenseType("RS02", "Room service: Beef steak", 70),
            new ExpenseType("RS03", "Room service: Thai massage", 100),
            new ExpenseType("SP01", "Swimming pool: Entry", 5),
            new ExpenseType("MB01", "Minibar: Coca cola", 3),
            new ExpenseType("MB02", "Minibar: Sprite", 3),
            new ExpenseType("MB03", "Minibar: Tiger beer", 6),
            new ExpenseType("MB04", "Minibar: Mineral water", 3),
        };
    }

    public static Map<String, ExpenseType> getSampleMenuMap() {
        HashMap<String, ExpenseType> sampleMenuMap = new HashMap<>();
        for (ExpenseType expenseType : getSampleExpenseTypes()) {
            sampleMenuMap.put(expenseType.getItemNumber(), expenseType);
        }
        return sampleMenuMap;
    }

    public static ReadOnlyConcierge getSampleConcierge() {
        Concierge sampleAb = new Concierge();
        for (Guest sampleGuest : getSampleGuests()) {
            sampleAb.addGuest(sampleGuest);
        }
        sampleAb.setRooms(getSampleRooms());
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
