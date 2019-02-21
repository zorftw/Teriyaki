package hakery.club.listener;

import hakery.club.event.Event;

public interface Listener {

    /**
     * Fire the listener
     *
     * @param event the passed event, can be anything
     */
    void call(Event event);

}
