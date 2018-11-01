package seedu.address.testutil;

import seedu.address.model.expenses.ExpenseType;
import seedu.address.model.expenses.Money;

/**
 * A utility class containing a list of {@code ExpenseType} objects to be used in tests.
 */
public class TypicalExpenseTypes {
    public static final ExpenseType EXPENSE_TYPE_RS01 = new ExpenseType(
            "RS01", "Room service: Red wine", new Money(50, 0));
    public static final ExpenseType EXPENSE_TYPE_RS02 = new ExpenseType(
            "RS02", "Room service: Beef steak", new Money(70, 0));
    public static final ExpenseType EXPENSE_TYPE_RS03 = new ExpenseType(
            "RS03", "Room service: Thai massage", new Money(100, 0));
    public static final ExpenseType EXPENSE_TYPE_MB01 = new ExpenseType(
            "MB01", "Minibar: Coca cola", new Money(3, 0));
    public static final ExpenseType EXPENSE_TYPE_MB02 = new ExpenseType(
            "MB02", "Minibar: Sprite", new Money(3, 0));
    public static final ExpenseType EXPENSE_TYPE_MB03 = new ExpenseType(
            "MB03", "Minibar: Tiger beer", new Money(6, 0));
    public static final ExpenseType EXPENSE_TYPE_MB04 = new ExpenseType(
            "MB04", "Minibar: Mineral water", new Money(3, 0));
    public static final ExpenseType EXPENSE_TYPE_SP01 = new ExpenseType(
            "SP01", "Swimming pool: Entry", new Money(5, 0));

    /**
     * Prevents initialisation.
     */
    private TypicalExpenseTypes() {}
}
