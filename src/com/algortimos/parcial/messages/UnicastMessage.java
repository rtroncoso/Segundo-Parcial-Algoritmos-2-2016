package com.algortimos.parcial.messages;

import com.algortimos.parcial.exceptions.ParticipantNotRegisteredException;
import com.algortimos.parcial.models.Participant;
import com.algortimos.parcial.models.Room;

/**
 * UnicastMessage class
 *
 * Extension of abstract message class to send
 * messages from one participant to another
 */
public class UnicastMessage extends Message {

    /** Stores the message receiver  */
    private Participant receiver;

    /**
     * UnicastMessage class constructor
     * @param message
     * @param sender
     */
    public UnicastMessage(String message, Participant sender, Participant receiver) {
        super(message, sender);
        this.receiver = receiver;
    }

    /**
     * UnicastMessage constructor using a nickname as the receiver and room reference
     * @param message
     * @param from
     * @param receiver
     */
    public UnicastMessage(String message, Participant from, String receiver, Room room)
            throws ParticipantNotRegisteredException {
        super(message, from);
        this.receiver = room.searchParticipant(receiver);
    }

    /**
     * Gets the receiver of this message
     * @return Participant
     */
    public Participant getReceiver() {
        return receiver;
    }

    /**
     * Sets the receiver of this message
     * @param receiver
     */
    public void setReceiver(Participant receiver) {
        this.receiver = receiver;
    }

}
