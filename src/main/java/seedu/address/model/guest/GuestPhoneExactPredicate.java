package seedu.address.model.guest;

import java.util.function.Predicate;

/**
 * Tests that a {@code Guest}'s {@code phoneNumber} exactly matches {@code phoneNumber} argument.
 */
public class GuestPhoneExactPredicate implements Predicate<Guest> {
    private final String phoneNumber;

    public GuestPhoneExactPredicate(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean test(Guest guest) {
        return guest.getPhone().toString().equals(phoneNumber);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GuestPhoneExactPredicate // instanceof handles nulls
                && phoneNumber.equals(((GuestPhoneExactPredicate) other).phoneNumber)); // state check
    }

    @Override
    public int hashCode() {
        return phoneNumber.hashCode();
    }
}
