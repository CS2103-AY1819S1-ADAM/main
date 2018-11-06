package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysDetailedRoom;

import java.time.LocalDateTime;

import org.junit.Test;

import guitests.guihandles.RoomDetailedCardHandle;
import seedu.address.model.expenses.ExpenseType;
import seedu.address.model.expenses.Money;
import seedu.address.model.room.Room;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.ExpenseBuilder;
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

    @Test
    public void addExpense_updatesRoomDetailedCard() {
        Room room = SampleDataUtil.getSampleConcierge().getRoomList().get(0);
        assertTrue(room.getBookings().getFirstActiveBooking().get().getIsCheckedIn());
        assertCardDisplay(new RoomDetailedCard(room), room);
        room.addExpense(new ExpenseBuilder()
                .withExpenseType(ExpenseBuilder.DEFAULT_EXPENSE_TYPE)
                .withCost(new Money(123, 45))
                .withDateTime(LocalDateTime.now())
                .build());
        assertCardDisplay(new RoomDetailedCard(room), room);
        room.addExpense(new ExpenseBuilder()
                .withExpenseType(new ExpenseType("TEST", "test", new Money(81, 76)))
                .withCost(new Money(378, 12))
                .withDateTime(LocalDateTime.now())
                .build());
        assertCardDisplay(new RoomDetailedCard(room), room);
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
