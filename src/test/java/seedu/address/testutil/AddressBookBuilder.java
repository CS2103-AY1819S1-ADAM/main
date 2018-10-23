package seedu.address.testutil;

import seedu.address.model.Concierge;
import seedu.address.model.person.Guest;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code Concierge ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private Concierge addressBook;

    public AddressBookBuilder() {
        addressBook = new Concierge();
    }

    public AddressBookBuilder(Concierge addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Guest} to the {@code Concierge} that we are building.
     */
    public AddressBookBuilder withPerson(Guest guest) {
        addressBook.addPerson(guest);
        return this;
    }

    public Concierge build() {
        return addressBook;
    }
}
