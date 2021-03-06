package sample.DOC;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import sample.common;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class patientsAndScansController {
    static int i=0;


    public ListView<String> patientListName;
    public ListView<String> patientListLD;
    public ListView<String> patientListPE;
    public Button searchBtn;
    public TextField searchPatientField;
    public Label noPatientMatchLbl;
    public StackPane mainpane;
    public StackPane navbarStackpane;

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


    @FXML
    public void initialize() throws SQLException {
        i=0;
        Connection con = common.getConnect();
        Statement st = con.createStatement();

        patientListName.getItems().add("NAME");
        patientListName.getItems().add("  ");

        patientListLD.getItems().add("LAST DIAGNOSED");
        patientListLD.getItems().add("  ");

        patientListPE.getItems().add("PATIENT ID");
        patientListPE.getItems().add("  ");

        ResultSet rs = st.executeQuery(String.format("SELECT * FROM visited WHERE department like '%s'",sample.user.getDesignation()));

        while(rs.next() && i<5){
            patientListPE.getItems().add(rs.getString(2));

            ResultSet rs1 = sample.common.getConnect().createStatement().executeQuery(
                    String.format("SELECT name,last_diagnosed FROM patients WHERE patient_id like '%s'", rs.getString("patient"))
            );
            String name="";
            String lD="";
            if(rs1.next()){
                name = rs1.getString("name");
                lD = rs1.getString("last_diagnosed");
            }rs1.close();
            patientListName.getItems().add(name);
            patientListLD.getItems().add(lD);
            i++;
        }

        con.close();
    }

    public void callSearch() throws SQLException, IOException {
        Connection con = sample.common.getConnect();
        Statement st = con.createStatement();

        if(st.executeQuery(String.format("SELECT * FROM appointments WHERE patient like '%s' AND date like '%s'", searchPatientField.getText(),sample.common.getToday())).next()){
            String id = searchPatientField.getText();
            String Query = String.format("SELECT * FROM patients WHERE patient_id='%s'", id);
            ResultSet rs = st.executeQuery(Query);
            if(rs.next()){
                patientController.patID = id;
                Stage stage = (Stage) searchBtn.getScene().getWindow();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("patient.fxml")));
                Scene sc = stage.getScene();
                Scene scene  = new Scene(root,sc.getWidth(),sc.getHeight());
                stage.setScene(scene);
            }
            else{
                noPatientMatchLbl.setText("No such patient registered!");
            }
            con.close();
        }else{
            noPatientMatchLbl.setText("No appointment Today For Patient "+searchPatientField.getText());
        }
    }
}
