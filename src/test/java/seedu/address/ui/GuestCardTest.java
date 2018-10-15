package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.person.Guest;
import seedu.address.testutil.GuestBuilder;

public class GuestCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Guest guestWithNoTags = new GuestBuilder().withTags(new String[0]).build();
        PersonCard personCard = new PersonCard(guestWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, guestWithNoTags, 1);

        // with tags
        Guest guestWithTags = new GuestBuilder().build();
        personCard = new PersonCard(guestWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, guestWithTags, 2);
    }

    @Test
    public void equals() {
        Guest guest = new GuestBuilder().build();
        PersonCard personCard = new PersonCard(guest, 0);

        // same guest, same index -> returns true
        PersonCard copy = new PersonCard(guest, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different guest, same index -> returns false
        Guest differentGuest = new GuestBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentGuest, 0)));

        // same guest, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(guest, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedGuest} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, Guest expectedGuest, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify guest details are displayed correctly
        assertCardDisplaysPerson(expectedGuest, personCardHandle);
    }
}
