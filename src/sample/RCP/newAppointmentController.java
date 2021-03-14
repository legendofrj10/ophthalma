package sample.RCP;

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


public class newAppointmentController {

    @FXML
    private TextField patientIDTxt;

    @FXML
    private ComboBox<?> chooseDepartmentCB;

    @FXML
    private Button newPatientBTN;

    @FXML
    private Button submitBTN;

    @FXML
    private DatePicker datePicker;

    @FXML
    void callNewPatient( ) throws IOException {
        Stage stage = (Stage) newPatientBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("patientsRegister.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callSubmit( ) {

    }

}
