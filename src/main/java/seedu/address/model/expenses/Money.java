package seedu.address.model.expenses;

import java.math.BigDecimal;
import java.math.BigInteger;

import seedu.address.model.expenses.exceptions.MoneyLimitExceededException;

/**
 * Class to represent monetary values.
 * Ensures that prices keyed in for Expense objects are valid.
 * Range of dollars limited to +/- Integer.MAX_VALUE.
 * Range does not include Integer.MIN_VALUE to prevent errors when changing signs.
 */
public class Money {

    private int dollars;
    private int cents;
    //v2.0: implement exchange rates

    public Money(int dollars, int cents) {
        if (cents >= 100 || cents < 0 || dollars == Integer.MIN_VALUE) {
            throw new IllegalArgumentException();
        }
        this.dollars = dollars;
        this.cents = cents;
    }

    /**
     * Converts a string with valid money format into a Money object.
     * @param toConvert The string to convert to a Money object.
     */
    public Money(String toConvert) {
        if (!isValidMoneyFormat(toConvert)) {
            throw new IllegalArgumentException();
        }
        String[] parts = toConvert.split("\\.");
        dollars = Integer.parseInt(parts[0]);
        cents = Integer.parseInt(parts[1]);
    }

    /**
     * Checks that a string is a valid representation of money.
     * @param money The string to be checked.
     * @return True if the string is in money format, false otherwise.
     */
    public static boolean isValidMoneyFormat(String money) {
        // check 1: if negative value, make a new string without negative sign
        String positiveMoney = new String(money);
        if (money.toCharArray()[0] == '-') {
            positiveMoney = money.substring(1);
        }

        // check 2: if starts with 0, should be 4 chars long, e.g. 0.45
        if (positiveMoney.toCharArray()[0] == '0' && positiveMoney.length() != 4) {
            return false;
        }

        // check 3: regex checking
        String regex = "\\d{1,10}\\.\\d{2}";
        if (!positiveMoney.matches(regex)) {
            return false;
        }

        // check 4: range checking
        // assume BigDecimal and BigInteger can hold arbitrarily large numbers
        BigDecimal d = new BigDecimal(positiveMoney);
        BigInteger i = d.toBigInteger();
        if (i.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) == 1) {
            return false;
        }

        return true;
    }

    /**
     * Creates a new Money object that is the sum of this object and another object.
     * @param addend The Money object to be added to this Money object.
     * @return The Money object representing the sum of the two Money objects.
     */
    public Money add(Money addend) {
        BigDecimal first = new BigDecimal(this.toString());
        BigDecimal second = new BigDecimal(addend.toString());
        BigDecimal sum = first.add(second);
        if (sum.abs().toBigInteger().compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) == 1) {
            throw new MoneyLimitExceededException();
        }
        return new Money(sum.setScale(2).toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Money // instanceof handles nulls
                && dollars == ((Money) other).dollars
                && cents == ((Money) other).cents); // state check
    }

    @Override
    public String toString() {
        return dollars + "." + String.format("%02d", cents);
    }

    @Override
    public int hashCode() {
        return (dollars * 100) + cents;
    }
}
