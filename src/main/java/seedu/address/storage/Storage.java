package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.ConciergeChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyConcierge;
import seedu.address.model.UserPrefs;
import seedu.address.model.login.PasswordHashList;

/**
 * API of the Storage component
 */
public interface Storage extends ConciergeStorage, UserPrefsStorage, PasswordsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getConciergeFilePath();

    @Override
    Optional<ReadOnlyConcierge> readConcierge() throws DataConversionException, IOException;

    @Override
    void saveConcierge(ReadOnlyConcierge concierge) throws IOException;

    @Override
    Path getPasswordsFilePath();

    @Override
    PasswordHashList getPasswordHashList() throws IOException;

    /**
     * Saves the current version of Concierge to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleConciergeChangedEvent(ConciergeChangedEvent abce);
}
