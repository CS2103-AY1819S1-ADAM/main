package seedu.address.model.expenses;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ExpenseTypeTest {

    private final ExpenseType validExpenseType = new ExpenseType(
            "1", "-", new Money(0, 0));

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ExpenseType(
                null, "-", new Money(0, 0)));
        Assert.assertThrows(NullPointerException.class, () -> new ExpenseType(
                "1", null, new Money(0, 0)));
    }

    @Test
    public void constructor_emptyString_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class,
                ExpenseType.MESSAGE_NUMBER_EMPTY, () -> new ExpenseType(
                        "", "-", new Money(0, 0)));
        Assert.assertThrows(IllegalArgumentException.class,
                ExpenseType.MESSAGE_NAME_EMPTY, () -> new ExpenseType(
                        "1", "", new Money(0, 0)));
    }

    @Test
    public void getItemNumber() {
        assert validExpenseType.getItemNumber().equals("1");
    }

    @Test
    public void getItemName() {
        assert validExpenseType.getItemName().equals("-");
    }

    @Test
    public void getItemCost() {
        assert validExpenseType.getItemCost().equals(new Money(0, 0));
    }
}
