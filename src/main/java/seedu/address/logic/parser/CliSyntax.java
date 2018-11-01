package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_ROOM = new Prefix("r/");
    public static final Prefix PREFIX_DATE_START = new Prefix("from/");
    public static final Prefix PREFIX_DATE_END = new Prefix("to/");
    public static final Prefix PREFIX_ITEM_NUMBER = new Prefix("no/");
    public static final Prefix PREFIX_COST = new Prefix("c/");
    public static final Prefix FLAG_ROOM = new Prefix("-r");
    public static final Prefix FLAG_GUEST = new Prefix("-g");
    public static final Prefix FLAG_CHECKED_IN_GUEST = new Prefix("-cg");

    public static final Prefix PREFIX_USERNAME = new Prefix("user/");
    public static final Prefix PREFIX_PASSWORD = new Prefix("pw/");
}
