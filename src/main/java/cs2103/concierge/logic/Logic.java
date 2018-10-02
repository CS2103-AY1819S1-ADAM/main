package cs2103.concierge.logic;

import cs2103.concierge.logic.commands.CommandResult;
import cs2103.concierge.logic.commands.exceptions.CommandException;
import cs2103.concierge.logic.parser.exceptions.ParseException;
import javafx.collections.ObservableList;
import cs2103.concierge.model.person.Person;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
