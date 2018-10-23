package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyConcierge;

/**
 * A class to access Concierge data stored as an xml file on the hard disk.
 */
public class XmlConciergeStorage implements ConciergeStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlConciergeStorage.class);

    private Path filePath;

    public XmlConciergeStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getConciergeFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyConcierge> readConcierge() throws DataConversionException, IOException {
        return readConcierge(filePath);
    }

    /**
     * Similar to {@link #readConcierge()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyConcierge> readConcierge(Path filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Concierge file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableConcierge xmlConcierge = XmlFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlConcierge.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveConcierge(ReadOnlyConcierge concierge) throws IOException {
        saveConcierge(concierge, filePath);
    }

    /**
     * Similar to {@link #saveConcierge(ReadOnlyConcierge)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveConcierge(ReadOnlyConcierge concierge, Path filePath) throws IOException {
        requireNonNull(concierge);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableConcierge(concierge));
    }

}
