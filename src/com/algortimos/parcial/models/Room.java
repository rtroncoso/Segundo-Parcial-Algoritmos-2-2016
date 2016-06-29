package com.algortimos.parcial.models;

import com.algortimos.parcial.events.BroadcastMessageSent;
import com.algortimos.parcial.events.Dispatcher;
import com.algortimos.parcial.events.UnicastMessageSent;
import com.algortimos.parcial.exceptions.InvalidNicknameException;
import com.algortimos.parcial.exceptions.NicknameAlreadyInUseException;
import com.algortimos.parcial.exceptions.NoDevicesRegisteredException;
import com.algortimos.parcial.exceptions.ParticipantNotRegisteredException;
import com.algortimos.parcial.messages.BroadcastMessage;
import com.algortimos.parcial.messages.Message;
import com.algortimos.parcial.messages.UnicastMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Room class - Integrated ChatRoom models
 */
public class Room {

    /** Contains this room's participant list */
    private List<Participant> participants = new ArrayList<Participant>( 255 );

    /** Messages sent in this room (maybe should store only global ones?) */
    private List<Message> messages = new ArrayList<Message>();

    /** Event dispatcher used in this room */
    private Dispatcher dispatcher = new Dispatcher();

    /**
     * Registers a new participant into the system using a Participant object
     * @param participant
     */
    public void registerParticipant(Participant participant)
            throws NicknameAlreadyInUseException {
        for(Participant p : this.getParticipants()) {
            if(p.getNickname().equals(participant.getNickname())) {
                throw new NicknameAlreadyInUseException();
            }
        }

        this.participants.add(participant);
    }

    /**
     * Registers a new participant into the system using parameters (creates a new object)
     * @param name
     * @param nickname
     * @param gender
     */
    public void registerParticipant(String name, String nickname, int gender)
            throws NicknameAlreadyInUseException, InvalidNicknameException {
        this.registerParticipant(new Participant(this, name, nickname, gender));
    }

    /**
     * Method used to send broadcast messages to this room
     * @param message
     */
    public void sendMessage(BroadcastMessage message) {
        this.dispatcher.notify(new BroadcastMessageSent(message));
        this.messages.add(message);
    }

    /**
     * Method used to send unicast messages to this room
     * @param message
     */
    public void sendMessage(UnicastMessage message) throws NoDevicesRegisteredException {
        this.dispatcher.notify(
            new UnicastMessageSent(message),
            message.getReceiver().getDevice()
        );
        this.messages.add(message);
    }

    /**
     * Searches the list of participants using a given nickname
     * @param nickname
     * @return Participant
     */
    public Participant searchParticipant(String nickname)
            throws ParticipantNotRegisteredException {
        for(Participant p : this.getParticipants()) {
            if (p.getNickname().equals(nickname)) {
                return p;
            }
        }

        throw new ParticipantNotRegisteredException();
    }

    /**
     * Logouts and removes a participant from the participants list
     * @param participant
     */
    public void logout(Participant participant) {
        this.participants.remove(participant);
    }

    /**
     * Obtains the list of participants in the room
     * @return List
     */
    public List<Participant> getParticipants() {
        return participants;
    }

    /**
     * Obtains the list of messages sent in this chatroom
     * @return List
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * Obtains the event dispatcher of this chat room
     * @return Dispatcher
     */
    public Dispatcher getDispatcher() {
        return dispatcher;
    }

}
