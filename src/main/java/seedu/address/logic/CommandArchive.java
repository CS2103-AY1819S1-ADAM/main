package seedu.address.logic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.util.FileUtil;
import seedu.address.model.expenses.Expense;

/**
 * Append all commands that user entered into command archive
 */
public class CommandArchive {
    /**
     * Create a logger without hard-coding the class name
     */
    private static final Logger LOGGER = Logger.getLogger(
            Thread.currentThread().getStackTrace()[0].getClassName());

    /**
     * Takes in the userInputHistory string, extracts the latest command and writes it to commandFile.txt with a
     */
    public static void stringToFile (String inputString) {
        Path commandHistory = Paths.get("commands_log", "commands_log.txt");
        try {
            FileUtil.createIfMissing(commandHistory);

            String latestUserCommand = inputString.substring(0, inputString.indexOf(
                System.getProperty("line.separator")));
            String timeStamp = LocalDateTime.now().format(Expense.DATETIME_FORMAT) + "  ";

            FileWriter fileWriter = new FileWriter(commandHistory.toFile(), true);
            BufferedWriter buffer = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(buffer);
            printWriter.print(timeStamp);
            printWriter.print(latestUserCommand);
            printWriter.print(System.getProperty("line.separator"));
            printWriter.close();

        } catch (IOException e) {
            LOGGER.log(Level.INFO, "An IOException was caught", e);
        }
    }

}
