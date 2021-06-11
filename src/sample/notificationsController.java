package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.stage.Popup;

public class notificationsController {

    @FXML
    private ScrollPane contentPane;

    @FXML
    private Button closeNotificationBtn;

    @FXML
    void callClose(ActionEvent event) {
        Popup popup = (Popup) closeNotificationBtn.getScene().getWindow();
        popup.hide();
    }

}
