package seedu.address.model.room;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.Guest;
import seedu.address.model.room.exceptions.UnoccupiedRoomCheckoutException;
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
    protected final List<Guest> occupant; // Note: List must be at most size == 1
    protected final Expenses expenses;
    protected final Reservations reservations;
    protected final Set<Tag> tags = new HashSet<>();

    /**
     * NEVER USE THIS DEFAULT CONSTRUCTOR.
     * I had to write this default constructor because Java necessitates that `final` fields must be
     * instantiated. Between choosing this or removing `final` access modifier, I decided to go with this because
     * the latter opens up the possibility for once-correct fields to be changed unintentionally.
     * If you can find a better alternative, please let me know and I will replace this stain immediately.
     */
    public Room() {
        this.roomNumber = new RoomNumber("001");
        this.capacity = new Capacity(1);
        this.occupant = new ArrayList<>();
        this.expenses = new Expenses();
        this.reservations = new Reservations();
    }

    /**
     * All parameters must be non-null.
     * Note: {@code occupant}, {@code expenses}, or {@code reservations} may be empty, but not null.
     */
    protected Room(RoomNumber roomNumber, Capacity capacity, List<Guest> occupant, Expenses expenses,
                Reservations reservations) {
        requireAllNonNull(roomNumber, capacity, occupant, expenses, reservations);
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.occupant = occupant;
        this.expenses = expenses;
        this.reservations = reservations;
    }

    public RoomNumber getRoomNumber() {
        return roomNumber;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public List<Guest> getOccupant() {
        return occupant;
    }

    public Expenses getExpenses() {
        return expenses;
    }

    public Reservations getReservations() {
        return reservations;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if room is occupied.
     */
    public boolean isOccupied() {
        return occupant.isEmpty();
    }

    /**
     * Returns true if both rooms of the same name have the same room number.
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
     * Returns true if the room number identifies this room.
     */
    public boolean isRoom(RoomNumber roomNumber) {
        return getRoomNumber().equals(roomNumber);
    }

    /**
     * Checks out this room and its occupant.
     * Future features to include exporting of receipt, setting room to housekeeping status for 1 hour.
     */
    public void checkout() {
        if (occupant.isEmpty()) {
            throw new UnoccupiedRoomCheckoutException();
        }
        // Add guest.checkout() once its implemented
        occupant.clear();
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
                && otherRoom.getOccupant().equals(getOccupant())
                && otherRoom.getReservations().equals(getReservations())
                && otherRoom.getExpenses().equals(getExpenses())
                && otherRoom.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(roomNumber, capacity, expenses, reservations, occupant, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Room: ")
                .append(getRoomNumber())
                .append(" Capacity: ")
                .append(getCapacity())
                .append(" Registered Guest: ");
        getOccupant().forEach(builder::append);
        builder.append(" Reservations: ")
                .append(getReservations())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
