package seedu.address.logic;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.autocomplete.AutoCompleteManager;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.UnauthorisedCommandException;
import seedu.address.logic.parser.ConciergeParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.guest.Guest;
import seedu.address.model.room.Room;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final ConciergeParser conciergeParser;

    private final AutoCompleteManager autoCompleteManager;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        conciergeParser = new ConciergeParser();
        autoCompleteManager = new AutoCompleteManager();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = conciergeParser.parseCommand(commandText);
            if (command.requiresSignIn() && !model.isSignedIn()) {
                throw new UnauthorisedCommandException();
            }
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Guest> getFilteredGuestList() {
        return model.getFilteredGuestList();
    }

    @Override
    public ObservableList<Guest> getFilteredCheckedInGuestList() {
        return model.getFilteredCheckedInGuestList();
    }

    @Override
    public ObservableList<Room> getFilteredRoomList() {
        return model.getFilteredRoomList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }

    @Override
    public List<String> getAutoCompleteCommands(String commandPrefix) {
        return autoCompleteManager.getAutoCompleteCommands(commandPrefix);
    }

    @Override
    public String getAutoCompleteNextParameter(String inputText) {
        return autoCompleteManager.getAutoCompleteNextMissingParameter(inputText);
    }
}
