package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalGuests.getTypicalGuests;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_GUEST;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysGuest;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.GuestCardHandle;
import guitests.guihandles.GuestListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.guest.Guest;
import seedu.address.storage.XmlSerializableAddressBook;

public class GuestListPanelTest extends GuiUnitTest {
    private static final ObservableList<Guest> TYPICAL_GUESTS =
            FXCollections.observableList(getTypicalGuests());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_GUEST);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private GuestListPanelHandle guestListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_GUESTS);

        for (int i = 0; i < TYPICAL_GUESTS.size(); i++) {
            guestListPanelHandle.navigateToCard(TYPICAL_GUESTS.get(i));
            Guest expectedGuest = TYPICAL_GUESTS.get(i);
            GuestCardHandle actualCard = guestListPanelHandle.getGuestCardHandle(i);

            assertCardDisplaysGuest(expectedGuest, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_GUESTS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        GuestCardHandle expectedGuest = guestListPanelHandle.getGuestCardHandle(INDEX_SECOND_GUEST.getZeroBased());
        GuestCardHandle selectedGuest = guestListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedGuest, selectedGuest);
    }

    /**
     * Verifies that creating and deleting large number of guests in {@code GuestListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Guest> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of guest cards exceeded time limit");
    }

    /**
     * Returns a list of guests containing {@code guestCount} guests that is used to populate the
     * {@code GuestListPanel}.
     */
    private ObservableList<Guest> createBackingList(int guestCount) throws Exception {
        Path xmlFile = createXmlFileWithGuests(guestCount);
        XmlSerializableAddressBook xmlAddressBook =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableAddressBook.class);
        return FXCollections.observableArrayList(xmlAddressBook.toModelType().getGuestList());
    }

    /**
     * Returns a .xml file containing {@code guestCount} guests.
     * This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithGuests(int guestCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<addressbook>\n");
        for (int i = 0; i < guestCount; i++) {
            builder.append("<guest>\n");
            builder.append("<name>").append(i).append("a</name>\n");
            builder.append("<phone>000</phone>\n");
            builder.append("<email>a@aa</email>\n");
            builder.append("<address>a</address>\n");
            builder.append("</guest>\n");
        }
        builder.append("</addressbook>\n");

        Path manyGuestsFile = Paths.get(TEST_DATA_FOLDER + "manyGuests.xml");
        FileUtil.createFile(manyGuestsFile);
        FileUtil.writeToFile(manyGuestsFile, builder.toString());
        manyGuestsFile.toFile().deleteOnExit();
        return manyGuestsFile;
    }

    /**
     * Initializes {@code guestListPanelHandle} with a {@code GuestListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code GuestListPanel}.
     */
    private void initUi(ObservableList<Guest> backingList) {
        GuestListPanel guestListPanel = new GuestListPanel(backingList);
        uiPartRule.setUiPart(guestListPanel);

        guestListPanelHandle = new GuestListPanelHandle(getChildNode(guestListPanel.getRoot(),
                GuestListPanelHandle.GUEST_LIST_VIEW_ID));
    }
}
