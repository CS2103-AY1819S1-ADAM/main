package seedu.address.model.room;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a room's capacity in the hotel.
 * Guarantees: immutable; is valid as declared in {@link #isValidCapacity(int)}
 */
public class Capacity {

    public static final String MESSAGE_CAPACITY_CONSTRAINTS =
            "Capacity should only contain positive integers, and it should not be blank";

    public final Integer value;

    /**
     * Constructs a {@code Name}.
     *
     * @param value A valid name.
     */
    public Capacity(int value) {
        checkArgument(isValidCapacity(value), MESSAGE_CAPACITY_CONSTRAINTS);
        this.value = value;
    }

    /**
     * Gets the integer value of this capacity
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Returns true if a given int is a valid capacity.
     */
    public static boolean isValidCapacity(int capacity) {
        return capacity > 0;
    }


    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Capacity // instanceof handles nulls
                && value.equals(((Capacity) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
