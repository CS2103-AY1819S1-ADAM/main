package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.guest.Guest;
import seedu.address.model.room.Room;

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


    /** Returns an unmodifiable view of the filtered list of guests */
    ObservableList<Guest> getFilteredGuestList();

    /** Returns an unmodifiable view of the filtered list of checked-in guests */
    public ObservableList<Guest> getFilteredCheckedInGuestList();

    /** Returns an unmodifiable view of the filtered list of rooms */
    ObservableList<Room> getFilteredRoomList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
