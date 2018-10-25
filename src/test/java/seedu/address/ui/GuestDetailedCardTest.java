package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysDetailedGuest;

import org.junit.Test;

import guitests.guihandles.GuestDetailedCardHandle;
import seedu.address.model.guest.Guest;
import seedu.address.testutil.GuestBuilder;

public class GuestDetailedCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Guest guestWithNoTags = new GuestBuilder().withTags(new String[0]).build();
        GuestDetailedCard guestDetailedCard = new GuestDetailedCard(guestWithNoTags);
        uiPartRule.setUiPart(guestDetailedCard);
        assertCardDisplay(guestDetailedCard, guestWithNoTags);

        // with tags
        Guest guestWithTags = new GuestBuilder().build();
        guestDetailedCard = new GuestDetailedCard(guestWithTags);
        uiPartRule.setUiPart(guestDetailedCard);
        assertCardDisplay(guestDetailedCard, guestWithTags);
    }

    @Test
    public void equals() {
        Guest guest = new GuestBuilder().build();
        GuestDetailedCard guestDetailedCard = new GuestDetailedCard(guest);

        // same guest, same index -> returns true
        GuestDetailedCard copy = new GuestDetailedCard(guest);
        assertTrue(guestDetailedCard.equals(copy));

        // same object -> returns true
        assertTrue(guestDetailedCard.equals(guestDetailedCard));

        // null -> returns false
        assertFalse(guestDetailedCard.equals(null));

        // different types -> returns false
        assertFalse(guestDetailedCard.equals(0));

        // different guest, same index -> returns false
        Guest differentGuest = new GuestBuilder().withName("differentName").build();
        assertFalse(guestDetailedCard.equals(new GuestDetailedCard(differentGuest)));

        // same guest, different index -> returns false
        assertFalse(guestDetailedCard.equals(new GuestDetailedCard(differentGuest)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedGuest} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(GuestDetailedCard guestDetailedCard, Guest expectedGuest) {
        guiRobot.pauseForHuman();

        GuestDetailedCardHandle guestDetailedCardHandle = new GuestDetailedCardHandle(guestDetailedCard.getRoot());

        // verify guest details are displayed correctly
        assertCardDisplaysDetailedGuest(expectedGuest, guestDetailedCardHandle);
    }
}
