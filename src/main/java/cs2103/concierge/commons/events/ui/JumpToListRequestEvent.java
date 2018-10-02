package cs2103.concierge.commons.events.ui;

import cs2103.concierge.commons.events.BaseEvent;
import cs2103.concierge.commons.core.index.Index;

/**
 * Indicates a request to jump to the list of persons
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
