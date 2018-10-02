package seedu.address.testutil;

import seedu.address.model.GuestList;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code GuestList ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private GuestList guestList;

    public AddressBookBuilder() {
        guestList = new GuestList();
    }

    public AddressBookBuilder(GuestList guestList) {
        this.guestList = guestList;
    }

    /**
     * Adds a new {@code Person} to the {@code GuestList} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        guestList.addPerson(person);
        return this;
    }

    public GuestList build() {
        return guestList;
    }
}
