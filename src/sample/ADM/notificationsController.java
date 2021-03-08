package sample.ADM;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class notificationsController {
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
        Scene scene = new Scene(root);
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
    void callLogOut() throws IOException {
        Stage stage = (Stage) logOutBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("logout.fxml"));
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
    void callSettings() throws IOException {
        Stage stage = (Stage) settingsBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("settings.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }
}
