package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysRoom;

import org.junit.Test;

import guitests.guihandles.RoomCardHandle;
import seedu.address.model.room.Room;
import seedu.address.testutil.RoomBuilder;

public class RoomCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Room roomWithNoTags = new RoomBuilder().withTags(new String[0]).build();
        RoomCard roomCard = new RoomCard(roomWithNoTags);
        uiPartRule.setUiPart(roomCard);
        assertCardDisplay(roomCard, roomWithNoTags);

        // with tags
        Room roomWithTags = new RoomBuilder().build();
        roomCard = new RoomCard(roomWithTags);
        uiPartRule.setUiPart(roomCard);
        assertCardDisplay(roomCard, roomWithTags);
    }

    @Test
    public void equals() {
        Room room = new RoomBuilder().build();
        RoomCard roomCard = new RoomCard(room);

        // same room, same index -> returns true
        RoomCard copy = new RoomCard(room);
        assertTrue(roomCard.equals(copy));

        // same object -> returns true
        assertTrue(roomCard.equals(roomCard));

        // null -> returns false
        assertFalse(roomCard.equals(null));

        // different types -> returns false
        assertFalse(roomCard.equals(0));

        // different room number -> returns false
        Room differentRoom = new RoomBuilder().withRoomNumber("099").build();
        assertFalse(roomCard.equals(new RoomCard(differentRoom)));
    }

    /**
     * Asserts that {@code roomCard} displays the details of {@code expectedRoom} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(RoomCard roomCard, Room expectedRoom) {
        guiRobot.pauseForHuman();

        RoomCardHandle roomCardHandle = new RoomCardHandle(roomCard.getRoot());

        // verify room details are displayed correctly
        assertCardDisplaysRoom(expectedRoom, roomCardHandle);
    }
}
