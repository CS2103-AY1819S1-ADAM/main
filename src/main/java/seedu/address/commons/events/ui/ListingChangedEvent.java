package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.logic.parser.Prefix;

/**
 * Represents a listing change in the left UI column
 */
public class ListingChangedEvent extends BaseEvent {

    private Prefix flag;

    public ListingChangedEvent(Prefix flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Prefix getFlag() {
        return flag;
    }
}
