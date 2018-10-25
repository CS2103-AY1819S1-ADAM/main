package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Menu;
import seedu.address.model.expenses.Expense;
import seedu.address.model.expenses.Expenses;
import seedu.address.model.room.Capacity;
import seedu.address.model.room.DoubleRoom;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.SingleRoom;
import seedu.address.model.room.SuiteRoom;
import seedu.address.model.room.booking.Booking;
import seedu.address.model.room.booking.Bookings;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Room.
 */
public class XmlAdaptedRoom {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Room's %s field is missing!";
    public static final String MESSAGE_OVERLAPPING_BOOKING = "Room contains overlapping bookings!";
    public static final String MESSAGE_NO_SUCH_ROOM_WITH_CAPACITY = "No such rooms with the capacity %d exists.";

    @XmlElement(required = true)
    private String roomNumber;
    @XmlElement(required = true)
    private Integer capacity;

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
     * Converts a given room into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedRoom
     */
    public XmlAdaptedRoom(Room source) {
        roomNumber = source.getRoomNumber().toString();
        capacity = source.getCapacity().getValue();
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
        if (!Capacity.isValidCapacity(capacity)) {
            throw new IllegalValueException(Capacity.MESSAGE_CAPACITY_CONSTRAINTS);
        }
        final Capacity modelCapacity = new Capacity(capacity);

        final SortedSet<Booking> bookingsSet = new TreeSet<>();
        for (XmlAdaptedBooking b : bookings) {
            bookingsSet.add(b.toModelType());
        }
        final Bookings modelBookings = new Bookings(bookingsSet);

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

        if (modelCapacity.equals(SingleRoom.CAPACITY_SINGLE_ROOM)) {
            return new SingleRoom(modelRoomNumber, modelExpenses, modelBookings, modelTags);
        } else if (modelCapacity.equals(DoubleRoom.CAPACITY_DOUBLE_ROOM)) {
            return new DoubleRoom(modelRoomNumber, modelExpenses, modelBookings, modelTags);
        } else if (modelCapacity.equals(SuiteRoom.CAPACITY_SUITE_ROOM)) {
            return new SuiteRoom(modelRoomNumber, modelExpenses, modelBookings, modelTags);
        } else {
            throw new IllegalValueException(String.format(MESSAGE_NO_SUCH_ROOM_WITH_CAPACITY, capacity));
        }
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
