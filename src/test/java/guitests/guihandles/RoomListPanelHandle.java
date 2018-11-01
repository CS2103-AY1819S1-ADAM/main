package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.room.Room;

/**
 * Provides a handle for {@code RoomListPanel} containing the list of {@code RoomCard}.
 */
public class RoomListPanelHandle extends NodeHandle<ListView<Room>> {
    public static final String ROOM_LIST_VIEW_ID = "#roomListView";

    private static final String CARD_PANE_ID = "#roomCardPane";

    private Optional<Room> lastRememberedSelectedRoomCard;

    public RoomListPanelHandle(ListView<Room> roomListPanelNode) {
        super(roomListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code RoomCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public RoomCardHandle getHandleToSelectedCard() {
        List<Room> selectedRoomList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedRoomList.size() != 1) {
            throw new AssertionError("Room list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(RoomCardHandle::new)
                .filter(handle -> handle.equals(selectedRoomList.get(0)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<Room> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code guest}.
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
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code RoomCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the room card handle of a room associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public RoomCardHandle getRoomCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(RoomCardHandle::new)
                .filter(handle -> handle.equals(getRoom(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Room getRoom(int index) {
        return getRootNode().getItems().get(index);
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
     * Remembers the selected {@code RoomCard} in the list.
     */
    public void rememberSelectedRoomCard() {
        List<Room> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedRoomCard = Optional.empty();
        } else {
            lastRememberedSelectedRoomCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code GuestCard} is different from the value remembered by the most recent
     * {@code rememberSelectedGuestCard()} call.
     */
    public boolean isSelectedRoomCardChanged() {
        List<Room> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedRoomCard.isPresent();
        } else {
            return !lastRememberedSelectedRoomCard.isPresent()
                    || !lastRememberedSelectedRoomCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
