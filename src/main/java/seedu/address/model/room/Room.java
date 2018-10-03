package seedu.address.model.room;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.UniqueGuestList;
import seedu.address.model.tag.Tag;

/**
 * Represents a room in the hotel.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Room {

    // Identity fields
    protected final RoomNumber roomNumber;

    // Data fields
    protected final Capacity capacity;
    protected final UniqueGuestList occupants;
    protected final Expenses expenses;
    protected final ReservationDates reservationDates;
    protected final Set<Tag> tags = new HashSet<>();

    public Room(RoomNumber roomNumber, Capacity capacity, UniqueGuestList occupants, Expenses expenses,
                ReservationDates reservationDates) {
        requireAllNonNull(roomNumber, capacity, occupants, expenses, reservationDates);
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.occupants = occupants;
        this.expenses = expenses;
        this.reservationDates = reservationDates;
    }

    public RoomNumber getRoomNumber() {
        return roomNumber;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public UniqueGuestList getOccupants() {
        return occupants;
    }

    public Expenses getExpenses() {
        return expenses;
    }

    public ReservationDates getReservationDates() {
        return reservationDates;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both rooms of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two rooms.
     */
    public boolean isSameRoom(Room otherRoom) {
        if (otherRoom == this) {
            return true;
        }

        return otherRoom != null
            && otherRoom.getRoomNumber().equals(getRoomNumber());
    }

    /**
     * Returns true if both rooms have the same identity and data fields.
     * This defines a stronger notion of equality between two rooms.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Room)) {
            return false;
        }

        Room otherRoom = (Room) other;
        return otherRoom.getRoomNumber().equals(getRoomNumber())
                && otherRoom.getCapacity().equals(getCapacity())
                && otherRoom.getOccupants().equals(getOccupants())
                && otherRoom.getReservationDates().equals(getReservationDates())
                && otherRoom.getExpenses().equals(getExpenses())
                && otherRoom.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(roomNumber, capacity, expenses, reservationDates, occupants, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Room ID: ")
                .append(getRoomNumber())
                .append(" Capacity: ")
                .append(getCapacity())
                .append(" Occupants: ")
                .append(getOccupants())
                .append(" Reservation Dates: ")
                .append(getReservationDates())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
