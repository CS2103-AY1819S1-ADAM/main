package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Identifier prefixes */
    // guest
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    // room
    public static final Prefix PREFIX_ROOM = new Prefix("r/");
    public static final Prefix PREFIX_ROOM_CAPACITY = new Prefix("c/");

    // booking
    public static final Prefix PREFIX_DATE_START = new Prefix("from/");
    public static final Prefix PREFIX_DATE_END = new Prefix("to/");

    // expense
    public static final Prefix PREFIX_ITEM_NUMBER = new Prefix("no/");
    public static final Prefix PREFIX_COST = new Prefix("c/");

    /* New value prefixes (for editing) */
    public static final Prefix PREFIX_NEW_NAME = new Prefix("nn/");
    public static final Prefix PREFIX_NEW_PHONE = new Prefix("np/");
    public static final Prefix PREFIX_NEW_EMAIL = new Prefix("ne/");
    public static final Prefix PREFIX_NEW_TAG = new Prefix("nt/");
    public static final Prefix PREFIX_NEW_ROOM = new Prefix("nr/");
    public static final Prefix PREFIX_NEW_CAPACITY = new Prefix("nc/");
    public static final Prefix PREFIX_NEW_DATE_START = new Prefix("nfrom/");
    public static final Prefix PREFIX_NEW_DATE_END = new Prefix("nto/");

    /* Flag prefixes */
    public static final Prefix FLAG_ROOM = new Prefix("-r");
    public static final Prefix FLAG_GUEST = new Prefix("-g");
    public static final Prefix FLAG_ROOM_HAS_BOOKINGS = new Prefix("-hb");
    public static final Prefix FLAG_ROOM_NO_BOOKINGS = new Prefix("-nb");
    public static final Prefix FLAG_CHECKED_IN_GUEST = new Prefix("-cg");

    /* Login prefixes */
    public static final Prefix PREFIX_USERNAME = new Prefix("user/");
    public static final Prefix PREFIX_PASSWORD = new Prefix("pw/");
}
