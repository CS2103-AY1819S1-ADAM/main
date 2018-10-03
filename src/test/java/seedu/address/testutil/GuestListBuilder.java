package seedu.address.testutil;

import seedu.address.model.GuestList;
import seedu.address.model.guest.Guest;

/**
 * A utility class to help with building GuestList objects.
 * Example usage: <br>
 *     {@code GuestList ab = new GuestListBuilder().withGuest("John", "Doe").build();}
 */
public class GuestListBuilder {

    private GuestList guestList;

    public GuestListBuilder() {
        guestList = new GuestList();
    }

    public GuestListBuilder(GuestList guestList) {
        this.guestList = guestList;
    }

    /**
     * Adds a new {@code Guest} to the {@code GuestList} that we are building.
     */
    public GuestListBuilder withGuest(Guest guest) {
        guestList.addPerson(guest);
        return this;
    }

    public GuestList build() {
        return guestList;
    }
}
