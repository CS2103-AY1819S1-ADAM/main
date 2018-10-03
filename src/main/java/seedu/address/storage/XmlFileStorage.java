package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores guestlist data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given guestlist data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableGuestList guestList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, guestList);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns guest list in the file or an empty guest list
     */
    public static XmlSerializableGuestList loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableGuestList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
