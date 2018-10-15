package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.room.Room;

/**
 * An UI component that displays information of a {@code Room}.
 */
public class RoomCard extends UiPart<Region> {

    private static final String FXML = "RoomListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Room room;

    @FXML
    private HBox cardPane;
    @FXML
    private Label roomNumber;
    @FXML
    private Label capacity;
    @FXML
    private Label expenses;
    @FXML
    private Label bookings;
    @FXML
    private FlowPane occupant;
    @FXML
    private FlowPane tags;

    public RoomCard(Room room) {
        super(FXML);
        this.room = room;
        roomNumber.setText("Room: " + room.getRoomNumber().toString());
        capacity.setText("Capacity: " + room.getCapacity().toString());
        expenses.setText("Expenses: " + room.getExpenses().toString());
        bookings.setText("Bookings: " + room.getBookings().toString());
        room.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RoomCard)) {
            return false;
        }

        // state check
        RoomCard card = (RoomCard) other;
        return roomNumber.getText().equals(card.roomNumber.getText())
                && room.equals(card.room);
    }
}
