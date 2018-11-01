package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import seedu.address.model.expenses.ExpenseType;
import seedu.address.model.expenses.exceptions.ItemNotFoundException;

/**
 * A representation of the menu of the items and services for sale.
 * This class should be read-only.
 */
public class Menu {

    private HashMap<String, ExpenseType> numberToType;

    public Menu() {
        numberToType = new HashMap<>();
    }

    public Menu(Menu other) {
        requireNonNull(other);
        numberToType = new HashMap<>(other.numberToType);
    }

    public void setMenu(Map<String, ExpenseType> other) {
        requireNonNull(other);
        numberToType.putAll(other);
    }

    public Map<String, ExpenseType> asUnmodifiableMap() {
        return Collections.unmodifiableMap(numberToType);
    }

    /**
     * Checks that the given item number exists in the menu.
     * @param item The item number to check.
     * @return True if the item number is in the menu, false otherwise.
     */
    public boolean isValidMenuNumber(String item) {
        requireNonNull(item);
        return numberToType.containsKey(item);
    }

    public ExpenseType getExpenseType(String item) {
        requireNonNull(item);
        if (!isValidMenuNumber(item)) {
            throw new ItemNotFoundException();
        }
        return numberToType.get(item);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Menu // instanceof handles nulls
                && numberToType.equals(((Menu) other).numberToType)); // state check
    }

    @Override
    public int hashCode() {
        return numberToType.hashCode();
    }
}
