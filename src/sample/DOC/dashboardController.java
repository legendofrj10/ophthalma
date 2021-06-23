package sample.DOC;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class dashboardController {

    public Label todaysAppointmentsTxt;
    public Label todaysRemainingAppsTxt;
    public ImageView userImg;
    public Label userNameTxt;
    public Label designationTxt;
    public Label emailTxt;
    public Label phnoTxt;
    public Label joiningDateTxt;
    public Label roleTxt;
    public StackPane navbarStackpane;
    public StackPane mainpane;
    @FXML
    private Button patientsAndScansBTN;
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
    void initialize(){
        Connection con = sample.common.getConnect();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(String.format(
                    "SELECT * FROM appointments WHERE date like '%s' AND " +
                            "department like '%s'",sample.common.getToday(),sample.user.getDesignation()));
            int totalAppointment=0;
            while (rs.next()){
                totalAppointment++;
            }
            rs=st.executeQuery(
                    String.format(
                            "SELECT * FROM visited WHERE date like '%s' AND " +
                                    "department like '%s'",sample.common.getToday(),sample.user.getDesignation())
            );
            int visited=0;
            while (rs.next()){
                visited++;
            }

            todaysAppointmentsTxt.setText((String.valueOf( totalAppointment)));
            todaysRemainingAppsTxt.setText((String.valueOf( totalAppointment-visited)));
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        userNameTxt.setText(sample.user.getUserName());
        roleTxt.setText(sample.user.getRole());
        designationTxt.setText(sample.user.getDesignation());
        emailTxt.setText(sample.user.getWorkEmail());
        phnoTxt.setText(sample.user.getPhno());
        joiningDateTxt.setText(sample.user.getJoiningDate());


    }

    @FXML
    void callChatBubble() throws IOException {
        Popup popup = new Popup();

        popup.getContent().add(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/chatBubble.fxml"))));

        popup.show(notificationBTN.getScene().getWindow(),
                mainpane.getWidth()-20,
                mainpane.getHeight()-20 );

    }
    @FXML
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
    void callNotifications() throws IOException {
        Popup popup = new Popup();

        popup.getContent().add(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/notifications.fxml"))));

        popup.show(notificationBTN.getScene().getWindow(),
                mainpane.getWidth()-20,
                100 );


    }

    @FXML
    void callPatientsAndScans() throws IOException {
        Stage stage = (Stage) patientsAndScansBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("patientsAndScans.fxml")));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);

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
