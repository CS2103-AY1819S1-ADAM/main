package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.room.Room;

/**
 * Provides a handle to a room card in the room list panel.
 */
public class RoomDetailedCardHandle extends NodeHandle<Node> {
    private static final String ROOMNUMBER_FIELD_ID = "#roomNumber";
    private static final String CAPACITY_FIELD_ID = "#capacity";
    private static final String ACTIVE_BOOKING_FIELD_ID = "#activeBooking";
    private static final String ALL_OTHER_BOOKING_FIELD_ID = "#allOtherBookings";
    private static final String EXPENSES_FIELD_ID = "#expenses";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label roomNumberLabel;
    private final Label capacityLabel;
    private final Label expensesLabel;
    private final Label activeBookingLabel;
    private final Label allOtherBookingsLabel;
    private final List<Label> tagLabels;

    public RoomDetailedCardHandle(Node cardNode) {
        super(cardNode);

        roomNumberLabel = getChildNode(ROOMNUMBER_FIELD_ID);
        capacityLabel = getChildNode(CAPACITY_FIELD_ID);

        Region expensesContainer = getChildNode(EXPENSES_FIELD_ID);
        expensesLabel = expensesContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .findFirst()
                .get(); // guaranteed to have only one item

        Region activeBookingContainer = getChildNode(ACTIVE_BOOKING_FIELD_ID);
        activeBookingLabel = activeBookingContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .findFirst()
                .get(); // guaranteed to have only one item

        Region allOtherBookingsContainer = getChildNode(ALL_OTHER_BOOKING_FIELD_ID);
        allOtherBookingsLabel = allOtherBookingsContainer
            .getChildrenUnmodifiable()
            .stream()
            .map(Label.class::cast)
            .findFirst()
            .get(); // guaranteed to have only one item

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getRoomNumber() {
        return roomNumberLabel.getText();
    }

    public String getCapacity() {
        return capacityLabel.getText();
    }

    public String getExpenses() {
        return expensesLabel.getText();
    }

    public String getActiveBooking() {
        return activeBookingLabel.getText();
    }

    public String getAllOtherBookings() {
        return allOtherBookingsLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code Room}.
     */
    public boolean equals(Room room) {
        return getRoomNumber().equals("Room: " + room.getRoomNumber())
                && getCapacity().equals("Capacity: " + room.getCapacity())
                && getExpenses().equals("Expenses:\n" + room.getExpenses().toStringSummary())
                && getActiveBooking().equals("Active booking:\n" + room.getBookings().toStringActiveBooking())
                && getAllOtherBookings().equals("All other bookings:\n" + room.getBookings().toStringAllOtherBookings())
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(room.getTags().stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList())));
    }
}
