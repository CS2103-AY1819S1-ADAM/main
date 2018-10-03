package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.GuestListChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyGuestList;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of GuestList data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private GuestListStorage guestListStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(GuestListStorage guestListStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.guestListStorage = guestListStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

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


    // ================ GuestList methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return guestListStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyGuestList> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(guestListStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyGuestList> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return guestListStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyGuestList addressBook) throws IOException {
        saveAddressBook(addressBook, guestListStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyGuestList addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        guestListStorage.saveAddressBook(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(GuestListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
