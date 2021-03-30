package server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class BulletinServer extends Thread{
    protected Socket clientSocket = null;
    protected ServerSocket serverSocket = null;
    protected BulletinServerThread[] threads = null;
    protected int numClients = 0;

    private TextArea display;

    public static int SERVER_PORT = 9000;
    public static int MAX_CLIENTS = 25;

    private boolean running = true;

    public ArrayList<Message> messages = null;

    public BulletinServer(TextArea display) {
        this.display = display;
        try {
            serverSocket = new ServerSocket(SERVER_PORT);

            threads = new BulletinServerThread[MAX_CLIENTS];
            messages = new ArrayList<>();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        System.out.println("--------------------------");
        System.out.println("Bulletin Server is running");
        System.out.println("--------------------------");
        System.out.println("Listening on port " + SERVER_PORT);

        try {
            while (running) {
                clientSocket = serverSocket.accept();
                System.out.println("Client #" + (numClients + 1) + " connected");
                threads[numClients] = new BulletinServerThread(clientSocket, messages, display);
                threads[numClients].start();
                numClients++;

                //check for an interrupt
                if(Thread.interrupted()){
                    running = false;
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


}
