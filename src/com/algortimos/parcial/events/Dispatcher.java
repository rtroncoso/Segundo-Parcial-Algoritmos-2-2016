package com.algortimos.parcial.events;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Event dispatcher class
 *
 */
public final class Dispatcher {

    /** Mapping of active listeners */
    private final HashMap<Class, ArrayList> listeners = new HashMap<Class, ArrayList>( 10 );

    /**
     * Add a listener to this message dispatcher's list
     * @param evtClass
     * @param listener
     * @param <L>
     */
    public <L> void addListener(Class<? extends Event<L>> evtClass, L listener) {
        final ArrayList<L> listeners = listenersOf(evtClass);
        if ( ! listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * Removes a listener from the listeners list
     * @param evtClass
     * @param listener
     * @param <L>
     */
    public <L> void removeListener(Class<? extends Event<L>> evtClass, L listener) {
        final ArrayList<L> listeners = listenersOf(evtClass);
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    /**
     * Gets listeners for a given event class
     * @param evtClass
     * @param <L>
     * @return
     */
    private <L> ArrayList<L> listenersOf(Class<? extends Event<L>> evtClass) {
        @SuppressWarnings("unchecked")
        final ArrayList<L> existing = this.listeners.get(evtClass);
        if (existing != null) {
            return existing;
        }

        final ArrayList<L> emptyList = new ArrayList<L>(5);
        this.listeners.put(evtClass, emptyList);
        return emptyList;
    }

    /**
     * Notifies an event to it's listeners
     * @param evt
     * @param <L>
     */
    public <L> void notify(final Event<L> evt) {
        @SuppressWarnings("unchecked")
        Class<Event<L>> evtClass = (Class<Event<L>>) evt.getClass();

        for (L listener : listenersOf(evtClass)) {
            evt.notify(listener);
        }
    }

    /**
     * Notifies an event to only one listener
     * @param evt
     * @param listener
     * @param <L>
     */
    public <L> void notify(final Event<L> evt, L listener) {
        evt.notify(listener);
    }

}