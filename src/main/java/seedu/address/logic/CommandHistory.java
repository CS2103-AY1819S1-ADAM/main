package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.util.LinkedList;
import java.util.List;

/**
 * Stores the history of commands executed.
 */
public class CommandHistory {
    private LinkedList<String> userInputHistory;

    public CommandHistory() {
        userInputHistory = new LinkedList<>();
    }

    public CommandHistory(CommandHistory commandHistory) {
        userInputHistory = new LinkedList<>(commandHistory.userInputHistory);
    }

    /**
     * Appends {@code userInput} to the list of user input entered.
     */
    public void add(String userInput) {
        requireNonNull(userInput);
        userInputHistory.add(userInput);

        /**
         * Converts userInputHistory from a linked list into a string so that it can be passed to the CommandArchive
         * class where the latest command can be extracted and added to commandFile.txt which tracks all the keystrokes
         * of a user with a timestamp
         */
        /*
        String newLine = System.getProperty("line.separator");
        StringBuilder string = new StringBuilder();
        Iterator<?> it = userInputHistory.descendingIterator();

        while (it.hasNext()) {
            string.append(it.next() + newLine);
        }
        String inputString = string.toString();
        System.out.println(inputString);
        // Fix this!!!
        // CommandArchive.stringToFile(inputString);
        */
    }

    /**
     * Returns a defensive copy of {@code userInputHistory}.
     */
    public List<String> getHistory() {
        return new LinkedList<>(userInputHistory);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof CommandHistory)) {
            return false;
        }

        // state check
        CommandHistory other = (CommandHistory) obj;
        return userInputHistory.equals(other.userInputHistory);
    }

    @Override
    public int hashCode() {
        return userInputHistory.hashCode();
    }
}
