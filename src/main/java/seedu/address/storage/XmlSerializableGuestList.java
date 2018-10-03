package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.GuestList;
import seedu.address.model.ReadOnlyGuestList;
import seedu.address.model.person.Guest;

/**
 * An Immutable GuestList that is serializable to XML format
 */
@XmlRootElement(name = "guestlist")
public class XmlSerializableGuestList {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate guest(s).";

    @XmlElement
    private List<XmlAdaptedPerson> guests;

    /**
     * Creates an empty XmlSerializableGuestList.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableGuestList() {
        guests = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableGuestList(ReadOnlyGuestList src) {
        this();
        guests.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code GuestList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson}.
     */
    public GuestList toModelType() throws IllegalValueException {
        GuestList guestList = new GuestList();
        for (XmlAdaptedPerson p : guests) {
            Guest guest = p.toModelType();
            if (guestList.hasPerson(guest)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            guestList.addPerson(guest);
        }
        return guestList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableGuestList)) {
            return false;
        }
        return guests.equals(((XmlSerializableGuestList) other).guests);
    }
}
