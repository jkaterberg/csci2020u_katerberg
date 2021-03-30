package server;

import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BulletinServerThread extends Thread{
    protected Socket socket = null;
    protected PrintWriter out = null;
    protected BufferedReader in = null;

    private TextArea display;

    private String username = null;
    private ArrayList<Message> messages = null;

    public BulletinServerThread(Socket clientSocket, ArrayList<Message> messages, TextArea display) {
        super();

        this.display = display;
        this.messages = messages;
        this.socket = clientSocket;

        try{
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        //initialize interaction
        out.println("Connected to BulletinServer");
        out.println("200 ready for transfer");

        processCommand();

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void processCommand(){
        String message = null;
        try {
            message = in.readLine();
        } catch (IOException e) {
            System.err.println("Error reading from client");
            return;
        }
        if(message==null){
            return;
        }
        StringTokenizer st = new StringTokenizer(message);
        String command = st.nextToken();
        String args = null;
        if(st.hasMoreTokens()){
            args = message.substring(command.length()+1, message.length());
        }
        processCommand(command, args);
    }

    protected void processCommand(String command, String args){
        if(command.equalsIgnoreCase("SEND")){
            try {
                String message = in.readLine();
                String username = in.readLine();

                synchronized (messages) {
                    messages.add(new Message(message, username));
                }
                System.out.println(messages);
                updateDisplay();
            }catch(IOException e){
                System.err.println("Could not read message from client");
            }
        }
    }

    private void updateDisplay(){
        String text = "";
        for(Message message : messages){
            text += message.toString() + "\n\n";
        }
        display.setText(text);
    }
}
