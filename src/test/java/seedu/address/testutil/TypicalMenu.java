package seedu.address.testutil;

import static seedu.address.testutil.TypicalExpenseTypes.EXPENSE_TYPE_MB01;
import static seedu.address.testutil.TypicalExpenseTypes.EXPENSE_TYPE_MB02;
import static seedu.address.testutil.TypicalExpenseTypes.EXPENSE_TYPE_MB03;
import static seedu.address.testutil.TypicalExpenseTypes.EXPENSE_TYPE_MB04;
import static seedu.address.testutil.TypicalExpenseTypes.EXPENSE_TYPE_RS01;
import static seedu.address.testutil.TypicalExpenseTypes.EXPENSE_TYPE_RS02;
import static seedu.address.testutil.TypicalExpenseTypes.EXPENSE_TYPE_RS03;
import static seedu.address.testutil.TypicalExpenseTypes.EXPENSE_TYPE_SP01;

import java.util.HashMap;
import java.util.Map;

import seedu.address.model.Menu;
import seedu.address.model.expenses.ExpenseType;

/**
 * A utility class that provides a sample {@code Menu} object to be used in tests.
 */
public class TypicalMenu {

    public static Map<String, ExpenseType> getTypicalMenuMap() {
        HashMap<String, ExpenseType> map = new HashMap<>();
        map.put(EXPENSE_TYPE_MB01.getItemNumber(), EXPENSE_TYPE_MB01);
        map.put(EXPENSE_TYPE_MB02.getItemNumber(), EXPENSE_TYPE_MB02);
        map.put(EXPENSE_TYPE_MB03.getItemNumber(), EXPENSE_TYPE_MB03);
        map.put(EXPENSE_TYPE_MB04.getItemNumber(), EXPENSE_TYPE_MB04);
        map.put(EXPENSE_TYPE_RS01.getItemNumber(), EXPENSE_TYPE_RS01);
        map.put(EXPENSE_TYPE_RS02.getItemNumber(), EXPENSE_TYPE_RS02);
        map.put(EXPENSE_TYPE_RS03.getItemNumber(), EXPENSE_TYPE_RS03);
        map.put(EXPENSE_TYPE_SP01.getItemNumber(), EXPENSE_TYPE_SP01);
        return map;
    }

    public static Menu getTypicalMenu() {
        Menu menu = new Menu();
        menu.setMenu(getTypicalMenuMap());
        return menu;
    }
}
