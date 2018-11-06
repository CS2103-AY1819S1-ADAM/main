package seedu.address.model.room;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.expenses.Expense;
import seedu.address.model.expenses.Expenses;
import seedu.address.model.room.booking.Booking;
import seedu.address.model.room.booking.BookingPeriod;
import seedu.address.model.room.booking.Bookings;
import seedu.address.model.room.booking.exceptions.ExpiredBookingCheckInException;
import seedu.address.model.room.booking.exceptions.InactiveBookingCheckInException;
import seedu.address.model.room.booking.exceptions.NoBookingException;
import seedu.address.model.room.booking.exceptions.RoomNotCheckedInException;
import seedu.address.model.room.exceptions.BookingAlreadyCheckedInException;
import seedu.address.model.tag.Tag;

/**
 * Represents a room in the hotel.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Room {

    // Identity fields
    public final RoomNumber roomNumber;

    // Data fields
    public final Capacity capacity;
    public final Expenses expenses;
    public final Bookings bookings;
    public final Set<Tag> tags;

    public Room(RoomNumber roomNumber, Capacity capacity) {
        this(roomNumber, capacity, new Expenses(), new Bookings(), new HashSet<>());
    }

    /**
     * All parameters must be non-null.
     */
    public Room(RoomNumber roomNumber, Capacity capacity, Expenses expenses, Bookings bookings, Set<Tag> tags) {
        requireAllNonNull(roomNumber, capacity, expenses, bookings, tags);
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.expenses = expenses;
        this.bookings = bookings;
        this.tags = tags;
    }

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

    //=========== Tags operations =============================================================

    /**
     * Returns a copy of this room with given tags added
     */
    public Room addTags(Tag... tags) {
        Set<Tag> editedTags = new HashSet<>(this.tags);
        editedTags.addAll(Arrays.asList(tags));
        return new Room(this.roomNumber, this.capacity, this.expenses, this.bookings, editedTags);
    }

    //=========== Bookings operations =============================================================

    /**
     * Adds a booking to a copy of this room's set of bookings
     */
    public Room addBooking(Booking booking) {
        Bookings editedBookings = bookings.add(booking);
        return new Room(this.roomNumber, this.capacity, this.expenses, editedBookings, this.tags);
    }

    /**
     * Update a booking with the edited booking
     */
    private Room updateBooking(Booking target, Booking editedBooking) {
        Bookings editedBookings = bookings.updateBooking(target, editedBooking);
        return new Room(this.roomNumber, this.capacity, this.expenses, editedBookings, this.tags);
    }

    /**
     * Checks in the first booking of this room, only if:
     * 1) it is not expired
     * 2) it is active
     * 3) it is not already checked-in
     */
    public Room checkIn() {
        Booking firstBooking = bookings.getFirstBooking();
        if (firstBooking.isExpired()) {
            throw new ExpiredBookingCheckInException();
        }
        if (!firstBooking.isActive()) {
            throw new InactiveBookingCheckInException();
        }
        if (firstBooking.getIsCheckedIn()) {
            throw new BookingAlreadyCheckedInException();
        }
        return updateBooking(firstBooking, firstBooking.checkIn());
    }

    /**
     * Checks out the first booking of this room.
     * TODO: Future features to include exporting of receipt, setting room to housekeeping status for __x__ hours.
     */
    public Room checkout() {
        Booking firstBooking = bookings.getFirstBooking();
        return new Room(this.roomNumber, this.capacity, this.expenses, bookings.remove(firstBooking), this.tags);
        // expenses.report(); // TODO: wait for WZ to implement
    }

    /**
     * Checks out the given booking
     * TODO: Future features to include exporting of receipt, setting room to housekeeping status for __x__ hours.
     */
    public Room checkout(BookingPeriod bookingPeriod) {
        Booking bookingToCheckout = bookings
                .getFirstBookingByPredicate(booking -> booking.getBookingPeriod().equals(bookingPeriod));
        return new Room(this.roomNumber, this.capacity, this.expenses, bookings.remove(bookingToCheckout), this.tags);
        // expenses.report(); // TODO: wait for WZ to implement
    }

    //=========== Expenses operations =============================================================

    /**
     * Add an expense to this room's expenses
     */
    public Room addExpense(Expense expense) {
        if (bookings.getSortedBookingsSet().isEmpty()) {
            // no bookings
            throw new NoBookingException();
        }
        if (!bookings.getFirstBooking().getIsCheckedIn()) {
            // not checked in
            throw new RoomNotCheckedInException();
        }
        Expenses editedExpenses = expenses.addExpense(expense);
        return new Room(this.roomNumber, this.capacity, editedExpenses, this.bookings, this.tags);
    }

    //=========== Boolean checkers =============================================================

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
                .append("\n")
                .append("Capacity: ")
                .append(getCapacity())
                .append("\n")
                .append("Expenses: ")
                .append(getExpenses())
                .append("\n")
                .append("Bookings: ")
                .append(getBookings())
                .append("\n")
                .append("Tags: ");
        getTags().forEach(builder::append);
        builder.append("\n\n");
        return builder.toString();
    }

}
