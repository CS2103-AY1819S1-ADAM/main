package cs2103.concierge.commons.events.storage;

import cs2103.concierge.commons.events.BaseEvent;

/**
 * Indicates an exception during a file saving
 */
public class DataSavingExceptionEvent extends BaseEvent {

    public final Exception exception;

    public DataSavingExceptionEvent(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return exception.toString();
    }

}
