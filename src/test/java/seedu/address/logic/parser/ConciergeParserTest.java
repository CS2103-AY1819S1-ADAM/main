package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.FLAG_GUEST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.testutil.TypicalBookingPeriods.TODAY_NEXTWEEK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GUEST;
import static seedu.address.testutil.TypicalRoomNumbers.ROOM_NUMBER_002;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CheckInCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditGuestDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.ServiceCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.guest.Guest;
import seedu.address.model.guest.GuestNameContainsKeywordsPredicate;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.BookingPeriod;
import seedu.address.testutil.EditGuestDescriptorBuilder;
import seedu.address.testutil.GuestBuilder;
import seedu.address.testutil.GuestUtil;
import seedu.address.testutil.TypicalExpenseTypes;
import seedu.address.testutil.TypicalExpenses;
import seedu.address.testutil.TypicalRoomNumbers;

public class ConciergeParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ConciergeParser parser = new ConciergeParser();

    @Test
    public void parseCommand_add() throws Exception {
        Guest guest = new GuestBuilder().build();
        RoomNumber roomNumber = ROOM_NUMBER_002;
        BookingPeriod bookingPeriod = TODAY_NEXTWEEK;

        AddCommand command = (AddCommand) parser.parseCommand(
                GuestUtil.getAddCommand(guest, roomNumber, bookingPeriod));
        assertEquals(new AddCommand(guest, roomNumber, bookingPeriod),
                command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Guest guest = new GuestBuilder().build();
        EditGuestDescriptor descriptor = new EditGuestDescriptorBuilder(guest).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_GUEST.getOneBased() + " " + GuestUtil.getEditGuestDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_GUEST, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + FLAG_GUEST.toString() + " " + PREFIX_NAME.toString()
                        + keywords.stream().collect(Collectors.joining(" ")));
        List<Predicate<Guest>> listPredicates = new LinkedList<>();
        List<Predicate<Room>> emptyRoomPredicates = new LinkedList<>();
        listPredicates.add(new GuestNameContainsKeywordsPredicate(keywords));
        assertEquals(new FindCommand(FLAG_GUEST.toString(), listPredicates, emptyRoomPredicates), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " -r") instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " -g") instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_GUEST.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_GUEST), command);
    }

    @Test
    public void parseCommand_service() throws Exception {
        String argsWithoutCost = CliSyntax.PREFIX_ROOM + TypicalRoomNumbers.ROOM_NUMBER_001.toString() + " "
                + CliSyntax.PREFIX_ITEM_NUMBER + TypicalExpenseTypes.EXPENSE_TYPE_RS01.getItemNumber();
        String argsWithCost = argsWithoutCost + " " + CliSyntax.PREFIX_COST
                + TypicalExpenses.EXPENSE_RS01.getCost().toString();
        ServiceCommand commandWithoutCost = (ServiceCommand) parser.parseCommand(
                ServiceCommand.COMMAND_WORD + " " + argsWithoutCost);
        ServiceCommand commandWithCost = (ServiceCommand) parser.parseCommand(
                ServiceCommand.COMMAND_WORD + " " + argsWithCost);
        assertEquals(new ServiceCommand(TypicalRoomNumbers.ROOM_NUMBER_001,
                TypicalExpenseTypes.EXPENSE_TYPE_RS01.getItemNumber(),
                Optional.of(TypicalExpenses.EXPENSE_RS01.getCost())), commandWithCost);
        assertEquals(new ServiceCommand(TypicalRoomNumbers.ROOM_NUMBER_001,
                TypicalExpenseTypes.EXPENSE_TYPE_RS01.getItemNumber(),
                Optional.empty()), commandWithoutCost);
    }

    @Test
    public void parseCommand_checkIn() throws Exception {
        CheckInCommand command = (CheckInCommand) parser.parseCommand(
                CheckInCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_ROOM
                        + TypicalRoomNumbers.ROOM_NUMBER_001.toString());
        assertEquals(new CheckInCommand(TypicalRoomNumbers.ROOM_NUMBER_001), command);
    }

    @Test
    public void parseCommand_checkout() throws Exception {
        // TODO whoever made checkout command
        assertEquals(1, 1);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
