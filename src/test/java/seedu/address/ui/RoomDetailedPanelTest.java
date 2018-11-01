package seedu.address.ui;

import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalRooms.getTypicalUniqueRoomListClean;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysDetailedRoom;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.RoomDetailedCardHandle;
import guitests.guihandles.RoomDetailedPanelHandle;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.RoomPanelSelectionChangedEvent;
import seedu.address.model.room.Room;

public class RoomDetailedPanelTest extends GuiUnitTest {
    private static final ObservableList<Room> TYPICAL_ROOMS =
            getTypicalUniqueRoomListClean().asUnmodifiableObservableList();

    private RoomPanelSelectionChangedEvent selectionChangedEventStub;

    private RoomDetailedPanel roomDetailedPanel;
    private RoomDetailedPanelHandle roomDetailedPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> roomDetailedPanel = new RoomDetailedPanel());
        uiPartRule.setUiPart(roomDetailedPanel);

        roomDetailedPanelHandle = new RoomDetailedPanelHandle(getChildNode(roomDetailedPanel.getRoot(),
                RoomDetailedPanelHandle.ROOM_LIST_VIEW_ID));
    }

    @Test
    public void display() throws Exception {
        for (int i = 0; i < TYPICAL_ROOMS.size(); i++) {
            selectionChangedEventStub = new RoomPanelSelectionChangedEvent(TYPICAL_ROOMS.get(i));
            postNow(selectionChangedEventStub);

            Room expectedRoom = TYPICAL_ROOMS.get(i);
            roomDetailedPanelHandle.navigateToCard(TYPICAL_ROOMS.get(i));
            RoomDetailedCardHandle actualCard = roomDetailedPanelHandle.getRoomDetailedCardHandle(0);

            assertCardDisplaysDetailedRoom(expectedRoom, actualCard);
        }

    }
}
