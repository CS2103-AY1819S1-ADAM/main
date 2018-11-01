package seedu.address.model.expenses;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.expenses.exceptions.MoneyLimitExceededException;
import seedu.address.testutil.Assert;

public class MoneyTest {

    @Test
    public void constructor_invalidDollarsAndCents_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Money(123, 456));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Money(123, -45));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Money(Integer.MIN_VALUE, 0));
    }

    @Test
    public void constructor_validDollarsAndCents_success() {
        new Money(123, 45);
        new Money(-678, 90);
        new Money(Integer.MAX_VALUE, 99);
        new Money(Integer.MIN_VALUE + 1, 0);
    }

    @Test
    public void constructor_invalidString_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Money("abc"));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Money("123.456"));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Money("1234567654321.46"));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Money("-1234567654321.46"));
    }

    @Test
    public void constructor_validString_success() {
        Money m = new Money("123.45");
        assertEquals(m, new Money(123, 45));
        Money n = new Money("987654321.01");
        assertEquals(n, new Money(987654321, 1));
    }

    @Test
    public void isValidMoneyFormat() {
        // valid
        assertTrue(Money.isValidMoneyFormat("123.45"));
        assertTrue(Money.isValidMoneyFormat("999999999.99"));
        assertTrue(Money.isValidMoneyFormat("1.01"));
        assertTrue(Money.isValidMoneyFormat("0.45"));
        assertTrue(Money.isValidMoneyFormat("-123.45"));
        assertTrue(Money.isValidMoneyFormat("-2147483647.00"));
        assertTrue(Money.isValidMoneyFormat("2147483647.99"));

        // invalid
        assertFalse(Money.isValidMoneyFormat("abc"));
        assertFalse(Money.isValidMoneyFormat("2234567890.12"));
        assertFalse(Money.isValidMoneyFormat("123.456"));
        assertFalse(Money.isValidMoneyFormat("0123.45"));
        assertFalse(Money.isValidMoneyFormat("123a45"));
        assertFalse(Money.isValidMoneyFormat("-2147483648.00"));
    }

    @Test
    public void add_sumTooLarge_throwsMoneyLimitExceededException() {
        Money a = new Money(Integer.MAX_VALUE, 99);
        Money b = new Money(0, 1);
        Money c = new Money(2000000000, 0);
        Money d = new Money(1000000000, 0);
        Money e = new Money(-2000000000, 0);

        Assert.assertThrows(MoneyLimitExceededException.class, () -> a.add(b));
        Assert.assertThrows(MoneyLimitExceededException.class, () -> c.add(d));
        Assert.assertThrows(MoneyLimitExceededException.class, () -> d.add(d).add(d));
        Assert.assertThrows(MoneyLimitExceededException.class, () -> a.add(b));
        Assert.assertThrows(MoneyLimitExceededException.class, () -> c.add(d));
        Assert.assertThrows(MoneyLimitExceededException.class, () -> d.add(d).add(d));
        Assert.assertThrows(MoneyLimitExceededException.class, () -> e.add(e));
    }

    @Test
    public void add_validInput_success() {
        Money a = new Money(475, 9);
        Money b = new Money(123, 89);
        Money c = new Money(684, 34);
        Money d = new Money(-97, 68);
        Money e = new Money(-765, 80);
        Money f = new Money(-890, 10);
        assertEquals(a.add(b), new Money(598, 98));
        assertEquals(b.add(c), new Money(808, 23));
        assertEquals(a.add(d), new Money(377, 41));
        assertEquals(d.add(b), new Money(26, 21));
        assertEquals(d.add(e), new Money(-863, 48));
        assertEquals(e.add(c), new Money(-81, 46));
        assertEquals(b.add(f), new Money(-766, 21));
        assertEquals(e.add(f), new Money(-1655, 90));
    }
}
