package seedu.address.model.expenses;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ExpensesTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Expenses(null));
    }

    @Test
    public void addExpense() {
        // null expense added
        Assert.assertThrows(NullPointerException.class, () -> (new Expenses()).addExpense(null));

        // functional programming - should be different object after calling addExpense
        Expenses first = new Expenses();
        Expenses second = first.addExpense(new Expense(new ExpenseType(
                "1", "-", new Money(0, 0))));
        assertFalse(first == second);

        // should have different items after adding item
        assertFalse(first.equals(second));
    }

    @Test
    public void clearExpenses() {
        Expenses expenses = new Expenses()
                .addExpense(new Expense(new ExpenseType("1", "-", new Money(0, 0))))
                .addExpense(new Expense(new ExpenseType("2", "--", new Money(1, 0))));
        Expenses cleared = expenses.clearExpenses();

        // functional programming - should be different object after calling clearExpenses
        assertFalse(expenses == cleared);

        // cleared should have no expenses
        assertTrue(cleared.equals(new Expenses()));
    }
}
