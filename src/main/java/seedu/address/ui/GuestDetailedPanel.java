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
import seedu.address.commons.events.ui.GuestPanelSelectionChangedEvent;
import seedu.address.model.guest.Guest;

/**
 * Panel containing the list of one guest.
 */
public class GuestDetailedPanel extends UiPart<Region> {
    private static final String FXML = "GuestDetailedPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(GuestDetailedPanel.class);

    @FXML
    private ListView<Guest> guestDetailedView;

    public GuestDetailedPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    /**
     * Sets the details of a {@code Guest} by adding into the {@code ListView<Guest>} list to be
     * displayed via UI.
     */
    public void setGuestDetails(Guest guest) {
        ObservableList<Guest> guestList = FXCollections.observableArrayList();
        guestList.add(guest);
        guestDetailedView.setItems(guestList);
        guestDetailedView.setCellFactory(listView -> new GuestListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Guest} using a {@code GuestDetailedCard}.
     */
    class GuestListViewCell extends ListCell<Guest> {
        @Override
        protected void updateItem(Guest guest, boolean empty) {
            super.updateItem(guest, empty);

            if (empty || guest == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new GuestDetailedCard(guest).getRoot());
            }
        }
    }

    /**
     * Event handler when a guest is selected on the left panel to display detailed information on
     * the right panel.
     */
    @Subscribe
    private void handleGuestPanelSelectionChangedEvent(GuestPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setGuestDetails(event.getNewSelection());
    }

}
