/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 * @author Igor Gayvan
 */
public class ChatClient {

    

    private String serverAddress;
    private int serverPort;
    public static String message;
    public ChatClient(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    /**
     * Sending message to server.
     *
     * @param recipient to send a message.
     * @param message message sent.
     */
    public void sendMessage(String recipient, String message) throws IOException {
        try (Socket socketClient = new Socket()) {
            // Configure Socket
            socketClient.setSoTimeout(1000);
            
            // Connecting to server
            System.out.println("Connecting...");
            socketClient.connect(new InetSocketAddress(serverAddress, serverPort));
            System.out.println("Connect establish");

            // Get Socket IO.
            DataOutputStream dos = new DataOutputStream(socketClient.getOutputStream());
            DataInputStream dis = new DataInputStream(socketClient.getInputStream());

            // Send command
            dos.writeUTF("MSG");
            dos.flush();
            
            // Send message
            dos.writeUTF(recipient); ///*String.valueOf(socketClient.getLocalSocketAddress()*/));
            dos.writeUTF(message);
            dos.flush();

            // Receive response
            String response = dis.readUTF();

            if ("OK".equals(response)) {
                System.out.println("Message was sent.");
            } else {
                System.out.println("ERROR: While sending message.");
            }
        }
    }
}
