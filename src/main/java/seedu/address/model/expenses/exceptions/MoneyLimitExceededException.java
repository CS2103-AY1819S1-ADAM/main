package seedu.address.model.expenses.exceptions;

/**
 * Exception to indicate that two Money objects cannot be added together
 * as the sum will exceed the accepted range (+/- Integer.MAX_VALUE).
 */
public class MoneyLimitExceededException extends RuntimeException {
    public MoneyLimitExceededException() {
        super("The resulting sum of money is too large.");
    }
}
