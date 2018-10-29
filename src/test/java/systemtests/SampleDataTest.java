package systemtests;

import static seedu.address.ui.testutil.GuiTestAssert.assertGuestListMatching;
import static seedu.address.ui.testutil.GuiTestAssert.assertRoomListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.Test;

import seedu.address.model.Concierge;
import seedu.address.model.guest.Guest;
import seedu.address.model.room.Room;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.TestUtil;

public class SampleDataTest extends ConciergeSystemTest {
    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */
    @Override
    protected Concierge getInitialData() {
        return null;
    }

    /**
     * Returns a non-existent file location to force test app to load sample data.
     */
    @Override
    protected Path getDataFileLocation() {
        Path filePath = TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
        deleteFileIfExists(filePath);
        return filePath;
    }

    /**
     * Deletes the file at {@code filePath} if it exists.
     */
    private void deleteFileIfExists(Path filePath) {
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException ioe) {
            throw new AssertionError(ioe);
        }
    }

    @Test
    public void concierge_dataFileDoesNotExist_loadSampleData() {
        Guest[] expectedGuestList = SampleDataUtil.getSampleGuests();
        assertGuestListMatching(getGuestListPanel(), expectedGuestList);

        List<Room> expectedRoomList =
                SampleDataUtil.getSampleRooms();
        assertRoomListMatching(getRoomListPanel(), expectedRoomList);

    }
}
