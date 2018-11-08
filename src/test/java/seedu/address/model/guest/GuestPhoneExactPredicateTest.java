package seedu.address.model.guest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.GuestBuilder;

public class GuestPhoneExactPredicateTest {

    @Test
    public void equals() {
        Phone firstPredicatePhone = new Phone("81027115");
        Phone secondPredicatePhone = new Phone("91234567");

        GuestPhoneExactPredicate firstPredicate = new GuestPhoneExactPredicate(firstPredicatePhone);
        GuestPhoneExactPredicate secondPredicate = new GuestPhoneExactPredicate(secondPredicatePhone);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GuestPhoneExactPredicate firstPredicateCopy =
                new GuestPhoneExactPredicate(firstPredicatePhone);
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
    public void test_samePhone_returnsTrue() {
        GuestPhoneExactPredicate predicate =
                new GuestPhoneExactPredicate(new Phone("81027115"));
        assertTrue(predicate.test(new GuestBuilder().withName("Alice Bob").withPhone("81027115").build()));
    }

    @Test
    public void test_differentPhone_returnsFalse() {
        GuestPhoneExactPredicate predicate =
                new GuestPhoneExactPredicate(new Phone("81027115"));
        assertFalse(predicate.test(new GuestBuilder().withName("Alice Bob").withPhone("91234567").build()));
    }
}
