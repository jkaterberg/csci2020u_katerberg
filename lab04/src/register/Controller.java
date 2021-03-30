package register;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Controller {
    public DatePicker birthField;
    public TextField usernameField;
    public PasswordField passwordField;
    public TextField fullNameField;
    public TextField emailField;
    public TextField phoneField;
    public Button submitButton;
    public Label emailErrorLabel;
    private final EmailValidator emailValidator = EmailValidator.getInstance();

    private DateTimeFormatter dateTimeFormatter;

    @FXML
    public void initialize(){
        final String datePattern = "yyyy-MM-dd";
        dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
        birthField.setConverter(new StringConverter<LocalDate>() {

            /**
             * Method to convert a date into a string
             * @param date LocalDate object
             * @return string representation of date
             */
            public String toString(LocalDate date) {
                String finalDate = null;
                if(null != date)
                    finalDate = dateTimeFormatter.format(date);
                return finalDate;
            }

            @Override
            public LocalDate fromString(String s) {
                LocalDate date = null;
                if (s != null)
                    date = LocalDate.parse(s, dateTimeFormatter);
                return date;
            }
        });

    }

    public void submitButtonClicked(ActionEvent actionEvent) {
        //Clear any previous errors
        emailErrorLabel.setText("");
        //check for valid inputs
        if(emailValidator.isValid(emailField.getText())) {
            System.out.println("Username: " + usernameField.getText());
            System.out.println("Password: " + passwordField.getText());
            System.out.println("Full Name: " + fullNameField.getText());
            System.out.println("E-mail: " + emailField.getText());
            System.out.println("Phone: " + phoneField.getText());
            System.out.println("Birthday: " + birthField.getEditor().getText());
        }else{
            emailField.selectAll();
            emailErrorLabel.setText("Invalid Email Address");
        }
    }
}