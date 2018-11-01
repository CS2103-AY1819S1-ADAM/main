package seedu.address.model.login;

/**
 * Signals that the password supplied does not match the user name.
 */
public class InvalidLogInException extends RuntimeException {

    public static final String MESSAGE_INVALID_USERNAME =
            "The username supplied is invalid.";
    public static final String MESSAGE_INVALID_PASSWORD =
            "The password does not match the username supplied.";

    public InvalidLogInException(String msg) {
        super(msg);
    }
}
