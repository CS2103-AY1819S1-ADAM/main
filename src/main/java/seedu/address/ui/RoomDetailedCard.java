package seedu.address.ui;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.room.Room;
import seedu.address.model.room.booking.Booking;
import seedu.address.model.room.booking.Bookings;

/**
 * An UI component that displays information of a {@code Room}.
 */
public class RoomDetailedCard extends UiPart<Region> {

    private static final String FXML = "RoomDetailedCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Concierge level 4</a>
     */

    public final Room room;

    @FXML
    private HBox cardPane;
    @FXML
    private Label header;
    @FXML
    private Label roomNumber;
    @FXML
    private Label capacity;
    @FXML
    private Label expenses;
    @FXML
    private FlowPane bookings;
    @FXML
    private FlowPane otherBookings;
    @FXML
    private FlowPane tags;

    public RoomDetailedCard(Room room) {
        super(FXML);
        this.room = room;
        header.setText("Room Details:");
        roomNumber.setText("Room: " + room.getRoomNumber().toString());
        capacity.setText("Capacity: " + room.getCapacity().toString());
        expenses.setText("Expenses: " + room.getExpenses().toStringTotalCost());
        Optional<Booking> activeBooking = room.getActiveBooking();
        bookings.getChildren().add(new Label("Active booking:\n"
                + activeBooking.map(Booking::toString).orElse("")));
        Bookings allOtherBookings = room.getBookings();
        if (activeBooking.isPresent()) {
            allOtherBookings = allOtherBookings.remove(activeBooking.get());
        }
        otherBookings.getChildren().add(new Label("All other bookings: \n" + allOtherBookings));
        room.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RoomDetailedCard)) {
            return false;
        }

        // state check
        RoomDetailedCard card = (RoomDetailedCard) other;
        return roomNumber.getText().equals(card.roomNumber.getText())
                && room.equals(card.room);
    }
}
