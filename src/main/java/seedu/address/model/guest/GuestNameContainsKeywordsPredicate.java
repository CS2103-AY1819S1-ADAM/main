package seedu.address.model.guest;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Guest}'s {@code Name} matches any of the keywords given.
 */
public class GuestNameContainsKeywordsPredicate implements Predicate<Guest> {
    private final List<Name> names;

    public GuestNameContainsKeywordsPredicate(List<Name> names) {
        this.names = names;
    }

    @Override
    public boolean test(Guest guest) {
        return names.stream()
                .anyMatch(name -> StringUtil.containsWordIgnoreCase(guest.getName().fullName, name.fullName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GuestNameContainsKeywordsPredicate // instanceof handles nulls
                && names.equals(((GuestNameContainsKeywordsPredicate) other).names)); // state check
    }

    @Override
    public int hashCode() {
        return names.hashCode();
    }
}
