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
import seedu.address.model.ReadOnlyGuestList;

/**
 * A class to access GuestList data stored as an xml file on the hard disk.
 */
public class XmlGuestListStorage implements GuestListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlGuestListStorage.class);

    private Path filePath;

    public XmlGuestListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getGuestListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyGuestList> readGuestList() throws DataConversionException, IOException {
        return readGuestList(filePath);
    }

    /**
     * Similar to {@link #readGuestList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyGuestList> readGuestList(Path filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("GuestList file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableGuestList xmlGuestList = XmlFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlGuestList.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveGuestList(ReadOnlyGuestList guestList) throws IOException {
        saveGuestList(guestList, filePath);
    }

    /**
     * Similar to {@link #saveGuestList(ReadOnlyGuestList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveGuestList(ReadOnlyGuestList guestList, Path filePath) throws IOException {
        requireNonNull(guestList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableGuestList(guestList));
    }

}
