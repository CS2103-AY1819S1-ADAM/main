package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.guest.Email;
import seedu.address.model.guest.Guest;
import seedu.address.model.guest.Name;
import seedu.address.model.guest.Phone;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Guest.
 * Note: Tagged as XmlRootElement for XML tests to be able to write XML files for just this class
 */
@XmlRootElement(name = "guest")
public class XmlAdaptedGuest {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Guest's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedGuest.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedGuest() {}

    /**
     * Constructs an {@code XmlAdaptedGuest} with the given guest details.
     */
    public XmlAdaptedGuest(String name, String phone, String email, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Guest into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedGuest
     */
    public XmlAdaptedGuest(Guest source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted guest object into the model's Guest object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted guest
     */
    public Guest toModelType() throws IllegalValueException {
        final List<Tag> guestTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            guestTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        final Set<Tag> modelTags = new HashSet<>(guestTags);
        return new Guest(modelName, modelPhone, modelEmail, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedGuest)) {
            return false;
        }

        XmlAdaptedGuest otherGuest = (XmlAdaptedGuest) other;
        return Objects.equals(name, otherGuest.name)
                && Objects.equals(phone, otherGuest.phone)
                && Objects.equals(email, otherGuest.email)
                && tagged.equals(otherGuest.tagged);
    }
}
