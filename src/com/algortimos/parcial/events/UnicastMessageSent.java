package com.algortimos.parcial.events;

import com.algortimos.parcial.events.listeners.UnicastMessageSentListener;
import com.algortimos.parcial.messages.UnicastMessage;

/**
 * UnicastMessageSent class
 */
public class UnicastMessageSent implements Event<UnicastMessageSentListener> {

    /** UnicastMessage being sent */
    private UnicastMessage message;

    /**
     * UnicastMessageSent class constructor
     * @param message
     */
    public UnicastMessageSent(UnicastMessage message) {
        this.message = message;
    }

    @Override
    public void notify(UnicastMessageSentListener listener) {
        listener.onUnicastMessageSent(this.message);
    }

}
