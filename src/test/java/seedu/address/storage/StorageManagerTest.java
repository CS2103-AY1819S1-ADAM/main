package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalConcierge.getTypicalConciergeClean;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.events.model.ConciergeChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.Concierge;
import seedu.address.model.ReadOnlyConcierge;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlConciergeStorage conciergeStorage = new XmlConciergeStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonPasswordsStorage passwordsStorage = new JsonPasswordsStorage(getTempFilePath("pws"));
        storageManager = new StorageManager(conciergeStorage, userPrefsStorage, passwordsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.getRoot().toPath().resolve(fileName);
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void conciergeReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlConciergeStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlConciergeStorageTest} class.
         */
        Concierge original = getTypicalConciergeClean();
        storageManager.saveConcierge(original);
        ReadOnlyConcierge retrieved = storageManager.readConcierge().get();
        assertEquals(original, new Concierge(retrieved));
    }

    @Test
    public void getConciergeFilePath() {
        assertNotNull(storageManager.getConciergeFilePath());
    }

    @Test
    public void handleConciergeChangedEvent_exceptionThrown_eventRaised() {
        Path dummyPath = Paths.get("dummy");
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(
                new XmlConciergeStorageExceptionThrowingStub(dummyPath),
                new JsonUserPrefsStorage(dummyPath),
                new JsonPasswordsStorage(dummyPath));
        storage.handleConciergeChangedEvent(new ConciergeChangedEvent(new Concierge()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlConciergeStorageExceptionThrowingStub extends XmlConciergeStorage {

        public XmlConciergeStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveConcierge(ReadOnlyConcierge concierge, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
