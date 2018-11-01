package seedu.address.model.login;

/**
 * Represents an invalid logout attempt.
 */
public class InvalidLogOutException extends Exception {

    public InvalidLogOutException() {
        super("You are not logged in and hence cannot logout.");
    }

}
