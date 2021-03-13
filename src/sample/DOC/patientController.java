package sample.DOC;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class patientController {

    static String patID="";
    public Label patientAge;
    public Button editRemarksBtn;
    public Button generatePrescriptionBTN;
    public TextField labtestName;
    public Button assignLabtestBTN;

    @FXML
    private Button backBtn;

    @FXML
    private TextArea remarks;

    @FXML
    private Label patientName;

    @FXML
    private ImageView gender;



    @FXML
    public void initialize() {
        remarks.setWrapText(true);
        System.out.println(patID);
        String pName = "";
        String pGender = "";
        String pRemark = "";
        String pAge = "";
        String Query = "SELECT * FROM patients WHERE patient_id='" + patID + "'";
        System.out.println(Query);
        try{
            Connection con = sample.common.getConnect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(Query);
            while (rs.next()) {
                pName = rs.getString("name");
                pGender = rs.getString("gender");
                pRemark = rs.getString("remarks");
                pAge = rs.getString("age");
            }
            con.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        patientName.setText(pName);
        patientAge.setText("( " + pAge + " )");
        Image image;
        if(pGender.equals("male")){
            image = new Image("file:src/photos/male.png");
        }else{
            image = new Image("file:src/photos/female.png");
        }
        gender.setImage(image);

        remarks.setText(pRemark);

    }

    @FXML
    void callBack() throws IOException {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("patientsAndScans.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }


    @FXML
    void callEditRemarks() {
        String remark = remarks.getText();
        String Query = "UPDATE patients SET remarks = '" + remark + "',last_diagnosed = '"
                + LocalDate.now() + " " + DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalTime.now())
                + "' WHERE patient_id = '" + patID + "'";
        try{
            Connection con = sample.common.getConnect();
            Statement st = con.createStatement();
            st.executeUpdate(Query);

            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void callGeneratePrescription() throws IOException {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("prescription.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    public void callAssignLabtest() {
    }
}
