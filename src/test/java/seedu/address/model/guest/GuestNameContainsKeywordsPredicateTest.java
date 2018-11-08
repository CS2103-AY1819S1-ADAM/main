package seedu.address.model.guest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.GuestBuilder;

public class GuestNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<Name> firstPredicateKeywordList = Collections.singletonList(new Name("first"));
        List<Name> secondPredicateKeywordList = Arrays.asList(new Name("first"), new Name("second"));

        GuestNameContainsKeywordsPredicate firstPredicate =
                new GuestNameContainsKeywordsPredicate(firstPredicateKeywordList);
        GuestNameContainsKeywordsPredicate secondPredicate =
                new GuestNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GuestNameContainsKeywordsPredicate firstPredicateCopy =
                new GuestNameContainsKeywordsPredicate(firstPredicateKeywordList);
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
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        GuestNameContainsKeywordsPredicate predicate =
                new GuestNameContainsKeywordsPredicate(Collections.singletonList(new Name("Alice")));
        assertTrue(predicate.test(new GuestBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new GuestNameContainsKeywordsPredicate(Arrays.asList(new Name("Alice"), new Name("Bob")));
        assertTrue(predicate.test(new GuestBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new GuestNameContainsKeywordsPredicate(Arrays.asList(new Name("Bob"), new Name("Carol")));
        assertTrue(predicate.test(new GuestBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new GuestNameContainsKeywordsPredicate(Arrays.asList(new Name("aLIce"), new Name("bOB")));
        assertTrue(predicate.test(new GuestBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        GuestNameContainsKeywordsPredicate predicate = new GuestNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new GuestBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new GuestNameContainsKeywordsPredicate(Arrays.asList(new Name("Carol")));
        assertFalse(predicate.test(new GuestBuilder().withName("Alice Bob").build()));

        // Keywords match phone, but does not match name
        predicate = new GuestNameContainsKeywordsPredicate(Arrays.asList(new Name("12345")));
        assertFalse(predicate.test(new GuestBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").build()));
    }
}
