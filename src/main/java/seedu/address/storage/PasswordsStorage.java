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
     * Returns password list from storage
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<PasswordHashList> readPasswordRef() throws DataConversionException, IOException;

    /**
     * Saves the given {@link seedu.address.model.login.PasswordHashList} to the storage.
     * @param passwordRef cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePasswordRef(PasswordHashList passwordRef) throws IOException;

}
