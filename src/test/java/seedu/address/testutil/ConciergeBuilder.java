package seedu.address.testutil;

import seedu.address.model.Concierge;
import seedu.address.model.person.Guest;

/**
 * A utility class to help with building Concierge objects.
 * Example usage: <br>
 *     {@code Concierge ab = new ConciergeBuilder().withPerson("John", "Doe").build();}
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
    public ConciergeBuilder withPerson(Guest guest) {
        concierge.addPerson(guest);
        return this;
    }

    public Concierge build() {
        return concierge;
    }
}
