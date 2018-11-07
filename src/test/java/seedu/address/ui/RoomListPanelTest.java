package seedu.address.ui;

import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalRooms.getTypicalUniqueRoomListClean;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysRoom;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Test;

import guitests.guihandles.RoomCardHandle;
import guitests.guihandles.RoomListPanelHandle;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.room.Room;

public class RoomListPanelTest extends GuiUnitTest {
    private static final ObservableList<Room> TYPICAL_ROOMS =
            getTypicalUniqueRoomListClean().asUnmodifiableObservableList();

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT =
            new JumpToListRequestEvent(INDEX_SECOND, CliSyntax.FLAG_ROOM);


    private RoomListPanelHandle roomListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_ROOMS);

        for (int i = 0; i < TYPICAL_ROOMS.size(); i++) {
            roomListPanelHandle.navigateToCard(TYPICAL_ROOMS.get(i));
            Room expectedRoom = TYPICAL_ROOMS.get(i);
            RoomCardHandle actualCard = roomListPanelHandle.getRoomCardHandle(i);

            assertCardDisplaysRoom(expectedRoom, actualCard);
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_ROOMS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        RoomCardHandle expectedRoom = roomListPanelHandle.getRoomCardHandle(INDEX_SECOND.getZeroBased());
        RoomCardHandle selectedRoom = roomListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedRoom, selectedRoom);
    }

    /**
     * Initializes {@code roomListPanelHandsle} with a {@code RoomListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code RoomListPanel}.
     */
    private void initUi(ObservableList<Room> backingList) {
        RoomListPanel roomListPanel = new RoomListPanel(backingList);
        uiPartRule.setUiPart(roomListPanel);

        roomListPanelHandle = new RoomListPanelHandle(getChildNode(roomListPanel.getRoot(),
                RoomListPanelHandle.ROOM_LIST_VIEW_ID));
    }


}
