package seedu.address.model.room;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Room's room number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRoomNumber(String)}
 */
public class RoomNumber {

    public static final String MAX_ROOM_NUMBER = "100";
    public static final String MESSAGE_ROOM_NUMBER_CONSTRAINTS =
            String.format("Room Number should only contain integers from 001 to %s, and it should not be blank",
                MAX_ROOM_NUMBER);

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ROOM_NUMBER_VALIDATION_REGEX = "^(0\\d[1-9]|0[1-9]\\d|100)$";

    public final String value;

    /**
     * Constructs a {@code id}.
     *
     * @param value A valid room number.
     */
    public RoomNumber(String value) {
        requireNonNull(value);
        checkArgument(isValidRoomNumber(value), MESSAGE_ROOM_NUMBER_CONSTRAINTS);
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid room number.
     */
    public static boolean isValidRoomNumber(String test) {
        return test.matches(ROOM_NUMBER_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RoomNumber // instanceof handles nulls
                && value.equals(((RoomNumber) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
