package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.Concierge;
import seedu.address.storage.XmlAdaptedGuest;
import seedu.address.storage.XmlAdaptedTag;
import seedu.address.storage.XmlSerializableConcierge;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalConcierge;

public class XmlUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validConcierge.xml");
    private static final Path MISSING_GUEST_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingGuestField.xml");
    private static final Path INVALID_GUEST_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidGuestField.xml");
    private static final Path VALID_GUEST_FILE = TEST_DATA_FOLDER.resolve("validGuest.xml");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempConcierge.xml");

    private static final String INVALID_PHONE = "9482asf424";

    private static final String VALID_NAME = "Hans Muster";
    private static final String VALID_PHONE = "9482424";
    private static final String VALID_EMAIL = "hans@example";
    private static final String VALID_ADDRESS = "4th street";
    private static final List<XmlAdaptedTag> VALID_TAGS = Collections.singletonList(new XmlAdaptedTag("friends"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, Concierge.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, Concierge.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, Concierge.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        Concierge dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableConcierge.class).toModelType();
        assertEquals(7, dataFromFile.getGuestList().size());
    }

    @Test
    public void xmlAdaptedGuestFromFile_fileWithMissingGuestField_validResult() throws Exception {
        XmlAdaptedGuest actualGuest = XmlUtil.getDataFromFile(
                MISSING_GUEST_FIELD_FILE, XmlAdaptedGuestWithRootElement.class);
        XmlAdaptedGuest expectedGuest = new XmlAdaptedGuest(
                null, VALID_PHONE, VALID_EMAIL, VALID_TAGS);
        assertEquals(expectedGuest, actualGuest);
    }

    @Test
    public void xmlAdaptedGuestFromFile_fileWithInvalidGuestField_validResult() throws Exception {
        XmlAdaptedGuest actualGuest = XmlUtil.getDataFromFile(
                INVALID_GUEST_FIELD_FILE, XmlAdaptedGuestWithRootElement.class);
        XmlAdaptedGuest expectedGuest = new XmlAdaptedGuest(
                VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_TAGS);
        assertEquals(expectedGuest, actualGuest);
    }

    @Test
    public void xmlAdaptedGuestFromFile_fileWithValidGuest_validResult() throws Exception {
        XmlAdaptedGuest actualGuest = XmlUtil.getDataFromFile(
                VALID_GUEST_FILE, XmlAdaptedGuestWithRootElement.class);
        XmlAdaptedGuest expectedGuest = new XmlAdaptedGuest(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_TAGS);
        assertEquals(expectedGuest, actualGuest);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new Concierge());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new Concierge());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_FILE);
        XmlSerializableConcierge dataToWrite = new XmlSerializableConcierge(new Concierge());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableConcierge dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableConcierge.class);
        assertEquals(dataToWrite, dataFromFile);

        dataToWrite = new XmlSerializableConcierge(TypicalConcierge.getTypicalConcierge());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableConcierge.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedGuest}
     * objects.
     */
    @XmlRootElement(name = "guests")
    private static class XmlAdaptedGuestWithRootElement extends XmlAdaptedGuest {}
}
