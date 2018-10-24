package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.GuestCardHandle;
import guitests.guihandles.GuestListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.guest.Guest;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(GuestCardHandle expectedCard, GuestCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedGuest}.
     */
    public static void assertCardDisplaysGuest(Guest expectedGuest, GuestCardHandle actualCard) {
        assertEquals(expectedGuest.getName().fullName, actualCard.getName());
        assertEquals(expectedGuest.getPhone().value, actualCard.getPhone());
        assertEquals(expectedGuest.getEmail().value, actualCard.getEmail());
        assertEquals(expectedGuest.getAddress().value, actualCard.getAddress());
        assertEquals(expectedGuest.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code guestListPanelHandle} displays the details of {@code guests} correctly and
     * in the correct order.
     */
    public static void assertListMatching(GuestListPanelHandle guestListPanelHandle, Guest... guests) {
        for (int i = 0; i < guests.length; i++) {
            guestListPanelHandle.navigateToCard(i);
            assertCardDisplaysGuest(guests[i], guestListPanelHandle.getGuestCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code guestListPanelHandle} displays the details of {@code guests} correctly and
     * in the correct order.
     */
    public static void assertListMatching(GuestListPanelHandle guestListPanelHandle, List<Guest> guests) {
        assertListMatching(guestListPanelHandle, guests.toArray(new Guest[0]));
    }

    /**
     * Asserts the size of the list in {@code guestListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(GuestListPanelHandle guestListPanelHandle, int size) {
        int numberOfPeople = guestListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
