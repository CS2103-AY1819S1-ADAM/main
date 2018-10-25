package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.guest.Guest;

/**
 * An UI component that displays information of a {@code Guest}.
 */
public class GuestDetailedCard extends UiPart<Region> {

    private static final String FXML = "GuestDetailedCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Guest guest;

    @FXML
    private HBox cardPane;
    @FXML
    private Label header;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    public GuestDetailedCard(Guest guest) {
        super(FXML);
        this.guest = guest;
        header.setText("Guest Details:");
        name.setText(guest.getName().fullName);
        phone.setText(guest.getPhone().value);
        email.setText(guest.getEmail().value);
        guest.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GuestDetailedCard)) {
            return false;
        }

        // state check
        GuestDetailedCard card = (GuestDetailedCard) other;
        return guest.equals(card.guest);
    }
}
