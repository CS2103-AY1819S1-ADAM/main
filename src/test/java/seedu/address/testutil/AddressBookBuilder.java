package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.guest.Guest;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withGuest("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Guest} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withGuest(Guest guest) {
        addressBook.addGuest(guest);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
