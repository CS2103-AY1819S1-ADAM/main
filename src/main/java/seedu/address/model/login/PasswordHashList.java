package seedu.address.model.login;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.PasswordHashUtil.hash;

import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Represents a key-value reference list for usernames and passwords, and is
 * used in the {@code LogInHelper}.
 */
public class PasswordHashList {

    // Name of the JsonNode variable, used to extract the key-value pairs
    private static final String PASSWORD_REF_NAME = "passwordReferenceList";

    // The JSON password reference list, also the JSON key that wraps the other key-value pairs
    private JsonNode passwordReferenceList;

    /**
     * Returns an empty password hash list with no username or passwords
     */
    public PasswordHashList() {
        this.passwordReferenceList = JsonNodeFactory.instance.objectNode();
    }

    /**
     * This method creates a new PasswordHashList using a JsonNode with a
     * {@code PASSWORD_REF_NAME} wrapper key. This facilitates the
     * serialising and storage component handled by {@code JsonUtil}.
     * @param passwordReferenceList
     */
    public PasswordHashList(JsonNode passwordReferenceList) {
        requireNonNull(passwordReferenceList);
        this.passwordReferenceList = passwordReferenceList.get(PASSWORD_REF_NAME);
    }

    /**
     * Returns a new {@code PasswordHashList} with the username and password
     * entry added. Currently used for testing, but could be extended to
     * allow users to add new accounts.
     */
    public PasswordHashList addEntry(String username, String password) {
        requireNonNull(username, password);

        // JsonNode is read-only, while ObjectNode can be written to
        ObjectNode newPasswordRef = (ObjectNode) passwordReferenceList;
        newPasswordRef.put(username, hash(password));

        // The current constructor that takes in a JsonNode object expects a
        // wrapper {@code PASSWORD_REF_NAME} key.
        PasswordHashList passwordHashList = new PasswordHashList();
        passwordHashList.passwordReferenceList = passwordReferenceList;

        return passwordHashList;
    }

    /**
     * Retrieves the password associated with the given username.
     * @param username The username which is the key
     * @return An Optional value which is empty if the username is not
     * present. Else, the optional will wrap the associated password.
     */
    protected Optional<String> getExpectedPassword(String username) {
        requireNonNull(username);

        JsonNode expectedPasswordNode = passwordReferenceList.get(username);

        if (expectedPasswordNode == null) {
            return Optional.empty();
        }

        return Optional.of(expectedPasswordNode.asText());
    }

    @Override
    public String toString() {
        return passwordReferenceList.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof PasswordHashList)) {
            return false;
        }

        return ((PasswordHashList) other).passwordReferenceList.equals(passwordReferenceList);
    }

}
