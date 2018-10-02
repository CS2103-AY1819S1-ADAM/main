package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.GuestListChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyGuestList;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends GuestListStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getGuestListFilePath();

    @Override
    Optional<ReadOnlyGuestList> readGuestList() throws DataConversionException, IOException;

    @Override
    void saveGuestList(ReadOnlyGuestList guestList) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(GuestListChangedEvent abce);
}
