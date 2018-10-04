package seedu.address.model.expenses;

import java.util.Date;

import seedu.address.model.expenses.exceptions.ItemNotFoundException;

/**
 * Represents a single spending on a single type of service.
 */
public class Expense {

    private final double cost;
    private final String item;
    private final Date date;

    /**
     * Constructs an {@code Expense} object.
     *
     * @param item The product or service exchanged for with this expense.
     * @param cost The monetary value of the expense.
     * @throws ItemNotFoundException if the item's menu number does not exist in the menu.
     */
    public Expense(String item, double cost) throws ItemNotFoundException {
        if (!ExpenseType.isValidMenuNumber(item)) {
            throw new ItemNotFoundException();
        }
        this.cost = cost;
        this.item = item;
        this.date = new Date();
    }

    /**
     * Constructs an {@code Expense} object.
     * Used if the user is charging the base price and hence does not need to manually enter a price.
     *
     * @param item The product or service exchanged for with this expense.
     * @throws ItemNotFoundException if the item's menu number does not exist in the menu.
     */
    public Expense(String item) {
        if (!ExpenseType.isValidMenuNumber(item)) {
            throw new ItemNotFoundException();
        }
        this.cost = ExpenseType.getItemPrice(item);
        this.item = item;
        this.date = new Date();
    }

    /**
     * Provides the cost of this expense.
     *
     * @return The monetary value of the expense.
     */
    public double getCost() {
        return cost;
    }

    /**
     * Provides the menu number of the item purchased in this expense.
     *
     * @return The product or service exchanged for with this expense.
     */
    public String getItemNumber() {
        return item;
    }

    /**
     * Provides the name of the item purchased in this expense.
     *
     * @return The product or service exchanged for with this expense.
     */
    public String getItemName() {
        return ExpenseType.getItemName(item);
    }

    /**
     * Provides the date of the transaction.
     *
     * @return The date and time of this transaction.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Provides the date of the transaction in a string.
     *
     * @return The date and time of this transaction in string.
     */
    public String getDateString() {
        return date.toString();
    }

    @Override
    public String toString() {
        return cost + " spent on " + item + " on " + getDateString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Expenses // instanceof handles nulls
                && cost == ((Expense) other).cost
                && item.equals(((Expense) other).item)
                && date.equals(((Expense) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
