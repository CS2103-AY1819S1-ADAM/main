package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysGuest;

import org.junit.Test;

import guitests.guihandles.GuestCardHandle;
import seedu.address.model.guest.Guest;
import seedu.address.testutil.GuestBuilder;

public class GuestCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Guest guestWithNoTags = new GuestBuilder().withTags(new String[0]).build();
        GuestCard guestCard = new GuestCard(guestWithNoTags, 1);
        uiPartRule.setUiPart(guestCard);
        assertCardDisplay(guestCard, guestWithNoTags, 1);

        // with tags
        Guest guestWithTags = new GuestBuilder().build();
        guestCard = new GuestCard(guestWithTags, 2);
        uiPartRule.setUiPart(guestCard);
        assertCardDisplay(guestCard, guestWithTags, 2);
    }

    @Test
    public void equals() {
        Guest guest = new GuestBuilder().build();
        GuestCard guestCard = new GuestCard(guest, 0);

        // same guest, same index -> returns true
        GuestCard copy = new GuestCard(guest, 0);
        assertTrue(guestCard.equals(copy));

        // same object -> returns true
        assertTrue(guestCard.equals(guestCard));

        // null -> returns false
        assertFalse(guestCard.equals(null));

        // different types -> returns false
        assertFalse(guestCard.equals(0));

        // different guest, same index -> returns false
        Guest differentGuest = new GuestBuilder().withName("differentName").build();
        assertFalse(guestCard.equals(new GuestCard(differentGuest, 0)));

        // same guest, different index -> returns false
        assertFalse(guestCard.equals(new GuestCard(guest, 1)));
    }

    /**
     * Asserts that {@code guestCard} displays the details of {@code expectedGuest} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(GuestCard guestCard, Guest expectedGuest, int expectedId) {
        guiRobot.pauseForHuman();
        GuestCardHandle guestCardHandle = new GuestCardHandle(guestCard.getRoot());


        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", guestCardHandle.getId());

        // verify guest details are displayed correctly
        assertCardDisplaysGuest(expectedGuest, guestCardHandle);
    }
}
