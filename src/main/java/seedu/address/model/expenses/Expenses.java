package seedu.address.model.expenses;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Contains all of the expenses incurred by the guests of a given room.
 */
public class Expenses {

    private final List<Expense> expenseList;

    /**
     * Constructs an {@code Expenses} object.
     */
    public Expenses() {
        expenseList = new LinkedList<>();
    }

    public Expenses(List<Expense> expenseList) {
        requireNonNull(expenseList);
        this.expenseList = new LinkedList<>(expenseList);
    }

    /**
     * Adds a new {@code Expense} to the current expenses.
     * New expense is added in front as it is more likely that recent expenses are accessed.
     *
     * @param newExpense The new expense incurred.
     * @return A new Expenses object with the new expense.
     */
    public Expenses addExpense(Expense newExpense) {
        requireNonNull(newExpense);
        List<Expense> newList = new LinkedList<>(expenseList);
        newList.add(0, newExpense);
        return new Expenses(newList);
    }

    /**
     * Clears all of the expense records when called.
     * Usually called when guests have checked out.
     *
     * @return An Expenses object with no expenses.
     */
    public Expenses clearExpenses() {
        return new Expenses();
    }

    public List<Expense> getExpensesList() {
        return Collections.unmodifiableList(expenseList);
    }

    /**
     * Get the total cost of all the expenses as a string.
     */
    public String toStringTotalCost() {
        Money totalCost = new Money(0, 0);
        for (Expense e : expenseList) {
            totalCost = totalCost.add(e.getCost());
        }
        return totalCost.toString();
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Expense e : expenseList) {
            output.append(e.toString() + "\n");
        }
        return output.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Expenses // instanceof handles nulls
                && expenseList.equals(((Expenses) other).expenseList)); // state check
    }

    @Override
    public int hashCode() {
        return expenseList.hashCode();
    }
}
