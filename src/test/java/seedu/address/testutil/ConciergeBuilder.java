package seedu.address.testutil;

import java.util.List;
import java.util.Map;

import seedu.address.model.Concierge;
import seedu.address.model.expenses.ExpenseType;
import seedu.address.model.guest.Guest;
import seedu.address.model.room.Room;

/**
 * A utility class to help with building Concierge objects.
 * Example usage: <br>
 *     {@code Concierge concierge = new ConciergeBuilder().withGuest("John", "Doe").build();}
 */
public class ConciergeBuilder {

    private Concierge concierge;

    public ConciergeBuilder() {
        concierge = new Concierge();
    }

    public ConciergeBuilder(Concierge concierge) {
        this.concierge = concierge;
    }

    /**
     * Adds a new {@code Guest} to the {@code Concierge} that we are building.
     */
    public ConciergeBuilder withGuest(Guest guest) {
        concierge.addGuest(guest);
        return this;
    }

    /**
     * Sets the {@code List<Room>} to the room list of the {@code Concierge} that we are building.
     */
    public ConciergeBuilder withRooms(List<Room> roomList) {
        concierge.setRooms(roomList);
        return this;
    }

    /**
     * Sets the {@code Map<String, ExpenseType>} to the menu of the {@code Concierge} that we are building.
     */
    public ConciergeBuilder withMenu(Map<String, ExpenseType> menu) {
        concierge.setMenu(menu);
        return this;
    }

    public Concierge build() {
        return concierge;
    }
}
