package seedu.address.model.guest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.GuestBuilder;

public class GuestTagsContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<Tag> firstPredicateKeywordList = Collections.singletonList(new Tag("friends"));
        List<Tag> secondPredicateKeywordList = Arrays.asList(new Tag("friends"), new Tag("worker"));

        GuestTagsContainsKeywordsPredicate firstPredicate =
                new GuestTagsContainsKeywordsPredicate(firstPredicateKeywordList);
        GuestTagsContainsKeywordsPredicate secondPredicate =
                new GuestTagsContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GuestTagsContainsKeywordsPredicate firstPredicateCopy =
                new GuestTagsContainsKeywordsPredicate(firstPredicateKeywordList);
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
    public void test_tagsContainsKeywords_returnsTrue() {
        // One keyword
        GuestTagsContainsKeywordsPredicate predicate =
                new GuestTagsContainsKeywordsPredicate(Collections.singletonList(new Tag("friends")));
        assertTrue(predicate.test(new GuestBuilder().withName("Alice Bob").withTags("friends").build()));

        // Multiple keywords, 1 tag
        predicate = new GuestTagsContainsKeywordsPredicate(
                Arrays.asList(new Tag("friends"), new Tag("worker")));
        assertTrue(predicate.test(new GuestBuilder().withName("Alice Bob").withTags("friends").build()));

        // 1 keyword, multiple tags
        predicate = new GuestTagsContainsKeywordsPredicate(Collections.singletonList(new Tag("friends")));
        assertTrue(predicate.test(new GuestBuilder().withName("Alice Bob").withTags("friends", "worker").build()));

        // Mixed-case keywords
        predicate = new GuestTagsContainsKeywordsPredicate(
                Arrays.asList(new Tag("frIenDs"), new Tag("WoRkEr")));
        assertTrue(predicate.test(new GuestBuilder().withName("Alice Bob").withTags("friends", "worker").build()));
    }

    @Test
    public void test_tagsDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        GuestTagsContainsKeywordsPredicate predicate = new GuestTagsContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new GuestBuilder().withName("Alice").withTags("friends").build()));

        // Non-matching keyword
        predicate = new GuestTagsContainsKeywordsPredicate(Arrays.asList(new Tag("Boss")));
        assertFalse(predicate.test(new GuestBuilder().withName("Alice Bob").withTags("friends").build()));

        // Keywords match phone, but does not match name
        predicate = new GuestTagsContainsKeywordsPredicate(Arrays.asList(new Tag("12345")));
        assertFalse(predicate.test(new GuestBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withTags("friends").build()));
    }
}
