package seedu.address.logic.autocomplete;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;

public class AutoCompleteManagerTest {

    public static final String KEYWORD_NOT_FOUND = "Keyword not found";
    public static final String SAMPLE_ADD_COMMAND = AddCommand.COMMAND_WORD + " "
        + PREFIX_NAME + "Jim Carrey"
        + PREFIX_PHONE + "98563995"
        + PREFIX_EMAIL + "jimcarrey@yahoo.com.sg"
        + PREFIX_TAG + "friends"
        + PREFIX_ROOM + "55"
        + PREFIX_DATE_START + "09/12/18"
        + PREFIX_DATE_END + "10/12/18";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private AutoCompleteManager autoCompleteManager = new AutoCompleteManager();

    @Test
    public void getAutoCompleteCommands_prefixKeywordInCommandTrie_notEmptyList() {
        List<String> wordList = autoCompleteManager.getAutoCompleteCommands("add");
        assertFalse(wordList.isEmpty());
    }

    @Test
    public void getAutoCompleteCommands_emptyKeyword_notEmptyList() {
        List<String> wordList = autoCompleteManager.getAutoCompleteCommands("");
        assertFalse(wordList.isEmpty());
    }

    @Test
    public void getAutoCompleteCommands_prefixKeywordNotInCommandTrie_emptyList() {
        List<String> wordList = autoCompleteManager.getAutoCompleteCommands(KEYWORD_NOT_FOUND);
        assertTrue(wordList.isEmpty());
    }

    @Test
    public void getAutoCompleteCommands_nullKeyword_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        autoCompleteManager.getAutoCompleteCommands(null);
    }

    @Test
    public void getAutoCompleteCommands_emptyInput_emptyString() {
        String endResult = autoCompleteManager.getAutoCompleteNextMissingParameter("");
        assertTrue(endResult.isEmpty());
    }

    @Test
    public void getAutoCompleteNextMissingParameter_noMorePrefix_emptyString() {
        String endResult = autoCompleteManager.getAutoCompleteNextMissingParameter(SAMPLE_ADD_COMMAND);
        assertTrue(endResult.isEmpty());
    }

}
