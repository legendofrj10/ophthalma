package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;

public class chatBubbleController {

    @FXML
    private StackPane mainpane;

    @FXML
    private ScrollPane listPane;

    @FXML
    private ScrollPane chatBox;

    @FXML
    private TextField textToSend;

    @FXML
    private Button sendBtn;

    @FXML
    private Button closeChatBtn;

    @FXML
    private Button backBtn;

    @FXML
    void callCheckEnter() {

    }

    @FXML
    void callCloseChat() {

        Popup popup = (Popup) closeChatBtn.getScene().getWindow();
        popup.hide();

    }

    @FXML
    void callSend() {

    }

}
