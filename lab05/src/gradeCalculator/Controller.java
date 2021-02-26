package gradeCalculator;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public TextField sidInput;
    public TextField assignmentsInput;
    public TextField midtermInput;
    public TextField examInput;
    public Button addButton;
    public Label errorLabel;
    @FXML private TableView<StudentRecord> table;
    @FXML private TableColumn<StudentRecord, String> sidColumn;
    @FXML private TableColumn<StudentRecord, Float> assignmentColumn;
    @FXML private TableColumn<StudentRecord, Float> midtermColumn;
    @FXML private TableColumn<StudentRecord, Float> examColumn;
    @FXML private TableColumn<StudentRecord, Float> finalColumn;
    @FXML private TableColumn<StudentRecord, Character> letterColumn;


    private final ObservableList<StudentRecord> studentList = DataSource.getAllMarks();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //make sure the property value factory should be exactly same as the e.g getStudentId from your model class
        sidColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        assignmentColumn.setCellValueFactory(new PropertyValueFactory<>("assignments"));
        midtermColumn.setCellValueFactory(new PropertyValueFactory<>("midterm"));
        examColumn.setCellValueFactory(new PropertyValueFactory<>("finalExam"));
        finalColumn.setCellValueFactory(new PropertyValueFactory<>("finalMark"));
        letterColumn.setCellValueFactory(new PropertyValueFactory<>("letter"));
        //add your data to the table here.
        table.setItems(studentList);
    }



    public void addButtonHandler(javafx.event.ActionEvent actionEvent) {
        try {
            //Get the relevant data
            String newSID = sidInput.getText();
            float newAssignments = Float.parseFloat(assignmentsInput.getText());
            float newMidterm = Float.parseFloat(midtermInput.getText());
            float newExam = Float.parseFloat(examInput.getText());

            //check that the fields were actually populated
            //Clear the text fields
            sidInput.clear();
            assignmentsInput.clear();
            midtermInput.clear();
            examInput.clear();

            //Create a new StudentRecord object
            StudentRecord newStudent = new StudentRecord(newSID, newMidterm, newAssignments, newExam);

            //Add new student to list of students and display the updated list on the table
            studentList.add(newStudent);
            table.setItems(studentList);
        }catch(NumberFormatException e){
            errorLabel.setText("Grade Error: Inputed grades are not numbers");
        }
    }
}
