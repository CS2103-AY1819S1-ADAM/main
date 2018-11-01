package seedu.address.model.guest;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Guest}'s {@code Name} matches any of the keywords given.
 */
public class GuestNameContainsKeywordsPredicate implements Predicate<Guest> {
    private final List<String> keywords;

    public GuestNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Guest guest) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(guest.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GuestNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((GuestNameContainsKeywordsPredicate) other).keywords)); // state check
    }

    @Override
    public int hashCode() {
        return keywords.hashCode();
    }
}
