package seedu.address.model.room;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.RoomBuilder;

public class RoomTagsContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<Tag> firstPredicateKeywordList = Collections.singletonList(new Tag("dirty"));
        List<Tag> secondPredicateKeywordList = Arrays.asList(new Tag("dirty"), new Tag("af"));

        RoomTagsContainsKeywordsPredicate firstPredicate =
                new RoomTagsContainsKeywordsPredicate(firstPredicateKeywordList);
        RoomTagsContainsKeywordsPredicate secondPredicate =
                new RoomTagsContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RoomTagsContainsKeywordsPredicate firstPredicateCopy =
                new RoomTagsContainsKeywordsPredicate(firstPredicateKeywordList);
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
        RoomTagsContainsKeywordsPredicate predicate =
                new RoomTagsContainsKeywordsPredicate(Collections.singletonList(new Tag("dirty")));
        assertTrue(predicate.test(new RoomBuilder().withTags("dirty").build()));

        // Multiple keywords, 1 tag
        predicate = new RoomTagsContainsKeywordsPredicate(
                Arrays.asList(new Tag("dirty"), new Tag("af")));
        assertTrue(predicate.test(new RoomBuilder().withTags("dirty").build()));

        // 1 keyword, multiple tags
        predicate = new RoomTagsContainsKeywordsPredicate(Collections.singletonList(new Tag("dirty")));
        assertTrue(predicate.test(new RoomBuilder().withTags("dirty", "af").build()));

        // Mixed-case keywords
        predicate = new RoomTagsContainsKeywordsPredicate(
                Arrays.asList(new Tag("DiRtY"), new Tag("AF")));
        assertTrue(predicate.test(new RoomBuilder().withTags("dirty", "af").build()));
    }

    @Test
    public void test_tagsDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        RoomTagsContainsKeywordsPredicate predicate = new RoomTagsContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new RoomBuilder().withTags("dirty", "af").build()));

        // Non-matching keyword
        predicate = new RoomTagsContainsKeywordsPredicate(Arrays.asList(new Tag("smelly")));
        assertFalse(predicate.test(new RoomBuilder().withTags("dirty", "af").build()));

        // Keywords match phone, but does not match name
        predicate = new RoomTagsContainsKeywordsPredicate(Arrays.asList(new Tag("002")));
        assertFalse(predicate.test(new RoomBuilder().withRoomNumber("002").withTags("dirty", "af").build()));
    }
}
