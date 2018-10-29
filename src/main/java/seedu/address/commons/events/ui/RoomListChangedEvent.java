package seedu.address.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.room.Room;

/**
 * Represents a room list change in the left UI column
 */
public class RoomListChangedEvent extends BaseEvent {

    private ObservableList<Room> roomList;

    public RoomListChangedEvent(ObservableList<Room> roomList) {
        this.roomList = roomList;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public ObservableList<Room> getRoomList() {
        return roomList;
    }
}
