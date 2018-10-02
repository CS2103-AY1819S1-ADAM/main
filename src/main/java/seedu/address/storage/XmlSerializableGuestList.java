package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.GuestList;
import seedu.address.model.ReadOnlyGuestList;
import seedu.address.model.person.Person;

/**
 * An Immutable GuestList that is serializable to XML format
 */
@XmlRootElement(name = "guestlist")
public class XmlSerializableGuestList {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    @XmlElement
    private List<XmlAdaptedPerson> persons;

    /**
     * Creates an empty XmlSerializableGuestList.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableGuestList() {
        persons = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableGuestList(ReadOnlyGuestList src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code GuestList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson}.
     */
    public GuestList toModelType() throws IllegalValueException {
        GuestList guestList = new GuestList();
        for (XmlAdaptedPerson p : persons) {
            Person person = p.toModelType();
            if (guestList.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            guestList.addPerson(person);
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
        return persons.equals(((XmlSerializableGuestList) other).persons);
    }
}
