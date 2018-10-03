package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalGuests.getTypicalGuestList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.guest.Guest;
import seedu.address.testutil.GuestBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalGuestList(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Guest validGuest = new GuestBuilder().build();

        Model expectedModel = new ModelManager(model.getGuestList(), new UserPrefs());
        expectedModel.addGuest(validGuest);
        expectedModel.commitGuestList();

        assertCommandSuccess(new AddCommand(validGuest), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validGuest), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Guest guestInList = model.getGuestList().getListOfGuests().get(0);
        assertCommandFailure(new AddCommand(guestInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_GUEST);
    }

}
