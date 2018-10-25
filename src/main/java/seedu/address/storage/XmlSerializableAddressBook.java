package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.expenses.ExpenseType;
import seedu.address.model.guest.Guest;
import seedu.address.model.room.Room;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_GUEST = "Guest list contains duplicate guest(s).";
    public static final String MESSAGE_DUPLICATE_ROOM = "Room list contains duplicate room(s)";

    @XmlElement
    private List<XmlAdaptedGuest> guests;

    @XmlElement
    private List<XmlAdaptedRoom> rooms;

    @XmlElement
    private HashMap<String, XmlAdaptedExpenseType> menu;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        guests = new ArrayList<>();
        rooms = new ArrayList<>();
        menu = new HashMap<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        this();
        guests.addAll(src.getGuestList().stream().map(XmlAdaptedGuest::new).collect(Collectors.toList()));
        rooms.addAll(src.getRoomList().stream().map(XmlAdaptedRoom::new).collect(Collectors.toList()));
        for (Map.Entry<String, ExpenseType> mapping : src.getMenuMap().entrySet()) {
            menu.put(mapping.getKey(), new XmlAdaptedExpenseType(mapping.getValue()));
        }
    }

    /**
     * Converts this addressbook into the model's {@code AddressBook} object.
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedGuest / XmlAdaptedRoom}
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (XmlAdaptedGuest p : guests) {
            Guest guest = p.toModelType();
            if (addressBook.hasGuest(guest)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_GUEST);
            }
            addressBook.addGuest(guest);
        }

        HashMap<String, ExpenseType> newMenu = new HashMap<>();
        for (Map.Entry<String, XmlAdaptedExpenseType> mapping : menu.entrySet()) {
            newMenu.put(mapping.getKey(), mapping.getValue().toModelType());
        }
        addressBook.setMenu(newMenu);

        for (XmlAdaptedRoom r : rooms) {
            Room room = r.toModelType(addressBook.getMenu());
            if (addressBook.hasRoom(room)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ROOM);
            }
            addressBook.addRoom(room);
        }

        return addressBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableAddressBook)) {
            return false;
        }
        return guests.equals(((XmlSerializableAddressBook) other).guests);
    }
}
