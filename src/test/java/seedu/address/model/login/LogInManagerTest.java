package seedu.address.model.login;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.login.InvalidLogInException.MESSAGE_INVALID_PASSWORD;
import static seedu.address.model.login.InvalidLogInException.MESSAGE_INVALID_USERNAME;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.Test;

public class LogInManagerTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LogInManager(null));
    }

    @Test
    public void isSignedIn_success() throws Exception {

        // new empty LogInManager -> not signed in
        LogInManager logInManager = new LogInManager();
        assertFalse(logInManager.isSignedIn());

        // new non-empty LogInManager -> not signed in
        PasswordHashList passwordRef = PasswordHashListTest.getSamplePasswordHashList();
        logInManager = new LogInManager(passwordRef);
        assertFalse(logInManager.isSignedIn());

        // after failed sign-in attempt -> not signed in
        assertThrows(InvalidLogInException.class, () -> new LogInManager(passwordRef).signIn(
                "user1", "wrongpassword"));
        assertFalse(logInManager.isSignedIn());

        // after correct sign-in attempt -> signed in
        logInManager.signIn("user1", "passw0rd");
        assertTrue(logInManager.isSignedIn());

        // after sign out -> not signed in
        logInManager.signOut();
        assertFalse(logInManager.isSignedIn());
    }

    @Test
    public void signIn_incorrectPasswordCase_success() throws Exception {
        PasswordHashList passwordRef = PasswordHashListTest.getSamplePasswordHashList();
        LogInManager logInManager = new LogInManager(passwordRef);

        // (hashed) password is of a different case -> accepted
        // note that two case-sensitive passwords would produce different hashes
        logInManager.signIn("user1", "PASSw0rd");
        assertTrue(logInManager.isSignedIn());
    }

    @Test
    public void signIn_invalidUsername_throwsInvalidLogInException() throws Exception {
        PasswordHashList passwordRef = PasswordHashListTest.getSamplePasswordHashList();
        LogInManager logInManager = new LogInManager(passwordRef);

        // username not in reference list
        assertThrows(InvalidLogInException.class,
                MESSAGE_INVALID_USERNAME, () -> logInManager.signIn("incorrectuser", "dummy"));

        // username in reference list, but of a different case
        assertThrows(InvalidLogInException.class,
                MESSAGE_INVALID_USERNAME, () -> logInManager.signIn("USER1", "dummy"));
    }

    @Test
    public void signIn_invalidPassword_throwsInvalidLogInException() throws Exception {
        PasswordHashList passwordRef = PasswordHashListTest.getSamplePasswordHashList();
        LogInManager logInManager = new LogInManager(passwordRef);

        // hashed password does not match at all
        assertThrows(InvalidLogInException.class,
                MESSAGE_INVALID_PASSWORD, () -> logInManager.signIn("user1", "incorrectpassword"));
    }

    @Test
    public void signOut_notSignedIn_throwsInvalidLogOutException() {
        LogInManager logInManager = new LogInManager();
        assertThrows(InvalidLogOutException.class, () -> logInManager.signOut());
    }
}
