package com.algortimos.parcial.devices;

import com.algortimos.parcial.Main;
import com.algortimos.parcial.messages.BroadcastMessage;
import com.algortimos.parcial.messages.Message;
import com.algortimos.parcial.messages.UnicastMessage;
import sun.java2d.pipe.SpanShapeRenderer;

import java.text.SimpleDateFormat;

/**
 * ConsoleOutputDevice class
 * Displays received messages of this device through console output
 */
public class ConsoleOutputDevice extends Device {

    /**
     * Displays a broadcast message into console
     * @param message
     */
    @Override
    public void display(Message message) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String messageString = "[" + format.format(message.getDate().getTime()) + "]" +
                " (" + message.getSender().getNickname() + "): " +
                message.getMessage();

        System.out.println(messageString);
    }

    /**
     * Displays a UnicastMessage into console
     * @param message
     */
    public void display(UnicastMessage message) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String messageString = "[" + format.format(message.getDate().getTime()) + "]" +
                " (pm from " + message.getSender().getNickname() + "): " +
                message.getMessage();

        System.out.println(messageString);
    }

    /**
     * Displays all of the messages received by this device
     */
    @Override
    public void displayAll() {
        System.out.println("Displaying all messages received by this device:");

        for(Message m : this.getMessages()) {
            this.display(m);
        }
    }

    /**
     * Method fired when a broadcast message is sent to a room
     * @param message
     */
    @Override
    public void onBrodcastMessageSent(BroadcastMessage message) {
        this.messages.add(message);
        this.display(message);
    }

    /**
     * Method fired when a broadcast message is sent to this device privately
     * @param message
     */
    @Override
    public void onUnicastMessageSent(UnicastMessage message) {
        this.messages.add(message);
        this.display(message);
    }

}
