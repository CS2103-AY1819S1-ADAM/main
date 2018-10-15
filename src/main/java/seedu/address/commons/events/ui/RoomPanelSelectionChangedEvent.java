package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.room.Room;

/**
 * Represents a selection change in the room List Panel
 */
public class RoomPanelSelectionChangedEvent extends BaseEvent {

    private final Room newSelection;

    public RoomPanelSelectionChangedEvent(Room newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Room getNewSelection() {
        return newSelection;
    }
}
