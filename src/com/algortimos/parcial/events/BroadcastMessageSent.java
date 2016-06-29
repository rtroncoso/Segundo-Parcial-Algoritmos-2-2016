package com.algortimos.parcial.events;

import com.algortimos.parcial.events.listeners.BroadcastMessageSentListener;
import com.algortimos.parcial.messages.BroadcastMessage;

/**
 * BroadcastMessageSent class
 */
public class BroadcastMessageSent implements Event<BroadcastMessageSentListener> {

    /** BroadcastMessage being sent */
    private BroadcastMessage message;

    /**
     * BroadcastMessageSent class constructor
     * @param message
     */
    public BroadcastMessageSent(BroadcastMessage message) {
        this.message = message;
    }

    @Override
    public void notify(BroadcastMessageSentListener listener) {
        listener.onBrodcastMessageSent(this.message);
    }

}
