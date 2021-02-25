package gradeCalculator;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML private TableView<StudentRecord> table;
    @FXML private TableColumn<StudentRecord, String> sidColumn;
    @FXML private TableColumn<StudentRecord, Float> assignmentColumn;
    @FXML private TableColumn<StudentRecord, Float> midtermColumn;
    @FXML private TableColumn<StudentRecord, Float> examColumn;
    @FXML private TableColumn<StudentRecord, Float> finalColumn;
    @FXML private TableColumn<StudentRecord, Character> letterColumn;


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

    private final ObservableList<StudentRecord> studentList = DataSource.getAllMarks();
}
