package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.ConciergeChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyConcierge;
import seedu.address.model.UserPrefs;
import seedu.address.model.login.PasswordHashList;

/**
 * Manages storage of Concierge data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ConciergeStorage conciergeStorage;
    private UserPrefsStorage userPrefsStorage;
    private PasswordsStorage passwordsStorage;


    public StorageManager(ConciergeStorage conciergeStorage,
                          UserPrefsStorage userPrefsStorage,
                          PasswordsStorage passwordsStorage) {
        super();
        this.conciergeStorage = conciergeStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.passwordsStorage = passwordsStorage;
    }
    // ================ SignIn / Password methods ==============================

    @Override
    public Path getPasswordsFilePath() {
        return passwordsStorage.getPasswordsFilePath();
    }

    @Override
    public Optional<PasswordHashList> getPasswordHashList() throws DataConversionException {
        return passwordsStorage.getPasswordHashList();
    }

    @Override
    public void savePasswordRef(PasswordHashList passwordHashList) throws IOException {
        passwordsStorage.savePasswordRef(passwordHashList);
    }

    // ================ UserPrefs methods ======================================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ Concierge methods ==============================

    @Override
    public Path getConciergeFilePath() {
        return conciergeStorage.getConciergeFilePath();
    }

    @Override
    public Optional<ReadOnlyConcierge> readConcierge() throws DataConversionException, IOException {
        return readConcierge(conciergeStorage.getConciergeFilePath());
    }

    @Override
    public Optional<ReadOnlyConcierge> readConcierge(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return conciergeStorage.readConcierge(filePath);
    }

    @Override
    public void saveConcierge(ReadOnlyConcierge concierge) throws IOException {
        saveConcierge(concierge, conciergeStorage.getConciergeFilePath());
    }

    @Override
    public void saveConcierge(ReadOnlyConcierge concierge, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        conciergeStorage.saveConcierge(concierge, filePath);
    }


    @Override
    @Subscribe
    public void handleConciergeChangedEvent(ConciergeChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveConcierge(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
