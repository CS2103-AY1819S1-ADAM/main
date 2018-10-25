package seedu.address.model.room;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.expenses.Expense;
import seedu.address.model.expenses.Expenses;
import seedu.address.model.room.booking.Booking;
import seedu.address.model.room.booking.Bookings;
import seedu.address.model.room.booking.exceptions.NoActiveBookingException;
import seedu.address.model.room.booking.exceptions.NoActiveOrExpiredBookingException;
import seedu.address.model.room.booking.exceptions.NoBookingException;
import seedu.address.model.room.exceptions.OccupiedRoomCheckinException;
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
    protected final Expenses expenses;
    protected final Bookings bookings;
    protected final Set<Tag> tags;

    protected Room(RoomNumber roomNumber, Capacity capacity) {
        this(roomNumber, capacity, new Expenses(), new Bookings(), new HashSet<>());
    }

    /**
     * All parameters must be non-null.
     */
    protected Room(RoomNumber roomNumber, Capacity capacity, Expenses expenses, Bookings bookings, Set<Tag> tags) {
        requireAllNonNull(roomNumber, capacity, expenses, bookings, tags);
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.expenses = expenses;
        this.bookings = bookings;
        this.tags = tags;
    }

    //=========== Special constructor method !IMPORTANT =============================================================

    /**
     * Method to edit a room by passing arguments and getting a new Room with those arguments
     * Abstract to allow subclass to override and return a {@code Room} of their own type
     * Package-private to hide it from outside classes
     */
    abstract Room makeRoom(RoomNumber roomNumber, Expenses expenses, Bookings bookings, Set<Tag> tags);

    //=========== Getters =============================================================

    public RoomNumber getRoomNumber() {
        return roomNumber;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public Expenses getExpenses() {
        return expenses;
    }

    public Bookings getBookings() {
        return bookings;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an {@code Optional} of the first booking of this room
     */
    public Optional<Booking> getFirstBooking() {
        Booking firstBooking;
        try {
            firstBooking = bookings.getFirstBooking();
        } catch (NoBookingException e) {
            firstBooking = null;
        }
        return Optional.ofNullable(firstBooking);
    }

    //=========== Bookings operations =============================================================

    /**
     * Adds a booking to a copy of this room's set of bookings
     */
    public Room addBooking(Booking booking) {
        Bookings editedBookings = bookings.add(booking);
        return makeRoom(this.roomNumber, this.expenses, editedBookings, this.tags);
    }

    /**
     * Update a booking with the edited booking
     */
    public Room updateBooking(Booking target, Booking editedBooking) {
        Bookings editedBookings = bookings.updateBooking(target, editedBooking);
        return makeRoom(this.roomNumber, this.expenses, editedBookings, this.tags);
    }

    /**
     * Checks in the first booking of this room and its occupant
     */
    public Room checkIn() {
        if (!hasActiveBooking()) {
            throw new NoActiveBookingException();
        }
        if (isCheckedIn()) {
            throw new OccupiedRoomCheckinException();
        }
        Booking firstBooking = bookings.getFirstBooking();
        return updateBooking(firstBooking, firstBooking.checkIn());
    }

    /**
     * Checks out the first booking of this room and its current occupant.
     * Future features to include exporting of receipt, setting room to housekeeping status for __x__ hours.
     */
    public Room checkout() {
        if (!hasActiveOrExpiredBooking()) {
            throw new NoActiveOrExpiredBookingException();
        }
        Booking firstBooking = bookings.getFirstBooking();
        return makeRoom(this.roomNumber, this.expenses, bookings.remove(firstBooking), this.tags);
        // expenses.report(); // weizheng implement this later
    }

    //=========== Expenses operations =============================================================

    /**
     * Add an expense to this room's expenses
     */
    public void addExpense(Expense expense) {
        expenses.addExpense(expense);
    }

    //=========== Boolean checkers =============================================================

    /**
     * Returns true if room's first booking has been checked in.
     */
    public boolean isCheckedIn() {
        Booking firstBooking = bookings.getFirstBooking();
        return firstBooking.getIsCheckedIn();
    }

    /**
     * Returns true if this room's bookings is non-empty
     */
    public boolean hasBookings() {
        return !bookings.isEmpty();
    }

    /**
     * Returns true if room's first booking is active.
     */
    public boolean hasActiveBooking() {
        Booking firstBooking = bookings.getFirstBooking();
        return firstBooking.isActive();
    }

    /**
     * Returns true if room's first booking is active or expired
     */
    public boolean hasActiveOrExpiredBooking() {
        Booking firstBooking = bookings.getFirstBooking();
        return firstBooking.isActive() || firstBooking.isExpired();
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
                && otherRoom.getBookings().equals(getBookings())
                && otherRoom.getExpenses().equals(getExpenses())
                && otherRoom.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(roomNumber, capacity, expenses, bookings, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Room: ")
                .append(getRoomNumber())
                .append(" Capacity: ")
                .append(getCapacity())
                .append("Expenses: ")
                .append(getExpenses())
                .append(" Bookings: ")
                .append(getBookings())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
