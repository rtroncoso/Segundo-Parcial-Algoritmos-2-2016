package com.algortimos.parcial.messages;

import com.algortimos.parcial.models.Participant;

/**
 * BroadcastMessage class
 *
 * Extension of abstract message class to send
 * global messages to a room
 */
public class BroadcastMessage extends Message {

    /**
     * BroadcastMessage class constructor
     *
     * @param message
     * @param sender
     */
    public BroadcastMessage(String message, Participant sender) {
        super(message, sender);
    }

}
