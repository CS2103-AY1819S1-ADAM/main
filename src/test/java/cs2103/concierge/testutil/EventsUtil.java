package cs2103.concierge.testutil;

import cs2103.concierge.commons.core.EventsCenter;
import cs2103.concierge.commons.events.BaseEvent;
import guitests.GuiRobot;
import javafx.application.Platform;

/**
 * Helper methods related to events.
 */
public class EventsUtil {
    /**
     * Posts {@code event} to all registered subscribers. This method will return successfully after the {@code event}
     * has been posted to all subscribers.
     */
    public static void postNow(BaseEvent event) {
        new GuiRobot().interact(() -> EventsCenter.getInstance().post(event));
    }

    /**
     * Posts {@code event} to all registered subscribers at some unspecified time in the future.
     */
    public static void postLater(BaseEvent event) {
        Platform.runLater(() -> EventsCenter.getInstance().post(event));
    }
}
