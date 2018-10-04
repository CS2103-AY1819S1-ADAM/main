package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Guest;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.room.DoubleRoom;
import seedu.address.model.room.Expenses;
import seedu.address.model.room.Reservations;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.SingleRoom;
import seedu.address.model.room.SuiteRoom;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Guest[] getSamplePersons() {
        return new Guest[] {
            new Guest(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Guest(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Guest(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Guest(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Guest(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Guest(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static List<Room> getSampleRooms() {
        return Stream.iterate(1, i -> i <= Integer.parseInt(RoomNumber.MAX_ROOM_NUMBER), i -> i + 1)
            .map(i -> {
                RoomNumber roomNumber = new RoomNumber(String.format("%03d", i));;
                List<Guest> occupant = new ArrayList<>();
                Expenses expenses = new Expenses();
                Reservations reservations = new Reservations();
                if (i % 10 == 0) { // All rooms with room number that is multiple of 10 is a SuiteRoom.
                    return new SuiteRoom(roomNumber, occupant, expenses, reservations);
                }
                if (i % 2 == 0) { // All rooms with even room number is a DoubleRoom.
                    return new DoubleRoom(roomNumber, occupant, expenses, reservations);
                }
                // ALl rooms with odd room number is a SingleRoom.
                return new SingleRoom(roomNumber, occupant, expenses, reservations);
            })
            .collect(Collectors.toList());
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Guest sampleGuest : getSamplePersons()) {
            sampleAb.addPerson(sampleGuest);
        }
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
