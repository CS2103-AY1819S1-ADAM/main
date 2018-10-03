package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Guest;

/**
 * Represents a selection change in the Guest List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final Guest newSelection;

    public PersonPanelSelectionChangedEvent(Guest newSelection) {
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
