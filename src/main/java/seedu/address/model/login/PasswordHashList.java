package seedu.address.model.login;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import seedu.address.commons.util.JsonUtil;

/**
 * Represents a password hash list, and is used in the {@code LogInHelper}.
 */
public class PasswordHashList {

    private JsonNode passwordRef; // The JSON password reference list

    public PasswordHashList(String passwordRefString) throws IOException {
        requireNonNull(passwordRefString);

        try {
            this.passwordRef = JsonUtil.getNodeObject(passwordRefString);
        } catch (IOException e) {
            // Caught and rethrown to wrap subclass exception com.fasterxml.jackson.core.JsonParseException
            // as JsonParseException.isAssignableFrom(IOException) returns false in {@code Assert.assertThrows}
            throw new IOException(e.getMessage());
        }
    }

    private PasswordHashList(JsonNode passwordRef) {
        requireNonNull(passwordRef);

        this.passwordRef = passwordRef;
    }

    /**
     * @return An empty password hash list with no username or passwords
     */
    public static PasswordHashList getEmptyPasswordHashList() {
        return new PasswordHashList(JsonNodeFactory.instance.objectNode());
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
        return "Number of users: " + passwordRef.size();
    }

}
