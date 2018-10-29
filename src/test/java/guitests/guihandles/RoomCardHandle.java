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
public class RoomCardHandle extends NodeHandle<Node> {
    private static final String ROOMNUMBER_FIELD_ID = "#roomNumber";
    private static final String CAPACITY_FIELD_ID = "#capacity";
    private static final String ACTIVE_BOOKING_FIELD_ID = "#activeBooking";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label roomNumberLabel;
    private final Label capacityLabel;
    private final List<Label> activeBookingLabels;
    private final List<Label> tagLabels;

    public RoomCardHandle(Node cardNode) {
        super(cardNode);

        roomNumberLabel = getChildNode(ROOMNUMBER_FIELD_ID);
        capacityLabel = getChildNode(CAPACITY_FIELD_ID);

        Region bookingsContainer = getChildNode(ACTIVE_BOOKING_FIELD_ID);
        activeBookingLabels = bookingsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());

        activeBookingLabels.remove(0);

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

    public List<String> getBookings() {
        return activeBookingLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    public List<String> getActiveBooking() {
        return activeBookingLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
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
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(room.getTags().stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList())));
    }
}
