package seedu.address.model.room;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.RoomBuilder;

public class RoomNumberExactPredicateTest {

    @Test
    public void equals() {
        RoomNumber firstPredicateRoomNumber = new RoomNumber("001");
        RoomNumber secondPredicateRoomNumber = new RoomNumber("002");

        RoomNumberExactPredicate firstPredicate = new RoomNumberExactPredicate(firstPredicateRoomNumber);
        RoomNumberExactPredicate secondPredicate = new RoomNumberExactPredicate(secondPredicateRoomNumber);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RoomNumberExactPredicate firstPredicateCopy =
                new RoomNumberExactPredicate(new RoomNumber("001"));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different guest -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // Same hashcode
        assertTrue(firstPredicate.hashCode() == firstPredicate.hashCode());

        // Different hashcode
        assertFalse(firstPredicate.hashCode() == secondPredicate.hashCode());
    }

    @Test
    public void test_sameRoomNumber_returnsTrue() {
        RoomNumberExactPredicate predicate =
                new RoomNumberExactPredicate(new RoomNumber("001"));
        assertTrue(predicate.test(new RoomBuilder().withRoomNumber("001").build()));
    }

    @Test
    public void test_differentPhone_returnsFalse() {
        RoomNumberExactPredicate predicate =
                new RoomNumberExactPredicate(new RoomNumber("002"));
        assertFalse(predicate.test(new RoomBuilder().withRoomNumber("001").build()));
    }
}
