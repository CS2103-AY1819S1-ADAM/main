package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.FLAG_ROOM;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.ui.testutil.GuiTestAssert.assertMainWindowDisplaysRoomList;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.events.ui.ListingChangedEvent;
import seedu.address.logic.LogicManager;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


/**
 * Contains tests for closing of the {@code MainWindow}.
 */
public class MainWindowGuiChangeTest extends GuiUnitTest {

    private MainWindow mainWindow;
    private Stage stage;

    @Before
    public void setUp() throws Exception {
        FxToolkit.setupStage(stage -> {
            this.stage = stage;
            mainWindow = new MainWindow(stage, new Config(), new UserPrefs(), new LogicManager(new ModelManager()));

            stage.setScene(mainWindow.getRoot().getScene());
        });
        FxToolkit.showStage();
    }

    @Test
    public void listing_listGuiChanges() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainWindow.fillInnerParts();
            }
        });

        postNow(new ListingChangedEvent(FLAG_ROOM));
        assertMainWindowDisplaysRoomList(mainWindow);

        /*
        @todo - Without Platform run later it encounters: Not on FX application thread; Even with it now, ui elements
        @todo - are not updating
        postNow(new ListingChangedEvent(FLAG_GUEST));
        assertMainWindowDisplaysGuestList(mainWindow);
        postNow(new ListingChangedEvent(FLAG_CHECKED_IN_GUEST));
        assertMainWindowDisplaysGuestList(mainWindow);*/
    }

}
