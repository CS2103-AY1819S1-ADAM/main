package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.room.Capacity;
import seedu.address.model.room.DoubleRoom;
import seedu.address.model.room.Expenses;
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

    @XmlElement(required = true)
    private String expenses;
    @XmlElement(required = true)
    private List<XmlAdaptedBooking> bookings = new ArrayList<>();
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
        expenses = source.getExpenses().toString();
        bookings.addAll(source.getBookings().asUnmodifiableSortedList().stream().map(XmlAdaptedBooking::new).collect
            (Collectors.toList()));
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted room object into the model's room object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted room
     */
    public Room toModelType() throws IllegalValueException {
        final List<Tag> roomTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            roomTags.add(tag.toModelType());
        }

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

        final Bookings modelBookings = new Bookings();
        for (XmlAdaptedBooking b : bookings) {
            Booking booking = b.toModelType();
            if (modelBookings.canAcceptBooking(booking)) {
                throw new IllegalValueException(MESSAGE_OVERLAPPING_BOOKING);
            }
            modelBookings.add(booking);
        }

        final Expenses modelExpenses = new Expenses();

        final Set<Tag> modelTags = new HashSet<>(roomTags);

        switch(capacity) {
        case 1:
            SingleRoom singleRoom = new SingleRoom(modelRoomNumber);
            singleRoom.resetBookings(modelBookings);
            singleRoom.resetExpenses(modelExpenses);
            return singleRoom;

        case 2:
            DoubleRoom doubleRoom = new DoubleRoom(modelRoomNumber);
            doubleRoom.resetBookings(modelBookings);
            doubleRoom.resetExpenses(modelExpenses);
            return doubleRoom;

        case 5:
            SuiteRoom suiteRoom = new SuiteRoom(modelRoomNumber);
            suiteRoom.resetBookings(modelBookings);
            suiteRoom.resetExpenses(modelExpenses);
            return suiteRoom;

        default:
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
