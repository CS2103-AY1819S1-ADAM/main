package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalConcierge.getTypicalConcierge;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Concierge;
import seedu.address.model.ReadOnlyConcierge;

public class XmlConciergeStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlConciergeStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readConcierge_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readConcierge(null);
    }

    private java.util.Optional<ReadOnlyConcierge> readConcierge(String filePath) throws Exception {
        return new XmlConciergeStorage(Paths.get(filePath)).readConcierge(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readConcierge("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readConcierge("NotXmlFormatConcierge.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readConcierge_invalidPersonConcierge_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readConcierge("invalidPersonConcierge.xml");
    }

    @Test
    public void readConcierge_invalidAndValidPersonConcierge_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readConcierge("invalidAndValidPersonConcierge.xml");
    }

    @Test
    public void readAndSaveConcierge_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempConcierge.xml");
        Concierge original = getTypicalConcierge();
        XmlConciergeStorage xmlConciergeStorage = new XmlConciergeStorage(filePath);

        //Save in new file and read back
        xmlConciergeStorage.saveConcierge(original, filePath);
        ReadOnlyConcierge readBack = xmlConciergeStorage.readConcierge(filePath).get();
        assertEquals(original, new Concierge(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        xmlConciergeStorage.saveConcierge(original, filePath);
        readBack = xmlConciergeStorage.readConcierge(filePath).get();
        assertEquals(original, new Concierge(readBack));

        //Save and read without specifying file path
        original.addPerson(IDA);
        xmlConciergeStorage.saveConcierge(original); //file path not specified
        readBack = xmlConciergeStorage.readConcierge().get(); //file path not specified
        assertEquals(original, new Concierge(readBack));

    }

    @Test
    public void saveConcierge_nullConcierge_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveConcierge(null, "SomeFile.xml");
    }

    /**
     * Saves {@code concierge} at the specified {@code filePath}.
     */
    private void saveConcierge(ReadOnlyConcierge concierge, String filePath) {
        try {
            new XmlConciergeStorage(Paths.get(filePath))
                    .saveConcierge(concierge, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveConcierge_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveConcierge(new Concierge(), null);
    }


}
