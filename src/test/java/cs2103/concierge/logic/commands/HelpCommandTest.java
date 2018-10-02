package cs2103.concierge.logic.commands;

import static org.junit.Assert.assertTrue;
import static cs2103.concierge.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.Rule;
import org.junit.Test;

import cs2103.concierge.commons.events.ui.ShowHelpRequestEvent;
import cs2103.concierge.logic.CommandHistory;
import cs2103.concierge.model.Model;
import cs2103.concierge.model.ModelManager;
import cs2103.concierge.ui.testutil.EventsCollectorRule;

public class HelpCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_help_success() {
        CommandTestUtil.assertCommandSuccess(new HelpCommand(), model, commandHistory, SHOWING_HELP_MESSAGE, expectedModel);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowHelpRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
