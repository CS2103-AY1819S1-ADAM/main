package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM;

import java.util.Optional;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Menu;
import seedu.address.model.Model;
import seedu.address.model.expenses.Expense;
import seedu.address.model.expenses.Money;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.exceptions.NoBookingException;
import seedu.address.model.room.booking.exceptions.RoomNotCheckedInException;

/**
 * Adds an Expense to a Room.
 */
public class ServiceCommand extends Command {

    public static final String COMMAND_WORD = "service";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds an Expense for the guests of a Room. "
            + "Parameters: "
            + PREFIX_ROOM + "ROOM "
            + PREFIX_ITEM_NUMBER + "ITEM NUMBER "
            + "[" + PREFIX_COST + "COST]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ROOM + "001 "
            + PREFIX_ITEM_NUMBER + "RS01 "
            + PREFIX_COST + "2.45";

    public static final String MESSAGE_SUCCESS =
            "New expense added: %1$s\nCharged to room: %2$s";
    public static final String MESSAGE_ROOM_HAS_NO_GUEST =
            "There are no guests checked in to this room.";
    public static final String MESSAGE_ITEM_NOT_FOUND =
            "The item was not found in the menu.";

    private final RoomNumber roomNumber;
    private final String itemNumber;
    private final Optional<Money> itemCost;

    /**
     * Creates a command to add the Expense to the Room.
     * @param roomNumber The number of the room to add the expense to
     * @param itemNumber The menu number of the item bought
     * @param itemCost The cost of the item
     */
    public ServiceCommand(RoomNumber roomNumber, String itemNumber, Optional<Money> itemCost) {
        requireAllNonNull(roomNumber, itemNumber, itemCost);
        this.roomNumber = roomNumber;
        this.itemNumber = itemNumber;
        this.itemCost = itemCost;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Menu menu = model.getMenu();
        if (!menu.isValidMenuNumber(itemNumber)) {
            throw new CommandException(MESSAGE_ITEM_NOT_FOUND);
        }
        Expense expense;
        if (itemCost.isPresent()) {
            expense = new Expense(menu.getExpenseType(itemNumber), itemCost.get());
        } else {
            expense = new Expense(menu.getExpenseType(itemNumber));
        }
        try {
            model.addExpense(roomNumber, expense);
            model.commitConcierge();
            return new CommandResult(String.format(MESSAGE_SUCCESS,
                    expense.getItemName(), roomNumber.toString()));
        } catch (NoBookingException | RoomNotCheckedInException e) {
            throw new CommandException(MESSAGE_ROOM_HAS_NO_GUEST);
        }
    }

    @Override
    public boolean requiresSignIn() {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ServiceCommand // instanceof handles nulls
                && roomNumber.equals(((ServiceCommand) other).roomNumber)
                && itemNumber.equals(((ServiceCommand) other).itemNumber)
                && itemCost.equals(((ServiceCommand) other).itemCost));
    }
}
