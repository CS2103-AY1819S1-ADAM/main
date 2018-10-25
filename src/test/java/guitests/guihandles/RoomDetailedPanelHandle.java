package guitests.guihandles;

import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.room.Room;

/**
 * Provides a handle for {@code RoomListPanel} containing the list of {@code RoomCard}.
 */
public class RoomDetailedPanelHandle extends NodeHandle<ListView<Room>> {
    public static final String ROOM_LIST_VIEW_ID = "#roomDetailedView";

    private static final String CARD_PANE_ID = "#cardPane";

    public RoomDetailedPanelHandle(ListView<Room> roomListPanelNode) {
        super(roomListPanelNode);
    }

    /**
     * Navigates the listview to display {@code room}.
     */
    public void navigateToCard(Room room) {
        if (!getRootNode().getItems().contains(room)) {
            throw new IllegalArgumentException("Room does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(room);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Returns the room card handle of a room associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public RoomDetailedCardHandle getRoomDetailedCardHandle(int index) {
        Set<Node> allNodes = getAllCardNodes();

        return getAllCardNodes().stream()
                .map(RoomDetailedCardHandle::new)
                .filter(handle -> handle.equals(getRoom(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Room getRoom(int index) {
        return getRootNode().getItems().get(index);
    }

}
