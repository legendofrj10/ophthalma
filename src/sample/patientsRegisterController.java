package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class patientsRegisterController {

    @FXML
    private TextField patientNameField;

    @FXML
    private ComboBox<?> patientGenderCB;

    @FXML
    private DatePicker patientDOB;

    @FXML
    private ComboBox<?> patientEyeCB;

    @FXML
    private Button registerBTN;

    @FXML
    private Button backBTN;

    @FXML
    void callBack() throws IOException {
        Stage stage = (Stage) backBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("patientsAndScans.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callRegister() {
        String name = patientNameField.getText();
        String gender = (String) patientGenderCB.getValue();
        String DOB = patientDOB.getValue().format(DateTimeFormatter.BASIC_ISO_DATE);
        String eye = (String) patientEyeCB.getValue();
        System.out.println(name+"  "+gender+"  "+DOB+"  "+eye);
    }

}
