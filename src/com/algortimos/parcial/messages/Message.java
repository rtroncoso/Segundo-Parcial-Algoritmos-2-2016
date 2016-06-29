package com.algortimos.parcial.messages;

import com.algortimos.parcial.models.Participant;

import java.util.Calendar;

/**
 * Message class - Base models that corresponds to messages sent
 * through the application
 */
public abstract class Message {

    /** Date time in which the message was sent */
    private Calendar date;

    /** Message being sent */
    private String message;

    /** Stores the participant who sent this message */
    private Participant sender;

    /**
     * Message class constructor
     * @param message
     * @param sender
     */
    public Message(String message, Participant sender) {
        this.date = Calendar.getInstance();
        this.message = message;
        this.sender = sender;
    }

    /**
     * Obtains the message sent
     * @return String
     */
    public String getMessage() {
        return message;
    }

    /**
     * Obtains the date in which this message was sent
     * @return Calendar
     */
    public Calendar getDate() {
        return date;
    }

    /**
     * Obtains the participant who sent this message
     * @return Participant
     */
    public Participant getSender() {
        return sender;
    }

}
