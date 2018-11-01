package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.commons.util.FileUtil;
import seedu.address.model.login.PasswordHashList;

/**
 * A class to access the passwords stored in the hard disk as a json file
 */
public class JsonPasswordsStorage implements PasswordsStorage {

    private Path filePath;

    public JsonPasswordsStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getPasswordsFilePath() {
        return filePath;
    }

    @Override
    public PasswordHashList getPasswordHashList() throws IOException {
        String fileContent = FileUtil.readFromFile(filePath);
        return new PasswordHashList(fileContent);
    }

}
