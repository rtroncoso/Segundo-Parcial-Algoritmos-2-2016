package com.algortimos.parcial.events.listeners;

import com.algortimos.parcial.messages.BroadcastMessage;

/**
 * BroadcastMessageSentListener
 */
public interface BroadcastMessageSentListener {

    /**
     * Fired when a participant sends a broadcast message
     * @param message
     */
    void onBrodcastMessageSent(BroadcastMessage message);

}
