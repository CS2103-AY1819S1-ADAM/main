package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.GuestList;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyGuestList;
import seedu.address.model.guest.Guest;
import seedu.address.testutil.GuestBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Guest validGuest = new GuestBuilder().build();

        CommandResult commandResult = new AddCommand(validGuest).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validGuest), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validGuest), modelStub.personsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Guest validGuest = new GuestBuilder().build();
        AddCommand addCommand = new AddCommand(validGuest);
        ModelStub modelStub = new ModelStubWithPerson(validGuest);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_GUEST);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Guest alice = new GuestBuilder().withName("Alice").build();
        Guest bob = new GuestBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different guest -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addGuest(Guest guest) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyGuestList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyGuestList getGuestList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasGuest(Guest guest) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteGuest(Guest target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateGuest(Guest target, Guest editedGuest) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Guest> getFilteredGuestList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredGuestList(Predicate<Guest> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoGuestList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoGuestList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoGuestList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoGuestList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitGuestList() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single guest.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Guest guest;

        ModelStubWithPerson(Guest guest) {
            requireNonNull(guest);
            this.guest = guest;
        }

        @Override
        public boolean hasGuest(Guest guest) {
            requireNonNull(guest);
            return this.guest.isSameGuest(guest);
        }
    }

    /**
     * A Model stub that always accept the guest being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Guest> personsAdded = new ArrayList<>();

        @Override
        public boolean hasGuest(Guest guest) {
            requireNonNull(guest);
            return personsAdded.stream().anyMatch(guest::isSameGuest);
        }

        @Override
        public void addGuest(Guest guest) {
            requireNonNull(guest);
            personsAdded.add(guest);
        }

        @Override
        public void commitGuestList() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyGuestList getGuestList() {
            return new GuestList();
        }
    }

}
