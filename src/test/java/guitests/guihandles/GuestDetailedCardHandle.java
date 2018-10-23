package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.Guest;

/**
 * Provides a handle to a guest card in the guest list panel.
 */
public class GuestDetailedCardHandle extends NodeHandle<Node> {
    private static final String NAME_FIELD_ID = "#name";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label nameLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final Label addressLabel;
    private final List<Label> tagLabels;

    public GuestDetailedCardHandle(Node cardNode) {
        super(cardNode);

        nameLabel = getChildNode(NAME_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);
        addressLabel = getChildNode(ADDRESS_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code guest}.
     */
    public boolean equals(Guest guest) {
        boolean name = getName().equals(guest.getName().fullName);
        boolean phone = getPhone().equals(guest.getPhone().value);
        boolean email = getEmail().equals(guest.getEmail().value);
        boolean address = getAddress().equals(guest.getAddress().value);

        return getName().equals(guest.getName().fullName)
                && getPhone().equals(guest.getPhone().value)
                && getEmail().equals(guest.getEmail().value)
                && getAddress().equals(guest.getAddress().value)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(guest.getTags().stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList())));
    }
}
