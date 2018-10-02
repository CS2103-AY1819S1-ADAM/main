package cs2103.concierge.logic;

import java.util.logging.Logger;

import cs2103.concierge.commons.core.ComponentManager;
import cs2103.concierge.commons.core.LogsCenter;
import cs2103.concierge.logic.commands.Command;
import cs2103.concierge.logic.commands.CommandResult;
import cs2103.concierge.logic.commands.exceptions.CommandException;
import cs2103.concierge.logic.parser.AddressBookParser;
import cs2103.concierge.logic.parser.exceptions.ParseException;
import cs2103.concierge.model.Model;
import javafx.collections.ObservableList;
import cs2103.concierge.model.person.Person;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = addressBookParser.parseCommand(commandText);
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
