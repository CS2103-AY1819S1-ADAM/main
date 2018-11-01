package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.expenses.ExpenseType;
import seedu.address.model.expenses.Money;
import seedu.address.testutil.Assert;

public class XmlAdaptedExpenseTypeTest {

    private static final String VALID_NUMBER = "1";
    private static final String VALID_NAME = "name";
    private static final String VALID_COST = "1.23";

    @Test
    public void toModelType_validArguments_returnsExpenseTypeObject() throws Exception {
        XmlAdaptedExpenseType testType = new XmlAdaptedExpenseType(VALID_NUMBER, VALID_NAME, VALID_COST);
        assertEquals(testType.toModelType(), new ExpenseType(
                VALID_NUMBER, VALID_NAME, new Money(VALID_COST)));
    }

    @Test
    public void toModelType_emptyNumber_throwsIllegalValueException() {
        XmlAdaptedExpenseType testType = new XmlAdaptedExpenseType("", VALID_NAME, VALID_COST);
        Assert.assertThrows(IllegalValueException.class, ExpenseType.MESSAGE_NUMBER_EMPTY, testType::toModelType);
    }

    @Test
    public void toModelType_emptyName_throwsIllegalValueException() {
        XmlAdaptedExpenseType testType = new XmlAdaptedExpenseType(VALID_NUMBER, "", VALID_COST);
        Assert.assertThrows(IllegalValueException.class, ExpenseType.MESSAGE_NAME_EMPTY, testType::toModelType);
    }

    @Test
    public void toModelType_nullNumber_throwsIllegalValueException() {
        XmlAdaptedExpenseType testType = new XmlAdaptedExpenseType(null, VALID_NAME, VALID_COST);
        Assert.assertThrows(IllegalValueException.class,
                XmlAdaptedExpenseType.MESSAGE_NUMBER_MISSING, testType::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedExpenseType testType = new XmlAdaptedExpenseType(VALID_NUMBER, null, VALID_COST);
        Assert.assertThrows(IllegalValueException.class,
                XmlAdaptedExpenseType.MESSAGE_NAME_MISSING, testType::toModelType);
    }

    @Test
    public void toModelType_nullCost_throwsIllegalValueException() {
        XmlAdaptedExpenseType testType = new XmlAdaptedExpenseType(VALID_NUMBER, VALID_NAME, null);
        Assert.assertThrows(IllegalValueException.class,
                XmlAdaptedExpenseType.MESSAGE_COST_MISSING, testType::toModelType);
    }

    @Test
    public void toModelType_invalidCost_throwsIllegalValueException() {
        XmlAdaptedExpenseType testType = new XmlAdaptedExpenseType(VALID_NUMBER, VALID_NAME, "abc");
        Assert.assertThrows(IllegalValueException.class,
                XmlAdaptedExpense.MESSAGE_INVALID_COST, testType::toModelType);
    }

}
