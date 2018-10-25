package seedu.address.ui;

import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalGuests.getTypicalGuests;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysDetailedGuest;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.GuestDetailedCardHandle;
import guitests.guihandles.GuestDetailedPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.GuestPanelSelectionChangedEvent;
import seedu.address.model.guest.Guest;


public class GuestDetailedPanelTest extends GuiUnitTest {
    private static final ObservableList<Guest> TYPICAL_GUESTS =
            FXCollections.observableList(getTypicalGuests());

    private GuestPanelSelectionChangedEvent selectionChangedEventStub;

    private GuestDetailedPanel guestDetailedPanel;
    private GuestDetailedPanelHandle guestDetailedPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> guestDetailedPanel = new GuestDetailedPanel());
        uiPartRule.setUiPart(guestDetailedPanel);

        guestDetailedPanelHandle = new GuestDetailedPanelHandle(getChildNode(guestDetailedPanel.getRoot(),
                GuestDetailedPanelHandle.GUEST_LIST_VIEW_ID));
    }

    @Test
    public void display() throws Exception {
        for (int i = 0; i < TYPICAL_GUESTS.size(); i++) {
            selectionChangedEventStub = new GuestPanelSelectionChangedEvent(TYPICAL_GUESTS.get(i));
            postNow(selectionChangedEventStub);

            Guest expectedGuest = TYPICAL_GUESTS.get(i);
            guestDetailedPanelHandle.navigateToCard(TYPICAL_GUESTS.get(i));
            GuestDetailedCardHandle actualCard = guestDetailedPanelHandle.getGuestDetailedCardHandle(0);

            assertCardDisplaysDetailedGuest(expectedGuest, actualCard);
        }

    }
}
