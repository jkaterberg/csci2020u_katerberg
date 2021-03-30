package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class BulletinClient {
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;

    private String SERVER_ADDRESS;
    private int SERVER_PORT;

    public BulletinClient(String SERVER_ADDRESS, int SERVER_PORT){
        this.SERVER_ADDRESS = SERVER_ADDRESS;
        this.SERVER_PORT = SERVER_PORT;

        try{
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + SERVER_ADDRESS);
        } catch (IOException e) {
            System.err.println("IOException while connecting to server " + SERVER_ADDRESS);
        }
        if (socket == null) {
            System.err.println("Socket is null");
        }
        try{
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String username, String message){
        System.out.println("Sending message...");
        String request = "SEND\n" + username + "\n" + message;
        out.println(request);
    }
}
