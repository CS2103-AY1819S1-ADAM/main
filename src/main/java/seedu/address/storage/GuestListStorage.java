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
    Path getAddressBookFilePath();

    /**
     * Returns GuestList data as a {@link ReadOnlyGuestList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyGuestList> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyGuestList> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyGuestList} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyGuestList addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyGuestList)
     */
    void saveAddressBook(ReadOnlyGuestList addressBook, Path filePath) throws IOException;

}
