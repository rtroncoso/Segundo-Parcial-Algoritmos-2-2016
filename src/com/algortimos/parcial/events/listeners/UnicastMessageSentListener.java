package com.algortimos.parcial.events.listeners;

import com.algortimos.parcial.messages.UnicastMessage;

/**
 * UnicastMessageSentListener
 */
public interface UnicastMessageSentListener {

    /**
     * Fired when a participant sends a unicast message
     * @param message
     */
    void onUnicastMessageSent(UnicastMessage message);

}
