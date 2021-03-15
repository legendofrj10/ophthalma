package sample.DOC;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.common;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class patientsAndScansController {
    static int i=0;

    public ListView<String> patientListName;
    public ListView<String> patientListLD;
    public ListView<String> patientListPE;
    public Button searchBtn;
    public TextField searchPatientField;
    public Label noPatientMatchLbl;

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
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/logout.fxml"))
);
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


    @FXML
    public void initialize() throws SQLException {
        i=0;
        Connection con = common.getConnect();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from patients order by last_diagnosed desc");

        patientListName.getItems().add("NAME");
        patientListName.getItems().add("  ");

        patientListLD.getItems().add("LAST DIAGNOSED");
        patientListLD.getItems().add("  ");

        patientListPE.getItems().add("PATIENT ID");
        patientListPE.getItems().add("  ");

        try{
            while(rs.next() && i<5){
                String name = rs.getString("name");
                String lD = rs.getString("last_diagnosed");
                String pE = rs.getString("patient_id");
                patientListName.getItems().add(name);
                patientListLD.getItems().add(lD);
                patientListPE.getItems().add(pE);
                i++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        con.close();
    }

    public void callSearch() throws SQLException, IOException {
        String id = searchPatientField.getText();
        Connection con = common.getConnect();
        Statement st = con.createStatement();
        String Query = "SELECT * FROM patients WHERE patient_id='" + id + "'";
        ResultSet rs = st.executeQuery(Query);
        if(rs.next()){
            patientController.patID = id;
            Stage stage = (Stage) searchBtn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("patient.fxml"));
            Scene sc = stage.getScene();
            Scene scene  = new Scene(root,sc.getWidth(),sc.getHeight());
            stage.setScene(scene);
        }
        else{
            noPatientMatchLbl.setText("No such patient registered!");
        }
        con.close();
    }
}
