package register;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Controller {
    public DatePicker birthField;
    public TextField usernameField;
    public PasswordField passwordField;
    public TextField fullNameField;
    public TextField emailField;
    public TextField phoneField;
    public Button submitButton;

    public void submitButtonClicked(ActionEvent actionEvent) {
        System.out.println("Username: " + usernameField.getText());
        System.out.println("Password: " + passwordField.getText());
        System.out.println("Full Name: " + fullNameField.getText());
        System.out.println("E-mail: " + emailField.getText());
        System.out.println("Phone: " + phoneField.getText());
        System.out.println("Birthday: " + birthField.getValue());
    }
}
