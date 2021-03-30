package client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller {
    public TextField usernameInput;
    public TextField messageInput;
    public Button exitButton;
    public Button sendButton;

    private String SERVER_ADDRESS = "localhost";
    private int SERVER_PORT = 9000;

    public void sendMessage(ActionEvent actionEvent) {
        //Gather the message info
        String username = usernameInput.getText();
        String message = messageInput.getText();

        if(!username.equals("") && !message.equals("")) {
            BulletinClient client = new BulletinClient(SERVER_ADDRESS, SERVER_PORT);
            client.send(username, message);
        }
    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }
}
