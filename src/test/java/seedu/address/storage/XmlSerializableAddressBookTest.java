package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.AddressBook;

public class XmlSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableAddressBookTest");
    private static final Path TYPICAL_GUESTS_FILE = TEST_DATA_FOLDER.resolve("typicalGuestsAddressBook.xml");
    private static final Path INVALID_GUEST_FILE = TEST_DATA_FOLDER.resolve("invalidGuestAddressBook.xml");
    private static final Path DUPLICATE_GUEST_FILE = TEST_DATA_FOLDER.resolve("duplicateGuestAddressBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalGuestsFile_success() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_GUESTS_FILE,
                XmlSerializableAddressBook.class);
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalGuestsAddressBook = getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalGuestsAddressBook);
    }

    @Test
    public void toModelType_invalidGuestFile_throwsIllegalValueException() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(INVALID_GUEST_FILE,
                XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateGuests_throwsIllegalValueException() throws Exception {
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_GUEST_FILE,
                XmlSerializableAddressBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableAddressBook.MESSAGE_DUPLICATE_GUEST);
        dataFromFile.toModelType();
    }

}
