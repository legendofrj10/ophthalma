package sample.MDC;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class medicineBillController {

    @FXML
    private Button backBTN;

    @FXML
    private TextField patientID;

    @FXML
    private Button searchPatientBTN;

    @FXML
    private Label patientNameTxt;

    @FXML
    private Label ageTxt;

    @FXML
    private Label medicineSellerNameTxt;

    @FXML
    private TextField medicineName;

    @FXML
    private Button searchMedicineBTN;

    @FXML
    private Button generateBillBTN;

    @FXML
    void callBack() throws IOException {
        Stage stage = (Stage) backBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callGenerateBill(ActionEvent event) {

    }

    @FXML
    void callSearchMedicine(ActionEvent event) {

    }

    @FXML
    void callSearchPatient(ActionEvent event) {

    }

}
