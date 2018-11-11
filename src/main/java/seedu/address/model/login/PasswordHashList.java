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
    private static final String PASSWORD_REF_NAME = "passwordRef";

    // The JSON password reference list, also the JSON key that wraps the other key-value pairs
    private JsonNode passwordRef;

    /**
     * Returns an empty password hash list with no username or passwords
     */
    public PasswordHashList() {
        this.passwordRef = JsonNodeFactory.instance.objectNode();
    }

    /**
     * This method creates a new PasswordHashList using a JsonNode with a
     * {@code PASSWORD_REF_NAME} wrapper key. This facilitates the
     * serialising and storage component handled by {@code JsonUtil}.
     * @param passwordRef
     */
    public PasswordHashList(JsonNode passwordRef) {
        requireNonNull(passwordRef);
        this.passwordRef = passwordRef.get(PASSWORD_REF_NAME);
    }

    /**
     * Returns a new {@code PasswordHashList} with the username and password
     * entry added. Currently used for testing, but could be extended to
     * allow users to add new accounts.
     */
    public PasswordHashList addEntry(String username, String password) {
        requireNonNull(username, password);

        // JsonNode is read-only, while ObjectNode can be written to
        ObjectNode newPasswordRef = (ObjectNode) passwordRef;
        newPasswordRef.put(username, hash(password));

        // The current constructor that takes in a JsonNode object expects a
        // wrapper {@code PASSWORD_REF_NAME} key.
        PasswordHashList passwordHashList = new PasswordHashList();
        passwordHashList.passwordRef = passwordRef;

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

        JsonNode expectedPasswordNode = passwordRef.get(username);

        if (expectedPasswordNode == null) {
            return Optional.empty();
        }

        return Optional.of(expectedPasswordNode.asText());
    }

    @Override
    public String toString() {
        return passwordRef.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof PasswordHashList)) {
            return false;
        }

        return ((PasswordHashList) other).passwordRef.equals(passwordRef);
    }

}
