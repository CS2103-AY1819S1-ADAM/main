package seedu.address.model.room;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.room.exceptions.DuplicateRoomException;
import seedu.address.model.room.exceptions.RoomNotFoundException;

/**
 * A list of rooms that enforces uniqueness between its elements and does not allow nulls.
 * A room is considered unique by comparing using {@code Room#isSameRoom(Room)}. As such, adding and updating of
 * rooms uses Room#isSameRoom(Room) for equality so as to ensure that the room being added or updated is
 * unique in terms of identity in the UniqueRoomList. A room can be removed by passing either its reference or
 * room number to the remove() method.
 *
 * Supports a minimal set of list operations.
 *
 * @see Room#isSameRoom(Room)
 */
public class UniqueRoomList implements Iterable<Room> {

    private final ObservableList<Room> internalList = FXCollections.observableArrayList();

    /**
     * Initializes an empty room list
     */
    public UniqueRoomList() {}

    /**
     * Initializes a room list with rooms ranging from 001 up to the maxRoomNumber
     * @param maxRoomNumber The maximum room number as a string
     */
    public UniqueRoomList(String maxRoomNumber) {
        this.internalList.setAll(Stream.iterate(1, i -> i <= Integer.parseInt(maxRoomNumber), i -> i + 1)
            .map(i -> {
                RoomNumber roomNumber = new RoomNumber(String.format("%03d", i));
                if (i % 10 == 0) { // All rooms with room number that is multiple of 10 is a SuiteRoom.
                    return new SuiteRoom(roomNumber);
                }
                if (i % 2 == 0) { // All rooms with even room number is a DoubleRoom.
                    return new DoubleRoom(roomNumber);
                }
                // ALl rooms with odd room number is a SingleRoom.
                return new SingleRoom(roomNumber);
            })
            .collect(Collectors.toList()));
    }
    //=========== Getters =============================================================

    /**
     * Returns the room according to the room number
     */
    public Room getRoom(RoomNumber roomNumber) {
        requireNonNull(roomNumber);
        for (Room room : internalList) {
            if (room.getRoomNumber().equals(roomNumber)) {
                return room;
            }
        }
        throw new RoomNotFoundException();
    }

    //=========== Operations =============================================================

    /**
     * Adds a room to the list.
     * The room must not already exist in the list.
     */
    public void add(Room toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRoomException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent room from the list.
     * The room must exist in the list.
     */
    public void remove(Room toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new RoomNotFoundException();
        }
    }

    /**
     * Replaces the room {@code target} in the list with {@code editedRoom}.
     * {@code target} must exist in the list.
     * The room identity of {@code editedRoom} must not be the same as another existing room in the list.
     */
    public void setRoom(Room target, Room editedRoom) {
        requireAllNonNull(target, editedRoom);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new RoomNotFoundException();
        }

        if (!target.isSameRoom(editedRoom) && contains(editedRoom)) {
            throw new DuplicateRoomException();
        }

        internalList.set(index, editedRoom);
    }

    /**
     * Replaces the contents of this list with {@code rooms}.
     * {@code rooms} must not contain duplicate rooms.
     */
    public void setRooms(List<Room> rooms) {
        requireAllNonNull(rooms);
        if (!roomsAreUnique(rooms)) {
            throw new DuplicateRoomException();
        }
        internalList.setAll(rooms);
    }

    //=========== Boolean checkers =============================================================

    /**
     * Returns true if the list contains an equivalent room as the given argument.
     */
    public boolean contains(Room toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameRoom);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Room> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Room> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueRoomList // instanceof handles nulls
                        && internalList.equals(((UniqueRoomList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code rooms} contains only unique rooms.
     */
    private boolean roomsAreUnique(List<Room> rooms) {
        for (int i = 0; i < rooms.size() - 1; i++) {
            for (int j = i + 1; j < rooms.size(); j++) {
                if (rooms.get(i).isSameRoom(rooms.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
