package test.hakery.club.main.events;

import hakery.club.teriyaki.event.Event;

/**
 * Test class to show any custom event
 */
public class MyCustomEvent implements Event {
    long ms;

    public MyCustomEvent(long ms) {
        this.ms = ms;
    }

    public long getMs() {
        return ms;
    }
}
