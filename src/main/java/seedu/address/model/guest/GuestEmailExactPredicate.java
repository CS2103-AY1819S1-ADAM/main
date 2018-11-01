package seedu.address.model.guest;

import java.util.function.Predicate;

/**
 * Tests that a {@code Guest}'s {@code email} exactly matches {@code email} argument.
 */
public class GuestEmailExactPredicate implements Predicate<Guest> {
    private final String email;

    public GuestEmailExactPredicate(String email) {
        this.email = email;
    }

    @Override
    public boolean test(Guest guest) {
        return guest.getEmail().toString().equals(email);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GuestEmailExactPredicate // instanceof handles nulls
                && email.equals(((GuestEmailExactPredicate) other).email)); // state check
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
