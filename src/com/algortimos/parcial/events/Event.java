package com.algortimos.parcial.events;

/**
 * Event interface
 */
public interface Event<L> {

    /**
     * Event notifier implementation
     * @param listener
     */
    void notify(final L listener);

}
