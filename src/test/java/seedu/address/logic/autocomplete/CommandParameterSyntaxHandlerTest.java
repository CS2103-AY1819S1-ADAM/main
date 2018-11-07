package seedu.address.logic.autocomplete;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CheckInCommand;
import seedu.address.logic.commands.CheckoutCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.LogInCommand;
import seedu.address.logic.commands.ServiceCommand;
import seedu.address.logic.parser.Prefix;

public class CommandParameterSyntaxHandlerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandParameterSyntaxHandler handler;

    @Before
    public void setup() {
        handler = new CommandParameterSyntaxHandler();
    }

    @Test
    public void getMissingPrefix_addCommand_allPrefixesMissing() {
        ArrayList<Prefix> result = handler.getMissingPrefixes(AddCommand.COMMAND_WORD, AddCommand.COMMAND_WORD);
        assertEquals(result, handler.ADD_COMMAND_PREFIXES);
    }

    @Test
    public void getMissingPrefix_checkInCommand_allPrefixesMissing() {
        ArrayList<Prefix> result = handler.getMissingPrefixes(CheckInCommand.COMMAND_WORD,
                CheckInCommand.COMMAND_WORD);
        assertEquals(result, handler.CHECKIN_COMMAND_PREFIXES);
    }

    @Test
    public void getMissingPrefix_checkOutCommand_allPrefixesMissing() {
        ArrayList<Prefix> result = handler.getMissingPrefixes(CheckoutCommand.COMMAND_WORD,
                CheckoutCommand.COMMAND_WORD);
        assertEquals(result, handler.CHECKOUT_COMMAND_PREFIXES);
    }

    @Test
    public void getMissingPrefix_editCommand_allPrefixesMissing() {
        ArrayList<Prefix> result = handler.getMissingPrefixes(EditCommand.COMMAND_WORD, EditCommand.COMMAND_WORD);
        assertEquals(result, handler.EDIT_COMMAND_PREFIXES);
    }

    @Test
    public void getMissingPrefix_loginCommand_allPrefixesMissing() {
        ArrayList<Prefix> result = handler.getMissingPrefixes(LogInCommand.COMMAND_WORD,
                LogInCommand.COMMAND_WORD);
        assertEquals(result, handler.LOGIN_COMMAND_PREFIXES);
    }

    @Test
    public void getMissingPrefix_serviceCommand_allPrefixesMissing() {
        ArrayList<Prefix> result = handler.getMissingPrefixes(ServiceCommand.COMMAND_WORD,
                ServiceCommand.COMMAND_WORD);
        assertEquals(result, handler.SERVICE_COMMAND_PREFIXES);
    }
}
