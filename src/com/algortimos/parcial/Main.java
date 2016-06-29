package com.algortimos.parcial;

import com.algortimos.parcial.exceptions.InvalidNicknameException;
import com.algortimos.parcial.exceptions.NicknameAlreadyInUseException;
import com.algortimos.parcial.exceptions.NoDevicesRegisteredException;
import com.algortimos.parcial.exceptions.ParticipantNotRegisteredException;
import com.algortimos.parcial.messages.BroadcastMessage;
import com.algortimos.parcial.messages.UnicastMessage;
import com.algortimos.parcial.models.Participant;
import com.algortimos.parcial.models.Room;
import java.util.Scanner;

/**
 * Main class
 */
public class Main {

    /** Sets this application to debug mode or not */
    public static final boolean APPLICATION_DEBUG = false;

    /** Static reference to this application's room */
    private static Room room = new Room();

    /** Application's command line scanner */
    private static Scanner scanner = new Scanner(System.in);

    /** Static reference to this application's current message sender */
    public static Participant participant;

    /**
     * Application main entry point
     * @param args
     */
    public static void main(String[] args) {
        Main.help();
        registerParticipant(new String[]{"Rodrigo Troncoso", "rtroncoso", "1", "t"});
        switchUser(new String[]{"rtroncoso"});

        while(true) {
            String message = scanner.nextLine();
            parseMessage(message.toLowerCase());
        }
    }

    /**
     * Parses a given command line message
     */
    public static void parseMessage(String message) {
        if( ! message.startsWith("/")) {
            error("Los comandos deben iniciar con un caracter de \"/\"");
            return;
        }

        // Save the index of the first space, if no space available use message length
        int firstSpace = message.contains(" ") ? message.indexOf(" ") : message.length();
        String command = message.substring(1, firstSpace);
        String[] arguments = (message.length() > command.length() + 2) ?
                message.substring(command.length() + 2).split(" ") :
                null;

        // debug purposes only
        if(APPLICATION_DEBUG) {
            System.out.println(arguments.length);
            for(String arg : arguments) {
                System.out.println(arg);
            }
        }

        switch(command) {
            case "r":
            case "registrar": {
                registerParticipant(arguments);
                break;
            }
            case "m":
            case "mensaje": {
                sendMessage(command, message, arguments);
                break;
            }
            case "p":
            case "privado": {
                sendPrivateMessage(command, message, arguments);
                break;
            }
            case "su":
            case "switchuser": {
                switchUser(arguments);
                break;
            }
            case "logout": {
                logout();
                break;
            }
            case "h":
            case "help": {
                help();
                break;
            }
            default: {
                error("El comando solicitado no existe.");
                break;
            }
        }
    }

    /**
     * Registers a participant into the chat room
     * @param arguments
     */
    public static void registerParticipant(String[] arguments) {
        if (arguments == null || arguments.length < 3) {
            error("Insuficientes argumentos para el comando \"/registrar\"");
            return;
        }

        Participant newParticipant;
        try {
            newParticipant = new Participant(room,
                    arguments[0],
                    arguments[1],
                    Integer.parseInt(arguments[2]),
                    ((arguments.length == 4 && arguments[3].equals("t")) ?
                            true : false)
            );
            room.registerParticipant(newParticipant);
        } catch (InvalidNicknameException e) {
            error("El nombre de usuario no debe contener espacios ni caracteres especiales");
            return;
        } catch (NicknameAlreadyInUseException e) {
            error("El nombre de usuario elegido ya está en uso");
            return;
        } catch(NumberFormatException e) {
            error("El parámetro \"genero\" debe ser un entero entre 1 y 2");
            return;
        }

        System.out.println("Se ha agregado al usuario " + newParticipant.getName() +
                " (" + newParticipant.getNickname() + ") a la sala.");
    }

    /**
     * Sends a global broadcast message to a room
     * @param command
     * @param message
     * @param arguments
     */
    public static void sendMessage(String command, String message, String[] arguments) {
        if(arguments == null || arguments.length < 1) {
            error("Insuficientes argumentos para el comando \"/mensaje\"");
            return;
        }

        if(participant == null) {
            error("No hay participantes registrados en el sistema");
            return;
        }

        room.sendMessage(new BroadcastMessage(message.substring(command.length() + 2), participant));
    }

    /**
     * Sends a private unicast message to a participant in a room
     * @param command
     * @param message
     * @param arguments
     */
    public static void sendPrivateMessage(String command, String message, String[] arguments) {
        if(arguments == null || arguments.length < 2) {
            error("Insuficientes argumentos para el comando \"/privado\"");
            return;
        }

        if(participant == null) {
            error("No hay participantes registrados en el sistema");
            return;
        }

        Participant receiver;
        try {
            receiver = room.searchParticipant(arguments[0]);
        } catch (ParticipantNotRegisteredException e) {
            error("El usuario de destino es inexistente");
            return;
        }

        try {
            room.sendMessage(
                new UnicastMessage(message.substring(command.length() + arguments[0].length() + 3),
                participant,
                receiver)
            );
        } catch (NoDevicesRegisteredException e) {
            error("El usuario de destino no tiene dispositivos registrados.");
        }
    }

    /**
     * Switches the current user that sends messages in the room
     * @param arguments
     */
    public static void switchUser(String[] arguments) {
        if(arguments == null || arguments.length < 1) {
            error("Insuficientes argumentos para el comando \"/switchUser\"");
            return;
        }

        Participant newParticipant;
        try {
            newParticipant = room.searchParticipant(arguments[0]);
        } catch (ParticipantNotRegisteredException e) {
            error("El participante solicitado no existe");
            return;
        }

        participant = newParticipant;
        System.out.println("Se ha cambiado el usuario actual a " +
                participant.getName() + " (" + participant.getNickname() + ")");
    }

    /**
     * Logouts the current participant from the system
     */
    public static void logout() {
        if(participant == null) {
            error("No hay participantes registrados en el sistema");
            return;
        }

        participant.logout();
        participant = null;
    }

    /**
     * Prints out an error message to std console
     * @param message
     */
    public static void error(String message) {
        System.out.println("[ ERROR ] : " + message);
    }

    /**
     * Displays message help with all available commands
     */
    public static void help() {
        String message = "Segundo Parcial Algoritmos - Troncoso Rodrigo\n" +
                "Sistema de chat entre participantes de una sala\n\n" +
                "| Comandos disponibles : \n" +
                "/(r)egistrar [nombre] [nickname] [genero: 1=M o 2=F] [conConsola: OPCIONAL T=si o F=no]] - " +
                    "registra un nuevo usuario en la sala\n" +
                "/(m)ensaje [mensaje] - envía un mensaje global a la sala\n" +
                "/(p)rivado [nickname] [mensaje] - envía un mensaje privado a un usuario\n" +
                "/su [nickname] - define el usuario con el cual se estan enviando mensajes (requerido al crear un usuario nuevo)\n" +
                "/logout - desconecta el usuario actual de la sala y muestra todos sus mensajes recibidos\n" +
                "/help - muestra este mensaje de ayuda";

        System.out.println(message);
    }

}
