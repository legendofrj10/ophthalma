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

import java.io.IOException;
import java.sql.*;

public class patientsAndScansController {
    static int i=0;

    public ListView<String> patientListName;
    public ListView<String> patientListLD;
    public ListView<String> patientListPE;
    public Button searchBtn;
    public TextField searchPatientField;
    public Label noPatientMatchLbl;
    @FXML
    private Button registerPatientBTN;

    @FXML
    private Button helpAndFaqBTN,patientsAndScansBTN,dashboardBTN,logOutBTN,settingsBTN,notificationBTN,chatBubbleBTN;

    static Connection getConnect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/OPHTHALMA",sample.common.getN(),sample.common.getP()
            );
            System.out.println("Connection established");
            return con;
        }catch (Exception e){
            e.printStackTrace();
        }
        System.exit(0);
        return getConnect();
    }

    static void closeConnect(Connection con){
        try{
            con.close();
            System.out.println("Connection closed");
        }catch (Exception e){
            e.printStackTrace();
        }
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
    void callHelpAndFaq() throws IOException {
        Stage stage = (Stage) helpAndFaqBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("helpAndFaq.fxml"));
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
    void callRegisterPatient() throws IOException {
        Stage stage = (Stage) registerPatientBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../RCP/patientsRegister.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }
    @FXML
    public void initialize() throws SQLException {
        i=0;
        Connection con = getConnect();
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
        closeConnect(con);
    }

    public void callSearch() throws SQLException, IOException {
        String id = searchPatientField.getText();
        Connection con = getConnect();
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
        closeConnect(con);
    }
}
