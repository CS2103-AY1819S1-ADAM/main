package seedu.address.model.guest;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Guest in Concierge.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Guest implements Comparable<Guest> {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Guest(Name name, Phone phone, Email email, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.tags.addAll(tags);
    }

    public Guest(Guest toBeCopied) {
        this.name = new Name(toBeCopied.getName().toString());
        this.phone = new Phone(toBeCopied.getPhone().toString());
        this.email = new Email(toBeCopied.getEmail().toString());
        this.tags.addAll(toBeCopied.getTags());
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both guests of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two guests.
     */
    public boolean isSameGuest(Guest otherGuest) {
        if (otherGuest == this) {
            return true;
        }

        return otherGuest != null
                && otherGuest.getName().equals(getName())
                && (otherGuest.getPhone().equals(getPhone()) || otherGuest.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both guests have the same identity and data fields.
     * This defines a stronger notion of equality between two guests.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Guest)) {
            return false;
        }

        Guest otherGuest = (Guest) other;
        return otherGuest.getName().equals(getName())
                && otherGuest.getPhone().equals(getPhone())
                && otherGuest.getEmail().equals(getEmail())
                && otherGuest.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" | Phone: ")
                .append(getPhone())
                .append(" | Email: ")
                .append(getEmail());
        return builder.toString();
    }

    @Override
    public int compareTo(Guest other) {
        int result = name.toString().compareTo(other.name.toString());
        if (result != 0) {
            return result;
        }
        result = phone.toString().compareTo(other.phone.toString());
        if (result != 0) {
            return result;
        }
        return email.toString().compareTo(other.email.toString());
    }

}
