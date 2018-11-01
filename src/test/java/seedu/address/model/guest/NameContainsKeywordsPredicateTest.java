package seedu.address.model.guest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.GuestBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

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
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        GuestNameContainsKeywordsPredicate predicate =
                new GuestNameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new GuestBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new GuestNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new GuestBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new GuestNameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new GuestBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new GuestNameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new GuestBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        GuestNameContainsKeywordsPredicate predicate = new GuestNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new GuestBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new GuestNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new GuestBuilder().withName("Alice Bob").build()));

        // Keywords match phone and email, but does not match name
        predicate = new GuestNameContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com"));
        assertFalse(predicate.test(new GuestBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").build()));
    }
}
