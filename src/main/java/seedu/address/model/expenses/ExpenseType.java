package seedu.address.model.expenses;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Contains all the different types of expenses available at the hotel.
 * All products and services have a unique menu number.
 * ExpenseType objects will only be created when the menu is read from the hard disk.
 */
public class ExpenseType {

    public static final String MESSAGE_NUMBER_EMPTY = "The given number is empty.";
    public static final String MESSAGE_NAME_EMPTY = "The given name is empty.";

    private final String itemNumber;
    private final String itemName;
    private final double itemCost;

    /**
     * Constructor for an {@code ExpenseType} object.
     *
     * @param itemNumber The menu number of the item.
     * @param itemName The name of the item.
     * @param itemCost The cost of the item.
     */
    public ExpenseType(String itemNumber, String itemName, double itemCost) {
        requireAllNonNull(itemNumber, itemName);
        if (itemNumber.equals("")) {
            throw new IllegalArgumentException(MESSAGE_NUMBER_EMPTY);
        }
        if (itemName.equals("")) {
            throw new IllegalArgumentException(MESSAGE_NAME_EMPTY);
        }
        this.itemNumber = itemNumber;
        this.itemName = itemName;
        this.itemCost = itemCost;
    }

    public ExpenseType(ExpenseType expenseTypeToCopy) {
        this.itemNumber = expenseTypeToCopy.getItemNumber();
        this.itemName = expenseTypeToCopy.getItemName();
        this.itemCost = expenseTypeToCopy.getItemCost();
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemCost() {
        return itemCost;
    }

    @Override
    public String toString() {
        return itemNumber + " " + itemName + " $" + itemCost;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpenseType // instanceof handles nulls
                && itemNumber.equals(((ExpenseType) other).itemNumber)
                && itemName.equals(((ExpenseType) other).itemName)
                && itemCost == ((ExpenseType) other).itemCost); // state check
    }

    @Override
    public int hashCode() {
        return itemNumber.hashCode();
    }
}
