package seedu.address.commons.core;

import seedu.address.model.room.RoomNumber;

/**
 * Container for user visible messages.
 */
public class Messages {
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_GUEST_DISPLAYED_INDEX = "The guest index provided is invalid";
    public static final String MESSAGE_INVALID_CHECKED_IN_GUEST_DISPLAYED_INDEX =
            "The checked-in guest index provided is invalid";
    public static final String MESSAGE_INVALID_ROOM_DISPLAYED_INDEX = "The room index provided is invalid";
    public static final String MESSAGE_INVALID_DATE = "The date provided is invalid";
    public static final String MESSAGE_NO_LIST_DISPLAYED = "No list is being displayed";
    public static final String MESSAGE_GUESTS_LISTED_OVERVIEW = "%1$d guests listed";
    public static final String MESSAGE_ROOMS_LISTED_OVERVIEW = "%1$d rooms listed";

    public static final String MESSAGE_VALID_ROOM =
            "The room number provided must be a 3-digit positive integer from 001 to "
            + RoomNumber.MAX_ROOM_NUMBER + "\n";
    public static final String MESSAGE_VALID_DATE =
            "The date provided must be in d/M/y format and valid according to the Gregorian calendar. "
            + "Day and Month can be 1 or 2 digits, Year can be 2 or 4 digits.\n";

}
