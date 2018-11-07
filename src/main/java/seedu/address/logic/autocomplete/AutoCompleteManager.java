package seedu.address.logic.autocomplete;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CheckInCommand;
import seedu.address.logic.commands.CheckoutCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LogInCommand;
import seedu.address.logic.commands.LogOutCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.ServiceCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.Prefix;

/**
 * AutoCompleteManager of the application
 */
public class AutoCompleteManager {

    private static final String ROOM_PREFIX = "r/";
    private static final String USER_PREFIX = "user/";
    private static final String EMPTY_STRING = "";

    private Trie commandTrie;
    private CommandParameterSyntaxHandler commandParameterSyntaxHandler;

    public AutoCompleteManager() {
        commandTrie = new Trie();
        commandParameterSyntaxHandler = new CommandParameterSyntaxHandler();
        initCommandKeyWords();
    }

    /**
     * Initialises command keywords in commandTrie
     */
    private void initCommandKeyWords() {
        commandTrie.insertWord(AddCommand.COMMAND_WORD);
        commandTrie.insertWord(ClearCommand.COMMAND_WORD);
        commandTrie.insertWord(EditCommand.COMMAND_WORD);
        commandTrie.insertWord(ExitCommand.COMMAND_WORD);
        commandTrie.insertWord(FindCommand.COMMAND_WORD);
        commandTrie.insertWord(HelpCommand.COMMAND_WORD);
        commandTrie.insertWord(HistoryCommand.COMMAND_WORD);
        commandTrie.insertWord(ListCommand.COMMAND_WORD);
        commandTrie.insertWord(LogOutCommand.COMMAND_WORD);
        commandTrie.insertWord(RedoCommand.COMMAND_WORD);
        commandTrie.insertWord(SelectCommand.COMMAND_WORD);
        commandTrie.insertWord(ServiceCommand.COMMAND_WORD);
        commandTrie.insertWord(UndoCommand.COMMAND_WORD);

        commandTrie.insertWord(CheckInCommand.COMMAND_WORD + ROOM_PREFIX);
        commandTrie.insertWord(CheckoutCommand.COMMAND_WORD + ROOM_PREFIX);
        commandTrie.insertWord(LogInCommand.COMMAND_WORD + USER_PREFIX);

    }

    /**
     * Returns sorted list of auto-completed commands with prefix
     */
    public List<String> getAutoCompleteCommands(String commandPrefix) {
        requireNonNull(commandPrefix);
        return commandTrie.autoComplete(commandPrefix).stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());
    }

    /**
     * Returns the next missing parameter of the inputText or
     * an empty string if there is no prefix
     */
    public String getAutoCompleteNextMissingParameter(String inputText) {
        requireNonNull(inputText);
        if (inputText.isEmpty()) {
            return EMPTY_STRING;
        }
        String command = inputText.split(" ")[0];

        ArrayList<Prefix> missingPrefixes = commandParameterSyntaxHandler.getMissingPrefixes(command, inputText);

        if (!missingPrefixes.isEmpty()) {
            return missingPrefixes.get(0).getPrefix();
        } else {
            return EMPTY_STRING;
        }

    }
}
