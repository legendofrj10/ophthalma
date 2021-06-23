package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class logoutController {


    public Label noLogOutBtn;
    @FXML
    private Button yesBTN;

    @FXML
    void callNo() throws IOException {

        Popup popup = (Popup) yesBTN.getScene().getWindow();
        popup.hide();

        Stage stage = common.mainStage;
        String dir = common.getUserLoggedIn().substring(0,3);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(dir + "/dashboard.fxml")));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callYes() throws IOException {
        Popup popup = (Popup) yesBTN.getScene().getWindow();
        popup.hide();

        Stage stage = common.mainStage;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

}
