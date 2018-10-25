package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysDetailedRoom;

import org.junit.Test;

import guitests.guihandles.RoomDetailedCardHandle;
import seedu.address.model.room.Room;
import seedu.address.testutil.RoomBuilder;

public class RoomDetailedCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Room roomWithNoTags = new RoomBuilder().withTags(new String[0]).build();
        RoomDetailedCard roomDetailedCard = new RoomDetailedCard(roomWithNoTags);
        uiPartRule.setUiPart(roomDetailedCard);
        assertCardDisplay(roomDetailedCard, roomWithNoTags);

        // with tags
        Room roomWithTags = new RoomBuilder().build();
        roomDetailedCard = new RoomDetailedCard(roomWithTags);
        uiPartRule.setUiPart(roomDetailedCard);
        assertCardDisplay(roomDetailedCard, roomWithTags);
    }

    @Test
    public void equals() {
        Room room = new RoomBuilder().build();
        RoomDetailedCard roomDetailedCard = new RoomDetailedCard(room);

        // same room, same index -> returns true
        RoomDetailedCard copy = new RoomDetailedCard(room);
        assertTrue(roomDetailedCard.equals(copy));

        // same object -> returns true
        assertTrue(roomDetailedCard.equals(roomDetailedCard));

        // null -> returns false
        assertFalse(roomDetailedCard.equals(null));

        // different types -> returns false
        assertFalse(roomDetailedCard.equals(0));

        // different room number -> returns false
        Room differentRoom = new RoomBuilder().withRoomNumber("099").build();
        assertFalse(roomDetailedCard.equals(new RoomDetailedCard(differentRoom)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedRoom} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(RoomDetailedCard roomDetailedCard, Room expectedRoom) {
        guiRobot.pauseForHuman();

        RoomDetailedCardHandle roomDetailedCardHandle = new RoomDetailedCardHandle(roomDetailedCard.getRoot());

        // verify room details are displayed correctly
        assertCardDisplaysDetailedRoom(expectedRoom, roomDetailedCardHandle);
    }
}
