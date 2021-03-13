package sample.ADM;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class accountsManagementController {

    @FXML
    private Button accountsManagementBtn;

    @FXML
    private Button dashboardBTN;

    @FXML
    private Button logOutBTN;

    @FXML
    private Button settingsBTN;

    @FXML
    private Button notificationBTN;

    @FXML
    private Button chatBubbleBTN;

    @FXML
    private Button addUserBtn;

    @FXML
    private Button modifyUserBtn;

    @FXML
    private BorderPane innerStage;

    @FXML
    void callAccountsManagement() {

    }

    @FXML
    void callAddUser() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("addUser.fxml"));
        innerStage.setCenter(root);
        addUserBtn.setDisable(true);
        modifyUserBtn.setDisable(false);
    }

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
    void callLogOut() throws IOException {
        Stage stage = (Stage) logOutBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../logout.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callModifyUser() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("modifyUser.fxml"));
        innerStage.setCenter(root);
        addUserBtn.setDisable(false);
        modifyUserBtn.setDisable(true);
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
    void callSettings() throws IOException {
        Stage stage = (Stage) settingsBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("settings.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

}
