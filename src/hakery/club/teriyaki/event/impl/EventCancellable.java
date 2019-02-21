package hakery.club.teriyaki.event.impl;

import hakery.club.teriyaki.event.CancellableEvent;
import hakery.club.teriyaki.event.Event;

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
