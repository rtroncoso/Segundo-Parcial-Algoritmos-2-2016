package com.algortimos.parcial.devices;

import com.algortimos.parcial.events.listeners.BroadcastMessageSentListener;
import com.algortimos.parcial.events.listeners.UnicastMessageSentListener;
import com.algortimos.parcial.messages.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Device class - Objects who receive and display messages
 */
public abstract class Device implements BroadcastMessageSentListener,
        UnicastMessageSentListener {

    /** Stores the list of messages sent to this device */
    protected List<Message> messages = new ArrayList<Message>();

    /**
     * Displays a message into the device using a message object
     * @param message
     */
    public abstract void display(Message message);

    /**
     * Displays all the messages sent to this device in order
     */
    public abstract void displayAll();

    /**
     * Obtains all the messages sent into this device
     * @return
     */
    public List<Message> getMessages() {
        return messages;
    }

}
