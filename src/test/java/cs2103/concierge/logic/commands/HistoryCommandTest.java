package cs2103.concierge.logic.commands;

import org.junit.Test;

import cs2103.concierge.logic.CommandHistory;
import cs2103.concierge.model.Model;
import cs2103.concierge.model.ModelManager;

public class HistoryCommandTest {
    private CommandHistory history = new CommandHistory();
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute() {
        CommandTestUtil.assertCommandSuccess(new HistoryCommand(), model, history, HistoryCommand.MESSAGE_NO_HISTORY, expectedModel);

        String command1 = "clear";
        history.add(command1);
        CommandTestUtil.assertCommandSuccess(new HistoryCommand(), model, history,
                String.format(HistoryCommand.MESSAGE_SUCCESS, command1), expectedModel);

        String command2 = "randomCommand";
        String command3 = "select 1";
        history.add(command2);
        history.add(command3);

        String expectedMessage = String.format(HistoryCommand.MESSAGE_SUCCESS,
                String.join("\n", command3, command2, command1));
        CommandTestUtil.assertCommandSuccess(new HistoryCommand(), model, history, expectedMessage, expectedModel);
    }

}
