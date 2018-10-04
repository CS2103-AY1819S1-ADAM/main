package seedu.address.model.expenses.exceptions;

/**
 * Indicates that the item number provided does not exist in the menu.
 */
public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException() {
        super("The item was not found in the menu.");
    }
}
