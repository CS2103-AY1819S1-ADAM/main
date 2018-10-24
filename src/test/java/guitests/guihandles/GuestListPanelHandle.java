package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.guest.Guest;

/**
 * Provides a handle for {@code GuestListPanel} containing the list of {@code GuestCard}.
 */
public class GuestListPanelHandle extends NodeHandle<ListView<Guest>> {
    public static final String GUEST_LIST_VIEW_ID = "#guestListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Guest> lastRememberedSelectedGuestCard;

    public GuestListPanelHandle(ListView<Guest> guestListPanelNode) {
        super(guestListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code GuestCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public GuestCardHandle getHandleToSelectedCard() {
        List<Guest> selectedGuestList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedGuestList.size() != 1) {
            throw new AssertionError("Guest list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(GuestCardHandle::new)
                .filter(handle -> handle.equals(selectedGuestList.get(0)))
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
        List<Guest> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code guest}.
     */
    public void navigateToCard(Guest guest) {
        if (!getRootNode().getItems().contains(guest)) {
            throw new IllegalArgumentException("Guest does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(guest);
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
     * Selects the {@code GuestCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the guest card handle of a guest associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public GuestCardHandle getGuestCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(GuestCardHandle::new)
                .filter(handle -> handle.equals(getGuest(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Guest getGuest(int index) {
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
     * Remembers the selected {@code GuestCard} in the list.
     */
    public void rememberSelectedGuestCard() {
        List<Guest> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedGuestCard = Optional.empty();
        } else {
            lastRememberedSelectedGuestCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code GuestCard} is different from the value remembered by the most recent
     * {@code rememberSelectedGuestCard()} call.
     */
    public boolean isSelectedGuestCardChanged() {
        List<Guest> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedGuestCard.isPresent();
        } else {
            return !lastRememberedSelectedGuestCard.isPresent()
                    || !lastRememberedSelectedGuestCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
