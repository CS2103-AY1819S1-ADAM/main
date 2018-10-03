package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyGuestList;

/** Indicates the GuestList in the model has changed*/
public class GuestListChangedEvent extends BaseEvent {

    public final ReadOnlyGuestList data;

    public GuestListChangedEvent(ReadOnlyGuestList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size();
    }
}
