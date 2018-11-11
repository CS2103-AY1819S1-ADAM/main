package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
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
    Optional<PasswordHashList> getPasswordHashList() throws DataConversionException;

    /**
     * Saves the given {@link seedu.address.model.login.PasswordHashList} to the storage.
     * @param passwordRef cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePasswordRef(PasswordHashList passwordRef) throws IOException;

}
