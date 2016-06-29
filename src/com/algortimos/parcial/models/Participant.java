package com.algortimos.parcial.models;

import com.algortimos.parcial.devices.ConsoleOutputDevice;
import com.algortimos.parcial.devices.Device;
import com.algortimos.parcial.events.BroadcastMessageSent;
import com.algortimos.parcial.events.UnicastMessageSent;
import com.algortimos.parcial.events.listeners.BroadcastMessageSentListener;
import com.algortimos.parcial.exceptions.InvalidNicknameException;
import com.algortimos.parcial.exceptions.NoDevicesRegisteredException;

/**
 * Participant class - Model for a participant of a ChatRoom
 */
public class Participant {

    /** Constant for male participants */
    public static final int GENDER_MALE = 1;

    /** Constant for female participants */
    public static final int GENDER_FEMALE = 2;

    /** Reference to this participant's associated ChatRoom */
    private Room room;

    /** Stores this participant's name */
    private String name;

    /** Stores this participant's nickname */
    private String nickname;

    /** Stores this participant's gender */
    private int gender;

    /** Device associated to this participant */
    private Device device;

    /**
     * Participant class constructor
     * @param room
     */
    public Participant(Room room, String name, String nickname, int gender)
            throws InvalidNicknameException {
        this.setNickname(nickname); // Nicknames should have validation (no spaces)
        this.room = room;
        this.name = name;
        this.gender = gender;
    }

    /**
     * Participant class constructor - automatically creates a console device
     * for this participant
     * @param room
     */
    public Participant(Room room, String name, String nickname, int gender, boolean withConsole)
            throws InvalidNicknameException {
        this(room, name, nickname, gender);
        if(withConsole) {
            this.setDevice(new ConsoleOutputDevice());
        }
    }

    /**
     * Logouts this participant from the room
     */
    public void logout() {
        Device device = null;
        try {
            device = this.getDevice();
            device.displayAll();
            this.room.getDispatcher().removeListener(BroadcastMessageSent.class, device);
            this.room.getDispatcher().removeListener(UnicastMessageSent.class, device);
        } catch (NoDevicesRegisteredException e) {}

        this.room.logout(this);
    }

    /**
     * Participant room getter
     * @return Room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Participant room setter
     * @param room
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Participant name getter
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Participant name setter
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Participant nickname getter
     * @return String
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Participant nickname setter
     * @param nickname
     */
    public void setNickname(String nickname) throws InvalidNicknameException {
        if(nickname.contains(" ")) {
            throw new InvalidNicknameException();
        }

        this.nickname = nickname;
    }

    /**
     * Participant gender getter
     * @return int
     */
    public int getGender() {
        return gender;
    }

    /**
     * Participant gender setter
     * @param gender
     */
    public void setGender(int gender) {
        this.gender = gender;
    }

    /**
     * Participant device getter
     * @return Device
     */
    public Device getDevice() throws NoDevicesRegisteredException {
        if(this.device == null) {
            throw new NoDevicesRegisteredException();
        }

        return device;
    }

    /**
     * Adds a device to this participant
     * @param device
     */
    public void setDevice(Device device) {
        this.room.getDispatcher().addListener(BroadcastMessageSent.class, device);
        this.room.getDispatcher().addListener(UnicastMessageSent.class, device);
        this.device = device;
    }

}
