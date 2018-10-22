package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Guest;

/**
 * Panel containing the list of persons.
 */
public class GuestListPanel extends UiPart<Region> {
    private static final String FXML = "GuestListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(GuestListPanel.class);

    @FXML
    private ListView<Guest> guestListView;

    public GuestListPanel(ObservableList<Guest> guestList) {
        super(FXML);
        setConnections(guestList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Guest> guestList) {
        guestListView.setItems(guestList);
        guestListView.setCellFactory(listView -> new GuestListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        guestListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in guest list panel changed to : '" + newValue + "'");
                        raise(new PersonPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code GuestCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            guestListView.scrollTo(index);
            guestListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Guest} using a {@code GuestCard}.
     */
    class GuestListViewCell extends ListCell<Guest> {
        @Override
        protected void updateItem(Guest guest, boolean empty) {
            super.updateItem(guest, empty);

            if (empty || guest == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new GuestCard(guest, getIndex() + 1).getRoot());
            }
        }
    }

}
