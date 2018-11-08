package seedu.address.model.room;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.RoomBuilder;

public class RoomCapacityExactPredicateTest {

    @Test
    public void equals() {
        Capacity firstPredicateCapacity = Capacity.SINGLE;
        Capacity secondPredicateCapacity = Capacity.DOUBLE;

        RoomCapacityExactPredicate firstPredicate = new RoomCapacityExactPredicate(firstPredicateCapacity.toString());
        RoomCapacityExactPredicate secondPredicate = new RoomCapacityExactPredicate(secondPredicateCapacity.toString());

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RoomCapacityExactPredicate firstPredicateCopy =
                new RoomCapacityExactPredicate(String.valueOf(Capacity.SINGLE.toString()));
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
    public void test_sameCapacity_returnsTrue() {
        RoomCapacityExactPredicate predicate =
                new RoomCapacityExactPredicate(String.valueOf(Capacity.SINGLE.value));
        assertTrue(predicate.test(new RoomBuilder().withCapacity(Capacity.SINGLE).build()));
    }

    @Test
    public void test_differentPhone_returnsFalse() {
        RoomCapacityExactPredicate predicate =
                new RoomCapacityExactPredicate(String.valueOf(Capacity.DOUBLE.value));
        assertFalse(predicate.test(new RoomBuilder().withCapacity(Capacity.SINGLE).build()));
    }
}
