package hakery.club.teriyaki.listener;

import hakery.club.teriyaki.event.Event;

public interface Listener {

    /**
     * Fire the listener
     *
     * @param event the passed event, can be anything
     */
    void call(Event event);

}
