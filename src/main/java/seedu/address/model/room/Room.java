package seedu.address.model.room;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.expenses.Expense;
import seedu.address.model.expenses.Expenses;
import seedu.address.model.guest.Guest;
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

    protected Room(Room room) {
        this(room.getRoomNumber(), room.getCapacity());
        this.expenses.setExpenses(room.getExpenses());
        this.bookings.setBookings(room.getBookings());
    }

    public RoomNumber getRoomNumber() {
        return roomNumber;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public Expenses getExpenses() {
        return expenses;
    }

    public List<Expense> getExpensesList() {
        return expenses.getExpensesList();
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
     * Clones this room with a deep copied Bookings object
     */
    public abstract <T extends Room> T cloneRoom();

    /**
     * Adds a booking to this room's list of bookings
     */
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    /**
     * Update a booking with the edited booking
     */
    public void updateBooking(Booking target, Booking editedBooking) {
        bookings.setBooking(target, editedBooking);
    }

    /**
     * Removes all expired bookings from the list.
     */
    public void clearExpiredBookings() {
        bookings.clearExpiredBookings();
    }

    /**
     * Reset this room's bookings
     */
    public void setBookings(Bookings replacementBookings) {
        bookings.setBookings(replacementBookings);
    }

    public void resetExpenses(Expenses expenses) {
        // to be filled in once Expenses is done by
    }

    /**
     * Returns true if room's first booking has been checked in.
     */
    public boolean isCheckedIn() {
        Booking firstBooking = bookings.getFirstBooking();
        return firstBooking.isCheckedIn();
    }

    /**
     * Returns true if this room's bookings is non-empty
     */
    public boolean hasBooking() {
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
        return firstBooking.isActiveOrExpired();
    }

    public Optional<Booking> getFirstBooking() {
        Booking firstBooking;
        try {
            firstBooking = bookings.getFirstBooking();
        } catch (NoBookingException e) {
            firstBooking = null;
        }
        return Optional.ofNullable(firstBooking);
    }

    /**
     * Add an expense to this room's expenses
     */
    public void addExpense(Expense expense) {
        expenses.addExpense(expense);
    }

    /**
     * Checks in the first booking of this room and its occupant
     */
    public void checkIn() {
        if (!hasActiveBooking()) {
            throw new NoActiveBookingException();
        }
        if (isCheckedIn()) {
            throw new OccupiedRoomCheckinException();
        }
        Booking firstBooking = bookings.getFirstBooking();
        // For tests to pass, we need to deep copy the booking here and replace it with its updated version
        // firstBooking.checkIn() returns a deep copy of the booking with check-in flag set to true
        Booking updatedFirstBooking = firstBooking.checkIn();
        updateBooking(firstBooking, updatedFirstBooking);
    }

    /**
     * Checks out the first booking of this room and its current occupant.
     * Future features to include exporting of receipt, setting room to housekeeping status for __x__ hours.
     */
    public void checkout() {
        if (!hasActiveOrExpiredBooking()) {
            throw new NoActiveOrExpiredBookingException();
        }
        Booking firstBooking = bookings.getFirstBooking();
        Guest guest = firstBooking.getGuest();
        // guest.checkout(); // joyce implement this later

        bookings.remove(firstBooking);
        // expenses.report(); // weizheng implement this later
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
