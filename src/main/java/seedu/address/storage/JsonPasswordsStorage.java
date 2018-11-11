package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
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
    public Optional<PasswordHashList> readPasswordRef() throws DataConversionException {
        return JsonUtil.readJsonFile(filePath, PasswordHashList.class);
    }

    @Override
    public void savePasswordRef(PasswordHashList passwordHashList) throws IOException {
        JsonUtil.saveJsonFile(passwordHashList, filePath);
    }

}
