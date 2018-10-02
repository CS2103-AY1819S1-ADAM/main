package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalGuestList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.GuestList;
import seedu.address.model.ReadOnlyGuestList;

public class XmlGuestListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlGuestListStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readGuestList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readGuestList(null);
    }

    private java.util.Optional<ReadOnlyGuestList> readGuestList(String filePath) throws Exception {
        return new XmlGuestListStorage(Paths.get(filePath)).readGuestList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readGuestList("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readGuestList("NotXmlFormatGuestList.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readGuestList_invalidPersonGuestList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readGuestList("invalidPersonGuestList.xml");
    }

    @Test
    public void readGuestList_invalidAndValidPersonGuestList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readGuestList("invalidAndValidPersonGuestList.xml");
    }

    @Test
    public void readAndSaveGuestList_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve(
                "TempGuestList.xml");
        GuestList original = getTypicalGuestList();
        XmlGuestListStorage xmlGuestListStorage = new XmlGuestListStorage(filePath);

        //Save in new file and read back
        xmlGuestListStorage.saveGuestList(original, filePath);
        ReadOnlyGuestList readBack = xmlGuestListStorage.readGuestList(filePath).get();
        assertEquals(original, new GuestList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        xmlGuestListStorage.saveGuestList(original, filePath);
        readBack = xmlGuestListStorage.readGuestList(filePath).get();
        assertEquals(original, new GuestList(readBack));

        //Save and read without specifying file path
        original.addPerson(IDA);
        xmlGuestListStorage.saveGuestList(original); //file path not specified
        readBack = xmlGuestListStorage.readGuestList().get(); //file path not specified
        assertEquals(original, new GuestList(readBack));

    }

    @Test
    public void saveGuestList_nullGuestList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveGuestList(null, "SomeFile.xml");
    }

    /**
     * Saves {@code guestList} at the specified {@code filePath}.
     */
    private void saveGuestList(ReadOnlyGuestList guestList, String filePath) {
        try {
            new XmlGuestListStorage(Paths.get(filePath))
                    .saveGuestList(guestList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveGuestList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveGuestList(new GuestList(), null);
    }


}
