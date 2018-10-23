package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Concierge;
import seedu.address.model.ReadOnlyConcierge;
import seedu.address.model.expenses.ExpenseType;
import seedu.address.model.person.Guest;
import seedu.address.model.room.Room;

/**
 * An Immutable Concierge that is serializable to XML format
 */
@XmlRootElement(name = "concierge")
public class XmlSerializableConcierge {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate guest(s).";
    public static final String MESSAGE_DUPLICATE_ROOM = "Room list contains duplicate room(s)";

    @XmlElement
    private List<XmlAdaptedPerson> guests;

    @XmlElement
    private List<XmlAdaptedRoom> rooms;

    @XmlElement
    private HashMap<String, XmlAdaptedExpenseType> menu;

    /**
     * Creates an empty XmlSerializableConcierge.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableConcierge() {
        guests = new ArrayList<>();
        rooms = new ArrayList<>();
        menu = new HashMap<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableConcierge(ReadOnlyConcierge src) {
        this();
        guests.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        rooms.addAll(src.getRoomList().stream().map(XmlAdaptedRoom::new).collect(Collectors.toList()));
        for (Map.Entry<String, ExpenseType> mapping : src.getMenuMap().entrySet()) {
            menu.put(mapping.getKey(), new XmlAdaptedExpenseType(mapping.getValue()));
        }
    }

    /**
     * Converts this concierge into the model's {@code Concierge} object.
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson / XmlAdaptedRoom}
     */
    public Concierge toModelType() throws IllegalValueException {
        Concierge concierge = new Concierge();
        for (XmlAdaptedPerson p : guests) {
            Guest guest = p.toModelType();
            if (concierge.hasPerson(guest)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            concierge.addPerson(guest);
        }

        HashMap<String, ExpenseType> newMenu = new HashMap<>();
        for (Map.Entry<String, XmlAdaptedExpenseType> mapping : menu.entrySet()) {
            newMenu.put(mapping.getKey(), mapping.getValue().toModelType());
        }
        concierge.setMenu(newMenu);

        for (XmlAdaptedRoom r : rooms) {
            Room room = r.toModelType(concierge.getMenu());
            if (concierge.hasRoom(room)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ROOM);
            }
            concierge.addRoom(room);
        }

        return concierge;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableConcierge)) {
            return false;
        }
        return guests.equals(((XmlSerializableConcierge) other).guests);
    }
}
