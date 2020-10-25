package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class patientsAndScansController {
    static int i=0;

//    public ListView<String> patientList;
    public ListView<String> patientListName;
    public ListView<String> patientListLD;
    public ListView<String> patientListPE;
    @FXML
    private Button registerPatientBTN;

    @FXML
    private Button helpAndFaqBTN,patientsAndScansBTN,dashboardBTN,logOutBTN,settingsBTN,notificationBTN,chatBubbleBTN;

    static Connection getConnect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/OPHTHALMA","yashraj","Raj"
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
    void callChatBubble() {

    }

    @FXML
    void callDashboard() throws IOException {
        i=0;
        Stage stage = (Stage) dashboardBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callHelpAndFaq() throws IOException {
        i=0;
        Stage stage = (Stage) helpAndFaqBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("helpAndFaq.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callLogOut() throws IOException {
        Stage stage = (Stage) logOutBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("logout.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callNotifications() {

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
    void callSettings() {

    }

    @FXML
    void callRegisterPatient() throws IOException {
        i=0;
        Stage stage = (Stage) registerPatientBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("patientsRegister.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }
    @FXML
    void callPatientsList() {
  //      String line = "Name                Last Diagnosed                Problematic Eye";
        if(i<5) {
            patientListName.getItems().add("Name");
            patientListLD.getItems().add("Last Diagnosed");
            patientListPE.getItems().add("Problematic Eye");

        }
        if(i<5){
            try{

                Connection con = getConnect();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from patients order by last_diagnosed desc");
                while(rs.next() && i<5){
                    String name = rs.getString("name");
                    String lD = rs.getString("last_diagnosed");
                    String pE = rs.getString("problematicEye");
                    /*line = name + new String(new char[21-name.length()]).replace('\0', ' ')
                            + lD + new String(new char[30-lD.length()]).replace('\0',' ')
                            + pE;*/
                    patientListName.getItems().add(name);
                    patientListLD.getItems().add(lD);
                    patientListPE.getItems().add(pE);
                    i++;
                }
                closeConnect(con);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
