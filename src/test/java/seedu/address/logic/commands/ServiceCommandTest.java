package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Menu;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyConcierge;
import seedu.address.model.expenses.Expense;
import seedu.address.model.expenses.ExpenseType;
import seedu.address.model.expenses.Money;
import seedu.address.model.guest.Guest;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.Booking;
import seedu.address.model.room.booking.exceptions.NoBookingException;
import seedu.address.model.room.booking.exceptions.RoomNotCheckedInException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.TypicalRoomNumbers;

public class ServiceCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final RoomNumber VALID_ROOM_NUMBER = TypicalRoomNumbers.ROOM_NUMBER_001;
    private static final String VALID_ITEM_NUMBER = "1";
    private static final Optional<Money> VALID_ITEM_COST = Optional.of(new Money(1, 23));
    private static final ServiceCommand VALID_SERVICE_COMMAND = new ServiceCommand(
            VALID_ROOM_NUMBER, VALID_ITEM_NUMBER, VALID_ITEM_COST);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullRoomNumber_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ServiceCommand(null, VALID_ITEM_NUMBER, VALID_ITEM_COST);
    }

    @Test
    public void constructor_nullItemNumber_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ServiceCommand(VALID_ROOM_NUMBER, null, VALID_ITEM_COST);
    }

    @Test
    public void constructor_nullItemCost_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ServiceCommand(VALID_ROOM_NUMBER, VALID_ITEM_NUMBER, null);
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        VALID_SERVICE_COMMAND.execute(null, null);
    }

    @Test
    public void execute_roomHasItemButNoBookings_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(ServiceCommand.MESSAGE_ROOM_HAS_NO_GUEST);
        Model model = new ModelStubHasItemButNoBookings();
        VALID_SERVICE_COMMAND.execute(model, EMPTY_COMMAND_HISTORY);
    }

    @Test
    public void execute_roomHasItemAndBookingsButNotCheckedIn_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(ServiceCommand.MESSAGE_ROOM_HAS_NO_GUEST);
        Model model = new ModelStubHasItemAndBookingsNotCheckedIn();
        VALID_SERVICE_COMMAND.execute(model, EMPTY_COMMAND_HISTORY);
    }

    @Test
    public void execute_invalidItemNumber_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(ServiceCommand.MESSAGE_ITEM_NOT_FOUND);
        Model model = new ModelStubWithBookingsAndCheckedInButNoItem();
        // new menu has no items, any item number is invalid.
        VALID_SERVICE_COMMAND.execute(model, EMPTY_COMMAND_HISTORY);
    }

    @Test
    public void execute_expenseAcceptedByModel_serviceSuccess() throws Exception {
        Model model = new ModelStubCanService();
        CommandResult commandResult = VALID_SERVICE_COMMAND.execute(model, EMPTY_COMMAND_HISTORY);
        String expectedCommandResultFeedback =
                String.format(ServiceCommand.MESSAGE_SUCCESS, "-", VALID_ROOM_NUMBER.toString());
        assertEquals(commandResult.feedbackToUser, expectedCommandResultFeedback);
    }

    /**
     * A default model stub that have all of the methods failing,
     * except getMenu(), which is always called by ServiceCommand.execute().
     */
    private class ModelStub implements Model {
        @Override
        public void addGuest(Guest guest) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyConcierge newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyConcierge getConcierge() {
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
        public ObservableList<Room> getFilteredRoomList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredRoomList(Predicate<Room> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoConcierge() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoConcierge() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoConcierge() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoConcierge() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitConcierge() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void checkInRoom(RoomNumber roomNumber) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void checkoutRoom(RoomNumber roomNumber) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void checkoutRoom(RoomNumber roomNumber, LocalDate localDate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addBooking(RoomNumber roomNumber, Booking booking) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addExpense(RoomNumber roomNumber, Expense expense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Menu getMenu() {
            return new Menu();
        }

        @Override
        public void addRoomTags(RoomNumber roomNumber, Tag... tags) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void reassignRoom(RoomNumber roomNumber, LocalDate startDate, RoomNumber newRoomNumber) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isSignedIn() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<String> getUsername() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void signIn(String username, String hashedPassword) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void signOut() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Guest> getFilteredCheckedInGuestList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCheckedInGuestList(Predicate<Guest> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Prefix getDisplayedListFlag() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDisplayedListFlag(Prefix flag) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that does not have any bookings.
     */
    private class ModelStubHasItemButNoBookings extends ModelStub {
        @Override
        public Menu getMenu() {
            return getValidMenu();
        }

        @Override
        public void addExpense(RoomNumber roomNumber, Expense expense) {
            throw new NoBookingException();
        }
    }

    /**
     * A Model stub with bookings but no one checked in.
     */
    private class ModelStubHasItemAndBookingsNotCheckedIn extends ModelStub {
        @Override
        public Menu getMenu() {
            return getValidMenu();
        }

        @Override
        public void addExpense(RoomNumber roomNumber, Expense expense) {
            throw new RoomNotCheckedInException();
        }
    }

    /**
     * A Model stub with bookings all checked in.
     */
    private class ModelStubWithBookingsAndCheckedInButNoItem extends ModelStub {
        @Override
        public void addExpense(RoomNumber roomNumber, Expense expense) {
            // called by {@code ServiceCommand#execute()}
        }
    }

    /**
     * A Model stub that allows a VALID_SERVICE_COMMAND to be executed.
     * Rooms always have booking, rooms are always checked in,
     * Menu contains an item with VALID_ITEM_NUMBER.
     */
    private class ModelStubCanService extends ModelStub {
        @Override
        public Menu getMenu() {
            return getValidMenu();
        }

        @Override
        public void addExpense(RoomNumber roomNumber, Expense expense) {
            // called by {@code ServiceCommand#execute()}
        }

        @Override
        public void commitConcierge() {
            // called by {@code ServiceCommand#execute()}
        }
    }

    /**
     * Generates a menu that has the required expense type used throughout this test class.
     */
    private Menu getValidMenu() {
        HashMap<String, ExpenseType> validMap = new HashMap<>();
        validMap.put(VALID_ITEM_NUMBER, new ExpenseType(VALID_ITEM_NUMBER, "-", VALID_ITEM_COST.get()));
        Menu menu = new Menu();
        menu.setMenu(validMap);
        return menu;
    }
}
