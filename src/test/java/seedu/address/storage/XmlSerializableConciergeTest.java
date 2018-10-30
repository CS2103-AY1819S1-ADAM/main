package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalConcierge.getTypicalConcierge;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.Concierge;
import seedu.address.testutil.TypicalConcierge;

public class XmlSerializableConciergeTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableConciergeTest");
    private static final Path TYPICAL_CONCIERGE_FILE = TEST_DATA_FOLDER.resolve("typicalConcierge.xml");
    private static final Path INVALID_GUEST_FILE = TEST_DATA_FOLDER.resolve("invalidGuestConcierge.xml");
    private static final Path DUPLICATE_GUEST_FILE = TEST_DATA_FOLDER.resolve("duplicateGuestConcierge.xml");

    private static final Concierge VALID_CONCIERGE = TypicalConcierge.getTypicalConciergeWithRoomBookings();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setupValidXmlTestFiles() throws Exception {
        XmlUtil.saveDataToFile(TYPICAL_CONCIERGE_FILE, new XmlSerializableConcierge(VALID_CONCIERGE));
    }

    @Test
    public void toModelType_typicalGuestsFile_success() throws Exception {
        XmlSerializableConcierge dataFromFile = XmlUtil.getDataFromFile(TYPICAL_CONCIERGE_FILE,
                XmlSerializableConcierge.class);
        Concierge conciergeFromFile = dataFromFile.toModelType();
        Concierge typicalGuestsConcierge = getTypicalConcierge();
        assertEquals(conciergeFromFile, typicalGuestsConcierge);
    }

    @Test
    public void toModelType_invalidGuestFile_throwsIllegalValueException() throws Exception {
        XmlSerializableConcierge dataFromFile = XmlUtil.getDataFromFile(INVALID_GUEST_FILE,
                XmlSerializableConcierge.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateGuests_throwsIllegalValueException() throws Exception {
        XmlSerializableConcierge dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_GUEST_FILE,
                XmlSerializableConcierge.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableConcierge.MESSAGE_DUPLICATE_GUEST);
        dataFromFile.toModelType();
    }

}
