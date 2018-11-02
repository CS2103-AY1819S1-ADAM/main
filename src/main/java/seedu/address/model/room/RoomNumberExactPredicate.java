package seedu.address.model.room;

import java.util.function.Predicate;

/**
 * Tests that a {@code Room}'s {@code RoomNumber} exactly matches {@code RoomNumber} argument.
 */
public class RoomNumberExactPredicate implements Predicate<Room> {
    private final String roomNumber;

    public RoomNumberExactPredicate(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public boolean test(Room room) {
        return room.getRoomNumber().value.equals(roomNumber);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RoomNumberExactPredicate // instanceof handles nulls
                && roomNumber.equals(((RoomNumberExactPredicate) other).roomNumber)); // state check
    }

    @Override
    public int hashCode() {
        return roomNumber.hashCode();
    }
}
