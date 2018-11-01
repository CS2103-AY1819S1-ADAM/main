package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.Concierge;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyConcierge;
import seedu.address.model.UserPrefs;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.XmlSerializableConcierge;
import seedu.address.testutil.TestUtil;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final Path CONCIERGE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("sampleData.xml");
    public static final Path PASSWORD_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pass_testing.json");

    public static final String APP_TITLE = "Test App";

    protected static final Path DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected Supplier<ReadOnlyConcierge> initialDataSupplier = () -> null;
    protected Path conciergeFileLocation = CONCIERGE_LOCATION_FOR_TESTING;
    protected Path passwordFileLocation = PASSWORD_LOCATION_FOR_TESTING;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyConcierge> initialDataSupplier, Path conciergeFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.conciergeFileLocation = conciergeFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            createDataFileWithData(new XmlSerializableConcierge(this.initialDataSupplier.get()),
                    this.conciergeFileLocation);
        }
    }

    @Override
    protected Config initConfig(Path configFilePath) {
        Config config = super.initConfig(configFilePath);
        config.setAppTitle(APP_TITLE);
        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        return config;
    }

    @Override
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        UserPrefs userPrefs = super.initPrefs(storage);
        double x = Screen.getPrimary().getVisualBounds().getMinX();
        double y = Screen.getPrimary().getVisualBounds().getMinY();
        userPrefs.updateLastUsedGuiSetting(new GuiSettings(600.0, 600.0, (int) x, (int) y));
        userPrefs.setConciergeFilePath(conciergeFileLocation);
        userPrefs.setPasswordsFilePath(passwordFileLocation);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of Concierge data stored inside the storage file.
     */
    public Concierge readStorageConcierge() {
        try {
            return new Concierge(storage.readConcierge().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the Concierge format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns the file path of the storage file.
     */
    public Path getStorageSaveLocation() {
        return storage.getConciergeFilePath();
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        Model copy = new ModelManager((model.getConcierge()), new UserPrefs());
        ModelHelper.setFilteredGuestList(copy, model.getFilteredGuestList());
        ModelHelper.setFilteredRoomList(copy, model.getFilteredRoomList());
        return copy;
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creates an XML file at the {@code filePath} with the {@code data}.
     */
    private <T> void createDataFileWithData(T data, Path filePath) {
        try {
            FileUtil.createIfMissing(filePath);
            XmlUtil.saveDataToFile(filePath, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
