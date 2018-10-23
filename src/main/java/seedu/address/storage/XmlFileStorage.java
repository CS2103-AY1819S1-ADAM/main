package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores concierge data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given concierge data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableConcierge concierge)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, concierge);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns concierge in the file or an empty concierge
     */
    public static XmlSerializableConcierge loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableConcierge.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
