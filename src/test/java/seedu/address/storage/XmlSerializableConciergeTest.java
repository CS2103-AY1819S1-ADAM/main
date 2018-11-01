package seedu.address.storage;

import static org.junit.Assert.assertEquals;

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
    private static final Path DUPLICATE_ITEM_FILE = TEST_DATA_FOLDER.resolve("duplicateItemConcierge.xml");
    private static final Path INVALID_ROOM_FILE = TEST_DATA_FOLDER.resolve("invalidRoomConcierge.xml");
    private static final Path DUPLICATE_ROOM_FILE = TEST_DATA_FOLDER.resolve("duplicateRoomConcierge.xml");
    private static final Path MISSING_ROOM_FILE = TEST_DATA_FOLDER.resolve("missingRoomConcierge.xml");
    private static final Path OVERLAPPING_BOOKING_FILE = TEST_DATA_FOLDER.resolve("overlappingBookingConcierge.xml");
    private static final Path INVALID_EXPENSE_FILE = TEST_DATA_FOLDER.resolve("invalidExpenseConcierge.xml");
    private static final Path INVALID_ITEM_FILE = TEST_DATA_FOLDER.resolve("invalidItemConcierge.xml");

    private static final Concierge TYPICAL_CONCIERGE = TypicalConcierge.getTypicalConcierge();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setupValidXmlTestFiles() throws Exception {
        XmlUtil.saveDataToFile(TYPICAL_CONCIERGE_FILE, new XmlSerializableConcierge(TYPICAL_CONCIERGE));
    }

    @Test
    public void toModelType_typicalConciergeFile_success() throws Exception {
        XmlSerializableConcierge dataFromFile = XmlUtil.getDataFromFile(TYPICAL_CONCIERGE_FILE,
                XmlSerializableConcierge.class);
        Concierge conciergeFromFile = dataFromFile.toModelType();
        assertEquals(conciergeFromFile, TYPICAL_CONCIERGE);
    }

    @Test
    public void toModelType_invalidGuestFile_throwsIllegalValueException() throws Exception {
        XmlSerializableConcierge dataFromFile = XmlUtil.getDataFromFile(INVALID_GUEST_FILE,
                XmlSerializableConcierge.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidRoomFile_throwsIllegalValueException() throws Exception {
        XmlSerializableConcierge dataFromFile = XmlUtil.getDataFromFile(INVALID_ROOM_FILE,
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

    @Test
    public void toModelType_duplicateItem_throwsIllegalValueException() throws Exception {
        XmlSerializableConcierge dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_ITEM_FILE,
                XmlSerializableConcierge.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableConcierge.MESSAGE_DUPLICATE_ITEM);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateRooms_throwsIllegalValueException() throws Exception {
        XmlSerializableConcierge dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_ROOM_FILE,
                XmlSerializableConcierge.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableConcierge.MESSAGE_DUPLICATE_ROOM);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_missingRooms_throwsIllegalValueException() throws Exception {
        XmlSerializableConcierge dataFromFile = XmlUtil.getDataFromFile(MISSING_ROOM_FILE,
                XmlSerializableConcierge.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableConcierge.MESSAGE_ROOM_MISSING);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_overlappingBooking_throwsIllegalValueException() throws Exception {
        XmlSerializableConcierge dataFromFile = XmlUtil.getDataFromFile(OVERLAPPING_BOOKING_FILE,
                XmlSerializableConcierge.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlAdaptedRoom.MESSAGE_OVERLAPPING_BOOKING);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidExpenseFile_throwsIllegalValueException() throws Exception {
        XmlSerializableConcierge dataFromFile = XmlUtil.getDataFromFile(INVALID_EXPENSE_FILE,
                XmlSerializableConcierge.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlAdaptedExpense.MESSAGE_INVALID_COST);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidItemFile_throwsIllegalValueException() throws Exception {
        XmlSerializableConcierge dataFromFile = XmlUtil.getDataFromFile(INVALID_ITEM_FILE,
                XmlSerializableConcierge.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlAdaptedExpense.MESSAGE_INVALID_COST);
        dataFromFile.toModelType();
    }
}
