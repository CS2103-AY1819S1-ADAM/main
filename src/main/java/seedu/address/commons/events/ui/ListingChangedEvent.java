package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a listing change in the left UI column
 */
public class ListingChangedEvent extends BaseEvent {

    private String flag;

    public ListingChangedEvent(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public String getFlag() {
        return flag;
    }
}
