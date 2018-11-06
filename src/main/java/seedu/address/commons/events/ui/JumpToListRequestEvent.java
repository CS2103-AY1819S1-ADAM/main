package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;
import seedu.address.logic.parser.Prefix;

/**
 * Indicates a request to jump to the list of guests
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final int targetIndex;
    public final Prefix flag;

    public JumpToListRequestEvent(Index targetIndex, Prefix flag) {
        this.targetIndex = targetIndex.getZeroBased();
        this.flag = flag;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
