package gradeCalculator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

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

    private String currentFilename = null;

    private ObservableList<StudentRecord> studentList = DataSource.getAllMarks();

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
            errorLabel.setText("Grade Error: Inputted grades are not numbers");
        }
    }

    @FXML
    private void save() {
        try {
            if(null != currentFilename) {
                //Create a new file object
                File CSVFile = new File(currentFilename);

                //Generate CSV content
                StringBuilder fileContent = new StringBuilder("SID,Assignments,Midterm,Final Exam\n");
                for (StudentRecord item : studentList) {
                    fileContent.append(item.getStudentID()).append(", ").append(item.getAssignments()).append(", ").append(item.getMidterm()).append(", ").append(item.getFinalExam()).append("\n");
                }

                //Write CSV to file
                FileWriter writer = new FileWriter(CSVFile);
                writer.write(fileContent.toString());
                writer.close();
            }else{
                saveFile();
            }
        }catch(IOException e){
            //Try and handle the exception
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Could not Save File");
            alert.setHeaderText("File could not be save at " + currentFilename);
            alert.showAndWait();
        }
    }

    @FXML
    private void load() {
        try {
            newFile(); //Clear the current table

            //Read the contents of the current file
            File data = new File(currentFilename);
            Scanner sc = new Scanner(data);
            String[] entry;
            sc.nextLine();//ignore the headers
            while (sc.hasNextLine()) {
                //Parse the CSV, add a new StudentRecord Object
                entry = sc.nextLine().split(",");
                studentList.add(new StudentRecord(entry[0], Float.parseFloat(entry[1]), Float.parseFloat(entry[2]), Float.parseFloat(entry[3])));
            }
            sc.close();
            table.setItems(studentList);
        }catch(FileNotFoundException | NullPointerException e){
            //Try to handle errors
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File Not Found");
            alert.setHeaderText("Could not locate" + currentFilename);
            alert.setContentText("Ensure file exists and try again");
            alert.showAndWait();
        }
    }

    @FXML
    private void newFile(){
        //Clear all the data
       studentList = FXCollections.observableArrayList();
       table.setItems(studentList);
    }


    public void openFile() {
        try {
            FileChooser chooser = new FileChooser();
            File newFile = chooser.showOpenDialog(null);
            currentFilename = newFile.getName();
            load();
        }catch(NullPointerException e){
            //Try to handle errors
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File Not Found");
            alert.setHeaderText("Could not locate" + currentFilename);
            alert.setContentText("Ensure file exists and try again");
            alert.showAndWait();
        }
    }

    public void saveFile() throws IOException {
        FileChooser chooser = new FileChooser();
        currentFilename = chooser.showSaveDialog(null).getName();
        save();
    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }
}
