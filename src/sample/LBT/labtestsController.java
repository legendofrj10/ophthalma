package sample.LBT;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class labtestsController {

    @FXML
    private Button backBTN;

    @FXML
    private TextField patientIDTxt;

    @FXML
    private Button searchBTN;

    @FXML
    private StackPane innerStackPane;

    @FXML
    void callBack( ) throws IOException {
        Stage stage = (Stage) backBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callSearch( ) {

    }

}
