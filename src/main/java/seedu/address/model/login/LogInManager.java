package seedu.address.model.login;

import static seedu.address.model.login.InvalidLogInException.MESSAGE_INVALID_PASSWORD;
import static seedu.address.model.login.InvalidLogInException.MESSAGE_INVALID_USERNAME;

import java.util.Optional;

import seedu.address.logic.commands.LogInCommand;

/**
 * A helper class to manage the login process.
 */
public class LogInManager {

    // The username currently associated with the model
    private Optional<String> username = Optional.empty();
    private final PasswordHashList passwordRef;

    /**
     * Creates an {@code LogInHelper} with an empty password reference list.
     */
    public LogInManager() {
        this.passwordRef = PasswordHashList.getEmptyPasswordHashList();
    }

    public LogInManager(PasswordHashList passwordRef) {
        this.passwordRef = passwordRef;
    }

    /**
     * Checks if the current {@code LogInHelper} is signed in.
     */
    public boolean isSignedIn() {
        return this.username.isPresent();
    }

    /**
     * Returns an Optional of the username associated with the session.
     */
    public Optional<String> getUsername() {
        return this.username;
    }

    /**
     * Attempts to sign in with the given {@code username} and the
     * {@code hashed password}.
     *
     * @throws InvalidLogInException Thrown when the username is not present,
     * or the password does not match the username.
     */
    public void signIn(String username, String hashedPassword) throws InvalidLogInException {
        if (isSignedIn()) {
            throw new InvalidLogInException(LogInCommand.MESSAGE_SIGNED_IN_ALREADY);
        }

        Optional<String> expectedPassword = passwordRef.getExpectedPassword(username);

        if (!expectedPassword.isPresent()) {
            throw new InvalidLogInException(MESSAGE_INVALID_USERNAME);
        } else if (!hashedPassword.equalsIgnoreCase(expectedPassword.get())) {
            throw new InvalidLogInException(MESSAGE_INVALID_PASSWORD);
        }

        this.username = Optional.of(username);
    }

    /**
     * Attempts to sign out of Concierge.
     *
     * @throws InvalidLogOutException if Concierge is not signed in.
     */
    public void signOut() throws InvalidLogOutException {
        if (!this.isSignedIn()) {
            throw new InvalidLogOutException();
        }

        this.username = Optional.empty();
    }
}
