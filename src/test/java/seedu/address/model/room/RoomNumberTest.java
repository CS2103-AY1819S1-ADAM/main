package seedu.address.model.room;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class RoomNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new RoomNumber(null));
    }

    @Test
    public void constructor_invalidRoomNumber_throwsIllegalArgumentException() {
        String invalidRoomNumber = "101";
        Assert.assertThrows(IllegalArgumentException.class, () -> new RoomNumber(invalidRoomNumber));
    }

    @Test
    public void isValidRoomNumber() {
        // null room number
        Assert.assertThrows(NullPointerException.class, () -> RoomNumber.isValidRoomNumber(null));

        // invalid room numbers
        assertFalse(RoomNumber.isValidRoomNumber("")); // empty string
        assertFalse(RoomNumber.isValidRoomNumber(" ")); // spaces only
        assertFalse(RoomNumber.isValidRoomNumber("000")); // not within 001 to 100
        assertFalse(RoomNumber.isValidRoomNumber("01")); // not 3 digits
        assertFalse(RoomNumber.isValidRoomNumber("room number")); // non-numeric
        assertFalse(RoomNumber.isValidRoomNumber("050a")); // alphabets within digits
        assertFalse(RoomNumber.isValidRoomNumber("0 01")); // spaces within digits

        // valid room numbers
        assertTrue(RoomNumber.isValidRoomNumber("001")); // exactly 3 digits and within 001 to 100
        assertTrue(RoomNumber.isValidRoomNumber("099"));
        assertTrue(RoomNumber.isValidRoomNumber("100"));
    }
}
