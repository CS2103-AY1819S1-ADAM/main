package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.time.format.DateTimeParseException;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Menu;
import seedu.address.model.expenses.Expense;
import seedu.address.model.expenses.ExpenseType;
import seedu.address.testutil.Assert;
import seedu.address.testutil.ExpenseBuilder;

public class XmlAdaptedExpenseTest {
    private static final String INVALID_ITEM = "1";
    private static final String INVALID_ITEM_EMPTY = "";
    private static final String INVALID_DATETIME = "2018-10-14T22:34:40.283201400";
    private static final String INVALID_COST = "abc";

    private static final Expense VALID_EXPENSE = new ExpenseBuilder().build();
    private static final ExpenseType VALID_EXPENSE_TYPE = VALID_EXPENSE.getExpenseType();
    private static final String VALID_ITEM = VALID_EXPENSE.getItemName();
    private static final String VALID_COST = VALID_EXPENSE.getCost().toString();
    private static final String VALID_DATETIME = VALID_EXPENSE.getDateTimeString();

    private static final Menu VALID_MENU = new Menu();

    @Before
    public void initialize() {
        HashMap<String, ExpenseType> validMenuMap = new HashMap<>();
        validMenuMap.put(VALID_ITEM, VALID_EXPENSE_TYPE);
        VALID_MENU.setMenu(validMenuMap);
    }

    @Test
    public void toModelType_validDetails_returnsNormalExpense() throws Exception {
        XmlAdaptedExpense testExpense = new XmlAdaptedExpense(VALID_ITEM, VALID_COST, VALID_DATETIME);
        Expense convertedExpense = testExpense.toModelType(VALID_MENU);
        assertEquals(convertedExpense.getExpenseType(), VALID_MENU.getExpenseType(VALID_ITEM));
        assertEquals(convertedExpense.getDateTimeString(), VALID_DATETIME);
    }

    @Test
    public void toModelType_invalidItem_returnsUnknownExpense() throws Exception {
        XmlAdaptedExpense testExpense = new XmlAdaptedExpense(INVALID_ITEM, VALID_COST, VALID_DATETIME);
        Expense convertedExpense = testExpense.toModelType(VALID_MENU);
        ExpenseType unknownType = new ExpenseType(INVALID_ITEM,
                XmlAdaptedExpense.EXPENSE_TYPE_UNKNOWN_NAME, XmlAdaptedExpense.EXPENSE_TYPE_UNKNOWN_COST);
        assertEquals(convertedExpense.getExpenseType(), unknownType);
        assertEquals(convertedExpense.getDateTimeString(), VALID_DATETIME);
    }

    @Test
    public void toModelType_emptyItem_throwsIllegalArgumentException() {
        XmlAdaptedExpense testExpense = new XmlAdaptedExpense(INVALID_ITEM_EMPTY, VALID_COST, VALID_DATETIME);
        Assert.assertThrows(IllegalArgumentException.class,
                ExpenseType.MESSAGE_NUMBER_EMPTY, () -> testExpense.toModelType(VALID_MENU));
    }

    @Test
    public void toModelType_invalidDatetime_throwsDateTimeParseException() {
        XmlAdaptedExpense testExpense = new XmlAdaptedExpense(VALID_ITEM, VALID_COST, INVALID_DATETIME);
        Assert.assertThrows(DateTimeParseException.class, () -> testExpense.toModelType(VALID_MENU));
    }

    @Test
    public void toModelType_invalidCost_throwsIllegalValueException() {
        XmlAdaptedExpense testExpense = new XmlAdaptedExpense(VALID_ITEM, INVALID_COST, VALID_DATETIME);
        Assert.assertThrows(IllegalValueException.class, () -> testExpense.toModelType(VALID_MENU));
    }
}
