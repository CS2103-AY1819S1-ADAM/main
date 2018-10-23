package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.ConciergeChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyConcierge;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ConciergeStorage, UserPrefsStorage {

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

    /**
     * Saves the current version of the Concierge to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleConciergeChangedEvent(ConciergeChangedEvent cce);
}
