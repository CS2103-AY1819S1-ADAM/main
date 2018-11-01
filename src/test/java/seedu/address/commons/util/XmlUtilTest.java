package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.Concierge;
import seedu.address.model.guest.Guest;
import seedu.address.model.room.Capacity;
import seedu.address.model.room.Room;
import seedu.address.storage.XmlAdaptedBooking;
import seedu.address.storage.XmlAdaptedExpense;
import seedu.address.storage.XmlAdaptedGuest;
import seedu.address.storage.XmlAdaptedRoom;
import seedu.address.storage.XmlAdaptedTag;
import seedu.address.storage.XmlSerializableConcierge;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalConcierge;

public class XmlUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validConcierge.xml");
    private static final Path VALID_GUEST_FILE = TEST_DATA_FOLDER.resolve("validGuest.xml");
    private static final Path VALID_ROOM_FILE = TEST_DATA_FOLDER.resolve("validRoom.xml");

    private static final Path MISSING_GUEST_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingGuestField.xml");
    private static final Path MISSING_ROOM_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingRoomField.xml");
    private static final Path INVALID_GUEST_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidGuestField.xml");
    private static final Path INVALID_ROOM_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidRoomField.xml");

    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempConcierge.xml");

    private static final Concierge VALID_CONCIERGE = TypicalConcierge.getTypicalConcierge();

    private static final Guest VALID_GUEST = VALID_CONCIERGE.getGuestList().get(0);
    private static final String VALID_NAME = VALID_GUEST.getName().toString();
    private static final String VALID_PHONE = VALID_GUEST.getPhone().toString();
    private static final String VALID_EMAIL = VALID_GUEST.getEmail().toString();
    private static final List<XmlAdaptedTag> VALID_GUEST_TAGS = VALID_GUEST.getTags()
            .stream().map(XmlAdaptedTag::new).collect(Collectors.toList());

    private static final String INVALID_PHONE = "9482asf424";

    private static final Room VALID_ROOM = VALID_CONCIERGE.getRoomList().get(0);
    private static final String VALID_ROOM_NUMBER = VALID_ROOM.getRoomNumber().toString();
    private static final Capacity VALID_CAPACITY = VALID_ROOM.getCapacity();
    private static final List<XmlAdaptedBooking> VALID_BOOKINGS = VALID_ROOM.getBookings().getSortedBookingsSet()
            .stream().map(XmlAdaptedBooking::new).collect(Collectors.toList());
    private static final List<XmlAdaptedExpense> VALID_EXPENSES = VALID_ROOM.getExpenses().getExpensesList()
            .stream().map(XmlAdaptedExpense::new).collect(Collectors.toList());
    private static final List<XmlAdaptedTag> VALID_ROOM_TAGS = VALID_ROOM.getTags()
            .stream().map(XmlAdaptedTag::new).collect(Collectors.toList());
    private static final int VALID_CONCIERGE_NUM_GUESTS = VALID_CONCIERGE.getGuestList().size();
    private static final int VALID_CONCIERGE_NUM_ROOMS = VALID_CONCIERGE.getRoomList().size();

    private static final String INVALID_ROOM_NUMBER = "01";
    private static final XmlAdaptedRoom INVALID_ROOM_FIELD_ROOM = new XmlAdaptedRoom(INVALID_ROOM_NUMBER,
            VALID_CAPACITY, VALID_BOOKINGS, VALID_EXPENSES, VALID_ROOM_TAGS);
    private static final XmlAdaptedGuest INVALID_GUEST_FIELD_GUEST = new XmlAdaptedGuest(
            VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_GUEST_TAGS);
    private static final List<XmlAdaptedBooking> EMPTY_BOOKINGS = new ArrayList<>();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setupValidXmlTestFiles() throws Exception {
        XmlUtil.saveDataToFile(VALID_FILE, new XmlSerializableConcierge(VALID_CONCIERGE));
        XmlUtil.saveDataToFile(VALID_ROOM_FILE, new XmlAdaptedRoom(VALID_ROOM));
        XmlUtil.saveDataToFile(VALID_GUEST_FILE, new XmlAdaptedGuest(VALID_GUEST));
        XmlUtil.saveDataToFile(INVALID_ROOM_FIELD_FILE, INVALID_ROOM_FIELD_ROOM);
        XmlUtil.saveDataToFile(INVALID_GUEST_FIELD_FILE, INVALID_GUEST_FIELD_GUEST);
    }

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, Concierge.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, Concierge.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, Concierge.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        Concierge dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableConcierge.class).toModelType();
        assertEquals(VALID_CONCIERGE_NUM_GUESTS, dataFromFile.getGuestList().size());
        assertEquals(VALID_CONCIERGE_NUM_ROOMS, dataFromFile.getRoomList().size());
    }

    @Test
    public void xmlAdaptedGuestFromFile_fileWithMissingGuestField_validResult() throws Exception {
        XmlAdaptedGuest actualGuest = XmlUtil.getDataFromFile(
                MISSING_GUEST_FIELD_FILE, XmlAdaptedGuestWithRootElement.class);
        XmlAdaptedGuest expectedGuest = new XmlAdaptedGuest(null, VALID_PHONE, VALID_EMAIL, VALID_GUEST_TAGS);
        assertEquals(expectedGuest, actualGuest);
    }

    /**
     * We do not compare Bookings here because the ever-changing booking periods will cause a mismatch between
     * the expected room and the MISSING_ROOM_FIELD_FILE. There is no way to write a MISSING_ROOM_FIELD_FILE, because
     * JAXB and Marshaller does not allow null for XmlElements that are tagged with (required=true). As such,
     * MISSING_ROOM_FIELD_FILE is only human editable, which leads to the problem of not having up-to-date
     * dates. Therefore, both expectedRoom and MISSING_ROOM_FIELD_FILE will NOT contain any bookings (i.e. empty).
     * @throws Exception
     */
    @Test
    public void xmlAdaptedRoomFromFile_fileWithMissingRoomField_validResult() throws Exception {
        XmlAdaptedRoom actualRoom = XmlUtil.getDataFromFile(
                MISSING_ROOM_FIELD_FILE, XmlAdaptedRoomWithRootElement.class);
        XmlAdaptedRoom expectedRoom = new XmlAdaptedRoom(VALID_ROOM_NUMBER, null, EMPTY_BOOKINGS,
                VALID_EXPENSES, VALID_ROOM_TAGS);
        assertEquals(expectedRoom, actualRoom);
    }

    @Test
    public void xmlAdaptedGuestFromFile_fileWithInvalidGuestField_validResult() throws Exception {
        XmlAdaptedGuest actualGuest = XmlUtil.getDataFromFile(
                INVALID_GUEST_FIELD_FILE, XmlAdaptedGuestWithRootElement.class);
        XmlAdaptedGuest expectedGuest = INVALID_GUEST_FIELD_GUEST;
        assertEquals(expectedGuest, actualGuest);
    }

    @Test
    public void xmlAdaptedRoomFromFile_fileWithInvalidRoomField_validResult() throws Exception {
        XmlAdaptedRoom actualRoom = XmlUtil.getDataFromFile(
                INVALID_ROOM_FIELD_FILE, XmlAdaptedRoomWithRootElement.class);
        XmlAdaptedRoom expectedRoom = INVALID_ROOM_FIELD_ROOM;
        assertEquals(expectedRoom, actualRoom);
    }

    @Test
    public void xmlAdaptedGuestFromFile_fileWithValidGuest_validResult() throws Exception {
        XmlAdaptedGuest actualGuest = XmlUtil.getDataFromFile(
                VALID_GUEST_FILE, XmlAdaptedGuestWithRootElement.class);
        XmlAdaptedGuest expectedGuest = new XmlAdaptedGuest(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_GUEST_TAGS);
        assertEquals(expectedGuest, actualGuest);
    }

    @Test
    public void xmlAdaptedRoomFromFile_fileWithValidRoom_validResult() throws Exception {
        XmlAdaptedRoom actualRoom = XmlUtil.getDataFromFile(
                VALID_ROOM_FILE, XmlAdaptedRoomWithRootElement.class);
        XmlAdaptedRoom expectedRoom = new XmlAdaptedRoom(VALID_ROOM);
        assertEquals(expectedRoom, actualRoom);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new Concierge());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new Concierge());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_FILE);
        XmlSerializableConcierge dataToWrite = new XmlSerializableConcierge(new Concierge());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableConcierge dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableConcierge.class);
        assertEquals(dataToWrite, dataFromFile);

        dataToWrite = new XmlSerializableConcierge(TypicalConcierge.getTypicalConciergeClean());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableConcierge.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedGuest}
     * objects.
     */
    @XmlRootElement(name = "guests")
    private static class XmlAdaptedGuestWithRootElement extends XmlAdaptedGuest {}

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedRoom}
     * objects.
     */
    @XmlRootElement(name = "rooms")
    private static class XmlAdaptedRoomWithRootElement extends XmlAdaptedRoom {}
}
