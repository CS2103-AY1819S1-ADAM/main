package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.guest.Guest;

/**
 * Represents a selection change in the Guest List Panel
 */
public class GuestPanelSelectionChangedEvent extends BaseEvent {

    private final Guest newSelection;

    public GuestPanelSelectionChangedEvent(Guest newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Guest getNewSelection() {
        return newSelection;
    }
}
