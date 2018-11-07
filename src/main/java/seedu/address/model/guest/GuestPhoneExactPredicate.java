package seedu.address.model.guest;

import java.util.function.Predicate;

/**
 * Tests that a {@code Guest}'s {@code phoneNumber} exactly matches {@code phoneNumber} argument.
 */
public class GuestPhoneExactPredicate implements Predicate<Guest> {
    private final Phone phone;

    public GuestPhoneExactPredicate(Phone phone) {
        this.phone = phone;
    }

    @Override
    public boolean test(Guest guest) {
        return guest.getPhone().equals(phone);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GuestPhoneExactPredicate // instanceof handles nulls
                && phone.equals(((GuestPhoneExactPredicate) other).phone)); // state check
    }

    @Override
    public int hashCode() {
        return phone.hashCode();
    }
}
