package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyConcierge;

/** Indicates the Concierge in the model has changed*/
public class ConciergeChangedEvent extends BaseEvent {

    public final ReadOnlyConcierge data;

    public ConciergeChangedEvent(ReadOnlyConcierge data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size();
    }
}
