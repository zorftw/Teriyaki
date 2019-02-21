package hakery.club.event;

/**
 * Interface class for all stoppable events
 */
public interface CancellableEvent {

    /**
     * Get the cancelled state of the event
     *
     * @return true if the event is cancelled
     */
    boolean cancelled();

    /**
     * Set the current cancelled state of the event
     *
     * @param state The new state to be set
     */
    void set_cancelled(boolean state);

}
