package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.model.login.PasswordHashList;

/**
 * Represents a storage for user passwords
 */
public interface PasswordsStorage {

    /**
     * Returns the file path of the passwords data file.
     */
    Path getPasswordsFilePath();

    /**
     * Retrieve the password list from storage.
     */
    PasswordHashList getPasswordHashList() throws IOException;

}
