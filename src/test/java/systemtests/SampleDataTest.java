package systemtests;

import static cs2103.concierge.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

import cs2103.concierge.model.AddressBook;
import cs2103.concierge.model.person.Person;
import cs2103.concierge.model.util.SampleDataUtil;
import cs2103.concierge.testutil.TestUtil;

public class SampleDataTest extends AddressBookSystemTest {
    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */
    @Override
    protected AddressBook getInitialData() {
        return null;
    }

    /**
     * Returns a non-existent file location to force test app to load sample data.
     */
    @Override
    protected Path getDataFileLocation() {
        Path filePath = TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
        deleteFileIfExists(filePath);
        return filePath;
    }

    /**
     * Deletes the file at {@code filePath} if it exists.
     */
    private void deleteFileIfExists(Path filePath) {
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException ioe) {
            throw new AssertionError(ioe);
        }
    }

    @Test
    public void addressBook_dataFileDoesNotExist_loadSampleData() {
        Person[] expectedList = SampleDataUtil.getSamplePersons();
        assertListMatching(getPersonListPanel(), expectedList);
    }
}
