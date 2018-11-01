package seedu.address.model.room;

import java.util.function.Predicate;

/**
 * Tests that a {@code Room}'s {@code Capacity} exactly matches {@code capacity} argument.
 */
public class RoomCapacityExactPredicate implements Predicate<Room> {
    private final String capacity;

    public RoomCapacityExactPredicate(String capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean test(Room room) {
        return room.getCapacity().value == Integer.parseInt(capacity);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RoomCapacityExactPredicate // instanceof handles nulls
                && capacity.equals(((RoomCapacityExactPredicate) other).capacity)); // state check
    }
    @Override
    public int hashCode() {
        return capacity.hashCode();
    }

}
