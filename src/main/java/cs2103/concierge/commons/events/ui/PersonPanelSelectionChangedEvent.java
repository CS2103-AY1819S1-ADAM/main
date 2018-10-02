package cs2103.concierge.commons.events.ui;

import cs2103.concierge.commons.events.BaseEvent;
import cs2103.concierge.model.person.Person;

/**
 * Represents a selection change in the Person List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final Person newSelection;

    public PersonPanelSelectionChangedEvent(Person newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Person getNewSelection() {
        return newSelection;
    }
}
