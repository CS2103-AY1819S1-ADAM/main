package seedu.address.model.room;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_001;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_002;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.room.exceptions.DuplicateRoomException;
import seedu.address.testutil.RoomBuilder;
import seedu.address.testutil.TypicalBookings;
import seedu.address.testutil.TypicalRooms;

public class UniqueRoomListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueRoomList uniqueRoomList = new UniqueRoomList();
    private final Room room001 = TypicalRooms.getTypicalUniqueRoomListClean().getRoom(ROOM_NUMBER_001);
    private final Room room002 = TypicalRooms.getTypicalUniqueRoomListClean().getRoom(ROOM_NUMBER_002);

    @Test
    public void contains_nullRoom_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRoomList.contains(null);
    }

    @Test
    public void contains_roomInList_returnsTrue() {
        assertTrue(uniqueRoomList.contains(room001));
    }

    @Test
    public void contains_roomWithSameIdentityFieldsInList_returnsTrue() {
        Room editedRoom = new RoomBuilder(room001)
                .withBookings(TypicalBookings.getTypicalBookingsLastWeekYesterdayCheckedIn()).build();
        assertTrue(uniqueRoomList.contains(editedRoom));
    }

    @Test
    public void getRoom_nullTargetRoom_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRoomList.getRoom(null);
    }

    @Test
    public void setRoom_nullTargetRoom_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRoomList.setRoom(null, room001);
    }

    @Test
    public void setRoom_nullEditedRoom_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRoomList.setRoom(room001, null);
    }

    @Test
    public void setRoom_editedRoomIsSameRoom_success() {
        uniqueRoomList.setRoom(room001, room001);
        UniqueRoomList expectedUniqueRoomList = new UniqueRoomList();
        assertEquals(expectedUniqueRoomList, uniqueRoomList);
    }

    @Test
    public void setRoom_editedRoomHasNonUniqueIdentity_throwsDuplicateRoomException() {
        thrown.expect(DuplicateRoomException.class);
        uniqueRoomList.setRoom(room001, room002);
    }

    @Test
    public void setRooms_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRoomList.setRooms((List<Room>) null);
    }

    @Test
    public void setRooms_list_replacesOwnListWithProvidedList() {
        UniqueRoomList expectedUniqueRoomList = new UniqueRoomList();
        Room editedRoom = new RoomBuilder(room001).withBookings(TypicalBookings.getTypicalBookingsTodayTomorrow())
            .build();
        expectedUniqueRoomList.setRoom(room001, editedRoom);
        uniqueRoomList.setRooms(expectedUniqueRoomList.asUnmodifiableObservableList());
        assertEquals(expectedUniqueRoomList, uniqueRoomList);
    }

    @Test
    public void setRooms_listWithDuplicateRooms_throwsDuplicateRoomException() {
        List<Room> listWithDuplicateRooms = Arrays.asList(room001, room001);
        thrown.expect(DuplicateRoomException.class);
        uniqueRoomList.setRooms(listWithDuplicateRooms);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueRoomList.asUnmodifiableObservableList().remove(0);
    }
}
