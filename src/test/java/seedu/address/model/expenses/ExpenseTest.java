package seedu.address.model.expenses;

import java.time.LocalDateTime;

import org.junit.Test;

import seedu.address.testutil.Assert;
import seedu.address.testutil.ExpenseBuilder;

public class ExpenseTest {

    private final Expense validExpense = new ExpenseBuilder().build();

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Expense(null));
        Assert.assertThrows(NullPointerException.class, () -> new Expense(null, 0));
        Assert.assertThrows(NullPointerException.class, () -> new Expense(null, 0, LocalDateTime.now()));
        Assert.assertThrows(NullPointerException.class, () ->
                new Expense(new ExpenseType("1", "-", 0), 0, null));
    }
}
