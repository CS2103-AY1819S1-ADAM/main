package seedu.address.model.login;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.PasswordHashUtil.hash;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;

import org.junit.Test;

import javafx.util.Pair;
import seedu.address.model.util.SampleDataUtil;

public class PasswordHashListTest {

    public static PasswordHashList getSamplePasswordHashList() {
        Pair<String, String> firstUser = new Pair("user1", "passw0rd");
        Pair<String, String> secondUser = new Pair("USER2", "passw1rd");
        return SampleDataUtil.getPasswordHashList(firstUser, secondUser);
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PasswordHashList(null));
    }

    // TODO: Add support to assert the success of parsing
    /*
    @Test
    public void constructor_validString_success() throws IOException {

        // empty json string
        String passwordRef = " { } "
        new PasswordHashList(passwordRef);

        // one entry
        passwordRef = "{ \"user\" : \"passw0rd\" }";
        new PasswordHashList(passwordRef);

        // multiple entries
        getSamplePasswordHashList();
    }
    */

    @Test
    public void getExpectedPassword_success() throws IOException {
        PasswordHashList passwordRef = getSamplePasswordHashList();

        // correct username, first user
        String retrievedPassword = passwordRef.getExpectedPassword("user1").get();
        assertEquals(retrievedPassword, hash("passw0rd"));

        // correct username, second user
        retrievedPassword = passwordRef.getExpectedPassword("USER2").get();
        assertEquals(retrievedPassword, hash("passw1rd"));
    }


    @Test
    public void getExpectedPassword_invalidUsername() throws IOException {
        PasswordHashList passwordRef = getSamplePasswordHashList();

        // null username -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> passwordRef.getExpectedPassword(null));

        // incorrect username -> returns empty optional
        assertFalse(passwordRef.getExpectedPassword("user0").isPresent());

        // incorrect username case -> returns empty optional
        assertFalse(passwordRef.getExpectedPassword("USER1").isPresent());

        // whitespace in preamble of username -> returns empty optional
        assertFalse(passwordRef.getExpectedPassword(" user1").isPresent());
    }

    @Test
    public void toString_success() throws IOException {

        // empty
        PasswordHashList passwordRef = new PasswordHashList();
        assertEquals(passwordRef.toString(), "Number of users: 0");

        // multiple entries
        passwordRef = getSamplePasswordHashList();
        assertEquals(passwordRef.toString(), "Number of users: 2");
    }

    @Test
    public void equals() {
        PasswordHashList passwordRef = getSamplePasswordHashList();

        // null -> returns false
        assertFalse(passwordRef.equals(null));

        // same object -> returns true
        assertTrue(passwordRef.equals(passwordRef));

        // same values -> returns true
        assertTrue(passwordRef.equals(new PasswordHashList()
                .addEntry("user1", "passw0rd")
                .addEntry("USER2", "passw1rd")));

        // different json nodes -> returns false
        assertFalse(passwordRef.equals(new PasswordHashList()));
    }

}
