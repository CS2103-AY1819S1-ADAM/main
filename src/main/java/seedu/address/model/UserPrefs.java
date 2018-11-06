package seedu.address.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private Path conciergeFilePath = Paths.get("data" , "concierge.xml");
    private Path passwordsFilePath = Paths.get("passwords.json");

    public UserPrefs() {
        setGuiSettings(500, 500, 0, 0);
    }

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    public Path getConciergeFilePath() {
        return conciergeFilePath;
    }

    public void setConciergeFilePath(Path conciergeFilePath) {
        this.conciergeFilePath = conciergeFilePath;
    }

    public Path getPasswordsFilePath() {
        return passwordsFilePath;
    }

    public void setPasswordsFilePath(Path passwordsFilePath) {
        this.passwordsFilePath = passwordsFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return Objects.equals(guiSettings, o.guiSettings)
                && Objects.equals(conciergeFilePath, o.conciergeFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, conciergeFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + conciergeFilePath);
        return sb.toString();
    }

}
