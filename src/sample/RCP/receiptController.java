package sample.RCP;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class receiptController {

    public Button appointmentBTN;
    public Button receiptSystemBTN;
    public Button submitBTN;
    public TextField patientIDTxt;
    public GridPane billPane;
    public Label appointmentFeeTxt;
    public Label medicineBillTxt;
    public Label prescriptionFeeTxt;
    public Label totalBillTxt;
    public Button submitBTN1;
    @FXML
    private Button helpAndFaqBTN,patientsAndScansBTN,dashboardBTN,logOutBTN,settingsBTN,notificationBTN,chatBubbleBTN;

    @FXML
    private Button accountsManagementBTN;

    @FXML
    void callChatBubble() throws IOException {
        Stage stage = (Stage) chatBubbleBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("chatBubble.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callDashboard() throws IOException {
        Stage stage = (Stage) dashboardBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callHelpAndFaq() throws IOException {
        Stage stage = (Stage) helpAndFaqBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("helpAndFaq.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callLogOut() throws IOException {
        Stage stage = (Stage) logOutBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../logout.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callNotifications() throws IOException {
        Stage stage = (Stage) notificationBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("notifications.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callPatientsAndScans() throws IOException {
        Stage stage = (Stage) patientsAndScansBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("patientsAndScans.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);

    }

    @FXML
    void callAccountsManagement() throws IOException {
        Stage stage = (Stage) accountsManagementBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("accountsManagement.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callSettings() throws IOException {
        Stage stage = (Stage) settingsBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("settings.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    public void callAppointment() throws IOException {
        Stage stage = (Stage) appointmentBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("appointment.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    public void callReceiptSystem() throws IOException {
        Stage stage = (Stage) receiptSystemBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("receipt.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    public void callSubmit() {
    }
}