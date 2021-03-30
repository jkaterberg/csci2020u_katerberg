package server;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public TextArea bulletinDisplay;
    BulletinServer server;
    private ArrayList<Message> messages = null;

    public void exit(ActionEvent actionEvent) throws InterruptedException {
        server.interrupt();
        Platform.exit();
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        server = new BulletinServer(bulletinDisplay);
        server.start();
    }
}
