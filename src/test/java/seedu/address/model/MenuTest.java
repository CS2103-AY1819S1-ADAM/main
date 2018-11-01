package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import seedu.address.model.expenses.exceptions.ItemNotFoundException;
import seedu.address.testutil.Assert;
import seedu.address.testutil.TypicalMenu;

public class MenuTest {

    private static final Menu validMenu = new Menu();

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Menu(null));
    }

    @Test
    public void constructor_validMenu_success() {
        Menu otherMenu = TypicalMenu.getTypicalMenu();
        Menu newMenu = new Menu(otherMenu);
        assertEquals(otherMenu, newMenu);
    }

    @Test
    public void setMenu_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> validMenu.setMenu(null));
    }

    @Test
    public void isValidMenuNumber_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> validMenu.isValidMenuNumber(null));
    }

    @Test
    public void isValidMenuNumber_invalidString_returnsFalse() {
        assertFalse(validMenu.isValidMenuNumber("-"));
    }

    @Test
    public void getExpenseType_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> validMenu.getExpenseType(null));
    }

    @Test
    public void getExpenseType_invalidString_throwsItemNotFoundException() {
        Assert.assertThrows(ItemNotFoundException.class, () -> validMenu.getExpenseType("-"));
    }

    @Test
    public void equals_test() {
        Menu menu1 = TypicalMenu.getTypicalMenu();
        Menu menu2 = new Menu();
        assertEquals(menu1, menu1);
        assertNotEquals(menu1, menu2);
    }

    @Test
    public void hashCode_test() {
        Menu menu1 = TypicalMenu.getTypicalMenu();
        Menu menu2 = new Menu();
        menu2.setMenu(menu1.asUnmodifiableMap());
        assertEquals(menu1.hashCode(), menu2.hashCode());
    }
}
