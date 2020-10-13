package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class helpAndFaqController {
    @FXML
    private Button helpAndFaqBTN,patientsAndScansBTN,dashboardBTN,logOutBTN,settingsBTN,notificationBTN,chatBubbleBTN;

    @FXML
    void callChatBubble() {

    }

    @FXML
    void callDashboard() throws IOException {
        Stage stage = (Stage) dashboardBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    void callHelpAndFaq() throws IOException {
        Stage stage = (Stage) helpAndFaqBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("helpAndFaq.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    void callLogOut() {

    }

    @FXML
    void callNotifications() {

    }

    @FXML
    void callPatientsAndScans() throws IOException {
        Stage stage = (Stage) patientsAndScansBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("patientsAndScans.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }

    @FXML
    void callSettings() {

    }
}
