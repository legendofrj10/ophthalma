package sample.DOC;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class prescriptionController {

    @FXML
    private Button backBTN;

    @FXML
    private Label hospitalNameTxt;

    @FXML
    private Label doctorNameTxt;

    @FXML
    private Label departmentTxt;

    @FXML
    private Label phnoTxt;

    @FXML
    private Label patientNameTxt;

    @FXML
    private Label patientAgeTxt;

    @FXML
    private Label patientGenderTxt;

    @FXML
    private Label dateOfPrescriptionTxt;

    @FXML
    private ListView<?> medicineListView;

    @FXML
    private Button printBTN;

    @FXML
    private TextField searchMedicineTB;

    @FXML
    private Button searchMedicineBTN;

    @FXML
    void callBack( ) throws IOException {
        Stage stage = (Stage) backBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("patient.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callPrint( ) {

    }

    @FXML
    void callSearchMedicine( ) {

    }

}
