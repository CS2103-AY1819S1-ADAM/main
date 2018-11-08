package seedu.address.model.room;

/**
 * Represents a room's capacity in the hotel.
 * Guarantees: immutable; is valid as declared in the constants of this enum
 */
public enum Capacity {
    SINGLE(1),
    DOUBLE(2),
    SUITE(5);

    public static final String MESSAGE_CAPACITY_CONSTRAINTS =
            "Capacity should only be SINGLE, DOUBLE, or SUITE. SINGLE is of integer value 1, DOUBLE is 2, SUITE is 5.";

    public final int value;

    /**
     * Constructs a {@code Name}.
     *
     * @param value A valid name.
     */
    Capacity(int value) {
        this.value = value;
    }

    /**
     * Gets the integer value of this capacity
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns true if a given string is a valid capacity.
     */
    public static boolean isValidCapacity(String test) {
        int value = 0;

        if (test.length() > 1) {
            return false;
        }

        try {
            value = Integer.parseInt(test);
        } catch (NumberFormatException e) {
            return false;
        }

        return SINGLE.value == value || DOUBLE.value == value || SUITE.value == value;
    }

    @Override
    public String toString() {
        return String.format("%d | %s", value, this.name());
    }

}
