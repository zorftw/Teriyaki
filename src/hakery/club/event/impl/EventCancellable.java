package hakery.club.event.impl;

import hakery.club.event.CancellableEvent;
import hakery.club.event.Event;

public class EventCancellable implements Event, CancellableEvent {

    private boolean cancelled;

    @Override
    public boolean cancelled() {
        return cancelled;
    }

    @Override
    public void set_cancelled(boolean state) {
        this.cancelled = state;
    }
}
