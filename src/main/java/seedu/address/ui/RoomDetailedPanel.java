package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.RoomListChangedEvent;
import seedu.address.commons.events.ui.RoomPanelSelectionChangedEvent;
import seedu.address.model.room.Room;


/**
 * Panel containing the list of one room.
 */
public class RoomDetailedPanel extends UiPart<Region> {
    private static final String FXML = "RoomDetailedPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RoomDetailedPanel.class);
    private ObservableList<Room> displayedRoomList = FXCollections.observableArrayList();

    @FXML
    private ListView<Room> roomDetailedView;

    public RoomDetailedPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
        // Ignore events caused by typing inside the details pane.
        getRoot().setOnKeyPressed(Event::consume);
    }

    /**
     * Sets the details of a {@code Room} by adding into the {@code ListView<Room>} list to be
     * displayed via UI.
     */
    private void setRoomDetails(Room room) {
        displayedRoomList.clear();
        displayedRoomList.add(room);
        roomDetailedView.setItems(displayedRoomList);
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

    @Subscribe
    private void handleRoomListChangedEvent(RoomListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (displayedRoomList.isEmpty()) {
            return;
        }
        Room displayedRoom = displayedRoomList.get(0);
        ObservableList<Room> changedRoomList = event.getRoomList();
        for (Room room : changedRoomList) {
            if (room.isSameRoom(displayedRoom)) {
                setRoomDetails(room);
            }
        }
    }

    /**
     * Clears the items from this list view
     */
    public void clearSelection() {
        roomDetailedView.getItems().clear();
    }

}
