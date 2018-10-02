package cs2103.concierge.commons.events.model;

import cs2103.concierge.commons.events.BaseEvent;
import cs2103.concierge.model.ReadOnlyAddressBook;

/** Indicates the AddressBook in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;

    public AddressBookChangedEvent(ReadOnlyAddressBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size();
    }
}
