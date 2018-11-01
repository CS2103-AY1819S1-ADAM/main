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
import seedu.address.model.Menu;
import seedu.address.model.expenses.Expense;
import seedu.address.model.expenses.Expenses;
import seedu.address.model.room.Capacity;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.Bookings;
import seedu.address.model.room.booking.exceptions.OverlappingBookingException;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Room.
 * Note: Tagged as XmlRootElement for XML tests to be able to write XML files for just this class
 */
@XmlRootElement(name = "room")
public class XmlAdaptedRoom {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Room's %s field is missing!";
    public static final String MESSAGE_OVERLAPPING_BOOKING = "Room contains overlapping bookings!";

    @XmlElement(required = true)
    private String roomNumber;
    @XmlElement(required = true)
    private Capacity capacity;

    @XmlElement
    private List<XmlAdaptedBooking> bookings = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedExpense> expenses = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedRoom.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRoom() {}

    /**
     * Constructor FOR TESTING in XmlAdaptedRoomTest
     */
    public XmlAdaptedRoom(String roomNumber, Capacity capacity, List<XmlAdaptedBooking> bookings,
                          List<XmlAdaptedExpense> expenses, List<XmlAdaptedTag> tagged) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.bookings = bookings;
        this.expenses = expenses;
        this.tagged = tagged;
    }

    /**
     * Converts a given room into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedRoom
     */
    public XmlAdaptedRoom(Room source) {
        roomNumber = source.getRoomNumber().toString();
        capacity = source.getCapacity();
        expenses.addAll(source.getExpenses().getExpensesList().stream().map(XmlAdaptedExpense::new)
                .collect(Collectors.toList()));
        bookings.addAll(source.getBookings().getSortedBookingsSet().stream().map(XmlAdaptedBooking::new)
                .collect(Collectors.toList()));
        tagged = source.getTags().stream().map(XmlAdaptedTag::new).collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted room object into the model's room object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted room
     */
    public Room toModelType(Menu menu) throws IllegalValueException {

        if (roomNumber == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, RoomNumber.class.getSimpleName()));
        }
        if (!RoomNumber.isValidRoomNumber(roomNumber)) {
            throw new IllegalValueException(RoomNumber.MESSAGE_ROOM_NUMBER_CONSTRAINTS);
        }
        final RoomNumber modelRoomNumber = new RoomNumber(roomNumber);

        if (capacity == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Capacity.class.getSimpleName()));
        }
        final Capacity modelCapacity = capacity;

        Bookings modelBookings = new Bookings();
        try {
            for (XmlAdaptedBooking b : bookings) {
                modelBookings = modelBookings.add(b.toModelType());
            }
        } catch (OverlappingBookingException e) {
            throw new IllegalValueException(MESSAGE_OVERLAPPING_BOOKING);
        }

        final List<Expense> expenseList = new ArrayList<>();
        for (XmlAdaptedExpense expense : expenses) {
            expenseList.add(expense.toModelType(menu));
        }
        final Expenses modelExpenses = new Expenses(expenseList);

        final List<Tag> roomTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            roomTags.add(tag.toModelType());
        }
        final Set<Tag> modelTags = new HashSet<>(roomTags);

        return new Room(modelRoomNumber, modelCapacity, modelExpenses, modelBookings, modelTags);
    }

    @Override
    public String toString() {
        return roomNumber + capacity + expenses + bookings + tagged;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedRoom)) {
            return false;
        }

        XmlAdaptedRoom otherRoom = (XmlAdaptedRoom) other;
        return Objects.equals(roomNumber, otherRoom.roomNumber)
                && Objects.equals(capacity, otherRoom.capacity)
                && Objects.equals(expenses, otherRoom.expenses)
                && Objects.equals(bookings, otherRoom.bookings)
                && tagged.equals(otherRoom.tagged);
    }
}
