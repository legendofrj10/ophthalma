package sample.DOC;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class logoutController {

    @FXML
    private Button noBTN;

    @FXML
    private Button yesBTN;

    @FXML
    void callNo() throws IOException {
        Stage stage = (Stage) noBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callYes() throws IOException {
        Stage stage = (Stage) yesBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../login.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

}
