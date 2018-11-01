package seedu.address.testutil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import seedu.address.model.expenses.Expense;
import seedu.address.model.expenses.Expenses;
import seedu.address.model.expenses.Money;

/**
 * A utility class that provides sample {@code Expense} and {@code Expenses} objects to be used in tests.
 */
public class TypicalExpenses {

    public static final Expense EXPENSE_RS01 = new ExpenseBuilder()
            .withExpenseType(TypicalExpenseTypes.EXPENSE_TYPE_RS01)
            .withCost(new Money(50, 0))
            .withDateTime(LocalDateTime.now())
            .build();

    public static final Expense EXPENSE_MB02 = new ExpenseBuilder()
            .withExpenseType(TypicalExpenseTypes.EXPENSE_TYPE_MB02)
            .withCost(new Money(123, 45))
            .withDateTime(LocalDateTime.now())
            .build();

    public static final Expense EXPENSE_SP01 = new ExpenseBuilder()
            .withExpenseType(TypicalExpenseTypes.EXPENSE_TYPE_SP01)
            .withCost(new Money(5, 0))
            .withDateTime(LocalDateTime.now())
            .build();

    public static Expenses getTypicalExpenses() {
        List<Expense> expenseList = new ArrayList<Expense>();
        expenseList.add(EXPENSE_RS01);
        expenseList.add(EXPENSE_MB02);
        expenseList.add(EXPENSE_SP01);
        return new Expenses(expenseList);
    }
}
