package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private GuestListPanel guestListPanel;
    private RoomListPanel roomListPanel;
    private GuestDetailedPanel guestDetailedPanel;
    private RoomDetailedPanel roomDetailedPanel;
    private Config config;
    private UserPrefs prefs;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane guestListPanelPlaceholder;

    @FXML
    private StackPane roomListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private VBox guestListBox;

    @FXML
    private VBox roomListBox;

    @FXML
    private StackPane guestDetailedPanelPlaceholder;

    @FXML
    private StackPane roomDetailedPanelPlaceholder;

    @FXML
    private VBox guestDetailedBox;

    @FXML
    private VBox roomDetailedBox;


    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;

        // Configure the UI
        setTitle(config.getAppTitle());
        setWindowDefaultSize(prefs);

        setAccelerators();
        registerAsAnEventHandler(this);

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     * Initial state only displays guest list and detailed guest panel.
     */
    void fillInnerParts() {
        guestListPanel = new GuestListPanel(logic.getFilteredGuestList());
        guestListPanelPlaceholder.getChildren().add(guestListPanel.getRoot());

        roomListPanel = new RoomListPanel(logic.getFilteredRoomList());
        roomListPanelPlaceholder.getChildren().add(roomListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getConciergeFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        guestDetailedPanel = new GuestDetailedPanel();
        guestDetailedPanelPlaceholder.getChildren().add(guestDetailedPanel.getRoot());

        roomDetailedPanel = new RoomDetailedPanel();
        roomDetailedPanelPlaceholder.getChildren().add(roomDetailedPanel.getRoot());

        this.showRoomList();
        this.showRoomDetailedPanel();
    }

    void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the default size based on user preferences.
     */
    private void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    /**
     * UI Visibility Function - Enables guest-related list, disables room-related list UI elements
     */
    public void showGuestList() {
        roomListBox.setDisable(true);
        roomListBox.setVisible(false);
        guestListBox.setDisable(false);
        guestListBox.setVisible(true);
    }

    /**
     * UI Visibility Functions - Enables room-related list, disables guest-related list UI elements
     */
    public void showRoomList() {
        roomListBox.setDisable(false);
        roomListBox.setVisible(true);
        guestListBox.setDisable(true);
        guestListBox.setVisible(false);
    }

    /**
     * UI Visibility Functions -  Enables guest-related detailed list, disables room-related detailed list UI elements
     */
    public void showGuestDetailedPanel() {
        guestDetailedBox.setDisable(false);
        guestDetailedBox.setVisible(true);
        roomDetailedBox.setDisable(true);
        roomDetailedBox.setVisible(false);
    }

    /**
     * UI Visibility Functions - Enables room-related detailed list, disables guest-related detailed list UI elements
     */
    public void showRoomDetailedPanel() {
        guestDetailedBox.setDisable(true);
        guestDetailedBox.setVisible(false);
        roomDetailedBox.setDisable(false);
        roomDetailedBox.setVisible(true);
    }

    /**
     * Sets the observable list of the guest list panel to be the list of checked-in guests
     */
    public void setGuestListPanelDisplayCheckedInGuestList() {
        guestListPanel = new GuestListPanel(logic.getFilteredCheckedInGuestList());
        guestListPanelPlaceholder.getChildren().add(guestListPanel.getRoot());
    }

    /**
     * Sets the observable list of the guest list panel to be the list of archived guests
     */
    public void setGuestListPanelDisplayGuestList() {
        guestListPanel = new GuestListPanel(logic.getFilteredGuestList());
        guestListPanelPlaceholder.getChildren().add(guestListPanel.getRoot());
    }

    public GuestListPanel getGuestListPanel() {
        return guestListPanel;
    }

    public boolean isGuestListVisible() {
        return guestListBox.isVisible();
    }

    public boolean isRoomListVisible() {
        return roomListBox.isVisible();
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    /**
     * Clears the guest detailed panel
     */
    public void clearGuestSelection() {
        guestDetailedPanel.clearSelection();
    }

    /**
     * Clears the room detailed panel
     */
    public void clearRoomSelection() {
        roomDetailedPanel.clearSelection();
    }
}
