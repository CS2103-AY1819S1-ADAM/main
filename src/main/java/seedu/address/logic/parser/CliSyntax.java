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
    public static final Prefix PREFIX_DATE_START = new Prefix("from/");
    public static final Prefix PREFIX_DATE_END = new Prefix("to/");
    public static final Prefix PREFIX_ROOM = new Prefix("r/");
    public static final Prefix FLAG_ROOM = new Prefix("-r");
    public static final Prefix FLAG_GUEST = new Prefix("-g");
}
