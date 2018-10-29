package seedu.address.model.expenses;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.address.model.expenses.exceptions.ItemNotFoundException;

/**
 * Represents a single spending on a single type of service.
 */
public class Expense {

    /**
     * Standard date-time format used for this hotel's expenses
     */
    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("d/M/u HH:mm:ss");

    private final Money cost;
    private final ExpenseType type;
    private final LocalDateTime dateTime;

    /**
     * Constructs an {@code Expense} object.
     *
     * @param type The menu number of the product or service exchanged for with this expense.
     * @param cost The monetary value of the expense.
     */
    public Expense(ExpenseType type, Money cost) {
        requireNonNull(type);
        this.type = type;
        this.cost = cost;
        this.dateTime = LocalDateTime.now();
    }

    /**
     * Constructs an {@code Expense} object.
     * Used if the user is charging the base price and hence does not need to manually enter a price.
     *
     * @param type The product or service exchanged for with this expense.
     * @throws ItemNotFoundException if the item's menu number does not exist in the menu.
     */
    public Expense(ExpenseType type) {
        requireNonNull(type);
        this.type = type;
        this.cost = this.type.getItemCost();
        this.dateTime = LocalDateTime.now();
    }

    public Expense(ExpenseType type, Money cost, LocalDateTime dateTime) {
        requireAllNonNull(type, dateTime);
        this.type = type;
        this.cost = cost;
        this.dateTime = dateTime;
    }

    /**
     * Provides the cost of this expense.
     *
     * @return The monetary value of the expense.
     */
    public Money getCost() {
        return cost;
    }

    /**
     * Provides the expense type of the item purchased in this expense.
     *
     * @return The ExpenseType object of the product or service exchanged for with this expense.
     */
    public ExpenseType getExpenseType() {
        return type;
    }

    /**
     * Provides the name of the item purchased in this expense.
     *
     * @return The name of the product or service exchanged for with this expense.
     */
    public String getItemName() {
        return type.getItemName();
    }

    /**
     * Provides the date of the transaction.
     *
     * @return The date and time of this transaction.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Provides the date of the transaction in a string.
     *
     * @return The date and time of this transaction in string.
     */
    public String getDateTimeString() {
        return dateTime.format(DATETIME_FORMAT);
    }

    @Override
    public String toString() {
        return cost + " spent on " + type.getItemName() + " on " + getDateTimeString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Expenses // instanceof handles nulls
                && cost.equals(((Expense) other).cost)
                && type.equals(((Expense) other).type)
                && dateTime.equals(((Expense) other).dateTime)); // state check
    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }
}
