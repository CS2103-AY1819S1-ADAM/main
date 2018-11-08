package seedu.address.model.guest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.GuestBuilder;

public class GuestEmailExactPredicateTest {

    @Test
    public void equals() {
        Email firstPredicateEmail = new Email("abc123@gmail.com");
        Email secondPredicateEmail = new Email("abc124@gmail.com");

        GuestEmailExactPredicate firstPredicate = new GuestEmailExactPredicate(firstPredicateEmail);
        GuestEmailExactPredicate secondPredicate = new GuestEmailExactPredicate(secondPredicateEmail);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GuestEmailExactPredicate firstPredicateCopy =
                new GuestEmailExactPredicate(firstPredicateEmail);
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
    public void test_sameEmail_returnsTrue() {
        GuestEmailExactPredicate predicate =
                new GuestEmailExactPredicate(new Email("abc@gmail.com"));
        assertTrue(predicate.test(new GuestBuilder().withName("Alice Bob").withEmail("abc@gmail.com").build()));
    }

    @Test
    public void test_differentEmail_returnsFalse() {
        GuestEmailExactPredicate predicate =
                new GuestEmailExactPredicate(new Email("abc@gmail.com"));
        assertFalse(predicate.test(new GuestBuilder().withName("Alice Bob").withEmail("abd@gmail.com").build()));
    }
}
