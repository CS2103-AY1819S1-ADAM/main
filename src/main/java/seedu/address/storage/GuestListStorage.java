package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.GuestList;
import seedu.address.model.ReadOnlyGuestList;

/**
 * Represents a storage for {@link GuestList}.
 */
public interface GuestListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getGuestListFilePath();

    /**
     * Returns GuestList data as a {@link ReadOnlyGuestList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyGuestList> readGuestList() throws DataConversionException, IOException;

    /**
     * @see #getGuestListFilePath()
     */
    Optional<ReadOnlyGuestList> readGuestList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyGuestList} to the storage.
     * @param guestList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveGuestList(ReadOnlyGuestList guestList) throws IOException;

    /**
     * @see #saveGuestList(ReadOnlyGuestList)
     */
    void saveGuestList(ReadOnlyGuestList guestList, Path filePath) throws IOException;

}
