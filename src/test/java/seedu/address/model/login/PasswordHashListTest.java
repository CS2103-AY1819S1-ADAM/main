package seedu.address.model.login;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.HASHED_PASSWORD_1;
import static seedu.address.logic.commands.CommandTestUtil.HASHED_PASSWORD_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_2;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.Test;

import javafx.util.Pair;
import seedu.address.model.util.SampleDataUtil;

public class PasswordHashListTest {

    public static PasswordHashList getSamplePasswordHashList() {
        Pair<String, String> firstUser = new Pair(VALID_USERNAME_1, VALID_PASSWORD_1);
        Pair<String, String> secondUser = new Pair(VALID_USERNAME_2, VALID_PASSWORD_2);
        return SampleDataUtil.getPasswordHashList(firstUser, secondUser);
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PasswordHashList(null));
    }

    @Test
    public void getExpectedPassword_success() {
        PasswordHashList passwordRef = getSamplePasswordHashList();

        // correct username, first user
        String retrievedPassword = passwordRef.getExpectedPassword(VALID_USERNAME_1).get();
        assertEquals(retrievedPassword, HASHED_PASSWORD_1);

        // correct username, second user
        retrievedPassword = passwordRef.getExpectedPassword(VALID_USERNAME_2).get();
        assertEquals(retrievedPassword, HASHED_PASSWORD_2);
    }


    @Test
    public void getExpectedPassword_invalidUsername() {
        PasswordHashList passwordRef = getSamplePasswordHashList();

        // null username -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> passwordRef.getExpectedPassword(null));

        // incorrect username -> returns empty optional
        assertFalse(passwordRef.getExpectedPassword("user0").isPresent());

        // incorrect username case -> returns empty optional
        assertFalse(passwordRef.getExpectedPassword(VALID_USERNAME_1.toUpperCase()).isPresent());

        // whitespace in preamble of username -> returns empty optional
        assertFalse(passwordRef.getExpectedPassword(" " + VALID_USERNAME_1).isPresent());
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
                .addEntry(VALID_USERNAME_1, VALID_PASSWORD_1)
                .addEntry(VALID_USERNAME_2, VALID_PASSWORD_2)));

        // different json nodes -> returns false
        assertFalse(passwordRef.equals(new PasswordHashList()));
    }

}
