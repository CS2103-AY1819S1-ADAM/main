package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.guest.Guest;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.Booking;
import seedu.address.model.room.booking.BookingPeriod;
import seedu.address.model.room.booking.exceptions.OverlappingBookingException;
import seedu.address.model.room.exceptions.RoomNotFoundException;

/**
 * Adds a guest to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a guest to the hotel and gives the guest a room. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_ROOM + " ROOM NUMBER "
            + PREFIX_DATE_START + " dd/MM/yyyy "
            + PREFIX_DATE_END + " dd/MM/yyyy "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_ROOM + "056"
            + PREFIX_DATE_START + "03/11/2018"
            + PREFIX_DATE_END + "05/11/2018";

    public static final String MESSAGE_SUCCESS =
            "New guest added: %1$s \nAssigned to room: %2$s \n\tfrom %3$s";
    public static final String MESSAGE_DUPLICATE_GUEST = "This guest already exists in the address book";

    private final Guest guestToAdd;
    private final RoomNumber roomNumberToAdd;
    private final Booking bookingToAdd;

    /**
     * Creates an AddCommand to add the specified {@code Guest}.
     * The {@code guest} is assigned to {@code roomNumber} for the duration of
     * {@code bookingPeriod}.
     */
    public AddCommand(Guest guest, RoomNumber roomNumber,
                      BookingPeriod bookingPeriod) {
        requireAllNonNull(guest, roomNumber, bookingPeriod);
        guestToAdd = guest;
        roomNumberToAdd = roomNumber;
        bookingToAdd = new Booking(guest, bookingPeriod);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasGuest(guestToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_GUEST);
        }
        model.addGuest(guestToAdd);

        try {
            model.addBooking(roomNumberToAdd, bookingToAdd);
        } catch (RoomNotFoundException e) {
            throw new CommandException(e.getMessage());
        } catch (OverlappingBookingException e) {
            throw new CommandException(e.getMessage());
        }

        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, guestToAdd,
                roomNumberToAdd, bookingToAdd.getBookingPeriod()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && guestToAdd.equals(((AddCommand) other).guestToAdd));
    }
}
