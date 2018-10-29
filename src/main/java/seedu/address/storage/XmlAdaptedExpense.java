package seedu.address.storage;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Menu;
import seedu.address.model.expenses.Expense;
import seedu.address.model.expenses.ExpenseType;
import seedu.address.model.expenses.Money;

/**
 * JAXB-friendly adapted version of Expense.
 */
public class XmlAdaptedExpense {

    public static final String EXPENSETYPE_UNKNOWN_NAME = "Unknown";
    public static final Money EXPENSETYPE_UNKNOWN_COST = new Money(0, 0);
    public static final String MESSAGE_INVALID_COST = "The given cost is not in the correct format.";

    @XmlElement (required = true)
    private String item;
    @XmlElement (required = true)
    private String cost;
    @XmlElement (required = true)
    private String datetime;

    /**
     * Constructs an XmlAdaptedExpense.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedExpense() {}

    /**
     * Constructs an XmlAdaptedExpense with the given fields.
     *
     * @param item The menu number of the item.
     * @param cost The cost incurred in this expense.
     * @param datetime The date and time of this transaction.
     */
    public XmlAdaptedExpense(String item, String cost, String datetime) {
        this.item = item;
        this.cost = cost;
        this.datetime = datetime;
    }

    /**
     * Converts an Expense object into JAXB-friendly form.
     *
     * @param source The Expense object to be converted.
     */
    public XmlAdaptedExpense(Expense source) {
        this.item = source.getExpenseType().getItemNumber();
        this.cost = source.getCost().toString();
        this.datetime = source.getDateTimeString();
    }

    /**
     * Converts this object into the proper Expense object.
     * @return The Expense representation of this object.
     */
    public Expense toModelType(Menu menu) throws IllegalValueException {
        ExpenseType expenseType;
        if (!menu.isValidMenuNumber(item)) {
            expenseType = new ExpenseType(item, EXPENSETYPE_UNKNOWN_NAME, EXPENSETYPE_UNKNOWN_COST);
        } else {
            expenseType = menu.getExpenseType(item);
        }
        if (!Money.isValidMoneyFormat(cost)) {
            throw new IllegalValueException(MESSAGE_INVALID_COST);
        }
        Money moneyCost = new Money(cost);
        LocalDateTime localDateTime = LocalDateTime.parse(datetime, Expense.DATETIME_FORMAT);
        return new Expense(expenseType, moneyCost, localDateTime);
    }

}
