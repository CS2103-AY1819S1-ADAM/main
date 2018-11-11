package seedu.address;

import static seedu.address.model.util.SampleDataUtil.getSampleConcierge;
import static seedu.address.model.util.SampleDataUtil.getSamplePasswordHashList;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyConcierge;
import seedu.address.model.UserPrefs;
import seedu.address.model.login.PasswordHashList;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.ConciergeStorage;
import seedu.address.storage.JsonPasswordsStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.PasswordsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.XmlConciergeStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 3, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;


    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing Concierge ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        userPrefs = initPrefs(userPrefsStorage);
        ConciergeStorage conciergeStorage = new XmlConciergeStorage(userPrefs.getConciergeFilePath());
        PasswordsStorage passwordsStorage = new JsonPasswordsStorage(userPrefs.getPasswordsFilePath());
        storage = new StorageManager(conciergeStorage, userPrefsStorage, passwordsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s Concierge and {@code userPrefs}. <br>
     * The data from the sample Concierge will be used instead if {@code storage}'s Concierge is not found,
     * or an empty Concierge will be used instead if errors occur when reading {@code storage}'s Concierge.
     */
    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyConcierge> conciergeOptional;
        ReadOnlyConcierge initialData;
        PasswordHashList passwordRef = initPasswordStorage(storage);

        try {
            conciergeOptional = storage.readConcierge();
            if (!conciergeOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample Concierge");
            }
            initialData =
                    conciergeOptional.orElseGet(SampleDataUtil::getSampleConcierge);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. "
                    + "Will be starting with a sample Concierge");
            initialData = getSampleConcierge();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. "
                    + "Will be starting with a sample Concierge");
            initialData = getSampleConcierge();
        }

        return new ModelManager(initialData, userPrefs, passwordRef);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code PasswordHashList} using the storage data.
     * If there is an invalid or missing passwords.json file, then an empty
     * one with the default data will be created.
     */
    protected PasswordHashList initPasswordStorage(Storage storage) {
        PasswordHashList passwords;

        try {
            Optional<PasswordHashList> passwordsOptional =
                    storage.getPasswordHashList();
            if (!passwordsOptional.isPresent()) {
                logger.info("Password file not found. "
                        + "Will be starting with a sample login account.");
            }

            passwords =
                    passwordsOptional.orElseGet(SampleDataUtil::getSamplePasswordHashList);
        } catch (DataConversionException e) {
            logger.warning("Problem while reading from the password file. "
                    + "Will be starting with a sample login account.");
            passwords = getSamplePasswordHashList();
        }

        // Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.savePasswordRef(passwords);
        } catch (IOException e) {
            logger.warning("Failed to save password file : " + StringUtil.getDetails(e));
        }

        return passwords;
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Concierge");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    private void initEventsCenter() {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting Concierge " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Concierge ] =============================");
        ui.stop();
        try {
            storage.saveUserPrefs(userPrefs);
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
        Platform.exit();
        System.exit(0);
    }

    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
