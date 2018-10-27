package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.ConciergeChangedEvent;
import seedu.address.commons.events.ui.RoomPanelSelectionChangedEvent;
import seedu.address.model.Concierge;
import seedu.address.model.ReadOnlyConcierge;
import seedu.address.model.room.Room;

/**
 * Panel containing the list of one room.
 */
public class RoomDetailedPanel extends UiPart<Region> {
    private static final String FXML = "RoomDetailedPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RoomDetailedPanel.class);

    @FXML
    private ListView<Room> roomDetailedView;

    public RoomDetailedPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    /**
     * Sets the details of a {@code Room} by adding into the {@code ListView<Room>} list to be
     * displayed via UI.
     */
    private void setRoomDetails(Room room) {
        ObservableList<Room> roomList = FXCollections.observableArrayList();
        roomList.add(room);
        roomDetailedView.setItems(roomList);
        roomDetailedView.setCellFactory(listView -> new RoomListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code room} using a {@code RoomDetailedCard}.
     */
    class RoomListViewCell extends ListCell<Room> {
        @Override
        protected void updateItem(Room room, boolean empty) {
            super.updateItem(room, empty);

            if (empty || room == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RoomDetailedCard(room).getRoot());
            }
        }
    }

    /**
     * Event handler when a room is selected on the left panel to display detailed information on
     * the right panel.
     */
    @Subscribe
    private void handleRoomPanelSelectionChangedEvent(RoomPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setRoomDetails(event.getNewSelection());
    }

    /**
     * Event handler to update room details on the right panel whenever Concierge changes
     */
    @Subscribe
    private void handleConciergeChangedEvent(ConciergeChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        ObservableList<Room> displayedRoomList = roomDetailedView.getItems();
        if (displayedRoomList.isEmpty()) {
            return;
        }
        Room displayedRoom = displayedRoomList.get(0);
        ReadOnlyConcierge changedConcierge = event.data;
        ObservableList<Room> changedRoomList = changedConcierge.getRoomList();
        changedRoomList.stream().filter(room -> room.isSameRoom(displayedRoom)).findFirst()
                .ifPresent(this::setRoomDetails);
    }

}
