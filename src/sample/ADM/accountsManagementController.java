package sample.ADM;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class accountsManagementController {

    public StackPane mainpane;
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
    public void initialize() throws IOException {
        callAddUser();
    }

    @FXML
    void callAddUser() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addUser.fxml")));
        innerStage.setCenter(root);
        addUserBtn.setDisable(true);
        modifyUserBtn.setDisable(false);
    }

    @FXML
    void callChatBubble() throws IOException {
        Popup popup = new Popup();

        popup.getContent().add(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/chatBubble.fxml"))));

        popup.show(notificationBTN.getScene().getWindow(),
                mainpane.getWidth()-20,
                mainpane.getHeight()-20 );

    }    @FXML
    void callDashboard() throws IOException {
        Stage stage = (Stage) dashboardBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dashboard.fxml")));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callLogOut() throws IOException {


        Popup popup = new Popup();



        popup.getContent().add(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/logout.fxml"))));

        popup.show(logOutBTN.getScene().getWindow());

        BoxBlur blur = new BoxBlur(3,3,3);

        mainpane.setEffect(blur);


    }

    @FXML
    void callModifyUser() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("modifyUser.fxml")));
        innerStage.setCenter(root);
        addUserBtn.setDisable(false);
        modifyUserBtn.setDisable(true);
    }

    @FXML
    void callNotifications() throws IOException {
        Popup popup = new Popup();

        popup.getContent().add(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/notifications.fxml"))));

        popup.show(notificationBTN.getScene().getWindow(),
                mainpane.getWidth()-20,
                100 );


    }

    @FXML
    void callSettings() throws IOException {
        Stage stage = (Stage) settingsBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/settings.fxml")));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

}
