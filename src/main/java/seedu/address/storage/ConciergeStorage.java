package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Concierge;
import seedu.address.model.ReadOnlyConcierge;

/**
 * Represents a storage for {@link Concierge}.
 */
public interface ConciergeStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getConciergeFilePath();

    /**
     * Returns Concierge data as a {@link ReadOnlyConcierge}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyConcierge> readConcierge() throws DataConversionException, IOException;

    /**
     * @see #getConciergeFilePath()
     */
    Optional<ReadOnlyConcierge> readConcierge(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyConcierge} to the storage.
     * @param concierge cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveConcierge(ReadOnlyConcierge concierge) throws IOException;

    /**
     * @see #saveConcierge(ReadOnlyConcierge)
     */
    void saveConcierge(ReadOnlyConcierge concierge, Path filePath) throws IOException;

}
