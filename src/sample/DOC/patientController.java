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

    String pName = "";
    String pGender = "";
    String pRemark = "";
    String pAge = "";


    @FXML
    public void initialize() {
        remarks.setWrapText(true);
        System.out.println(patID);

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
            image = new Image("file:src/sample/photos/male.png");
        }else{
            image = new Image("file:src/sample/photos/female.png");
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
        String Query = String.format("UPDATE patients SET remarks = '%s',last_diagnosed " +
                "= '%s %s' WHERE patient_id = '%s'", remark, LocalDate.now(),
                DateTimeFormatter.ofPattern("HH:mm:ss").
                format(LocalTime.now()), patID);
        try{
            Connection con = sample.common.getConnect();
            Statement st = con.createStatement();
            st.executeUpdate(Query);

            ResultSet rs;
            rs = st.executeQuery(String.format("SELECT * FROM everydayDetails " +
                    "WHERE date like '%s'", sample.common.getToday()));
            int visits=0;
            int males=0;
            int females=0;
            if(rs.next()){
                visits = rs.getInt(4);
                visits++;
                males = rs.getInt(5);
                females = rs.getInt(6);

            }
            if (pGender.equals("male")) {
                males++;
            } else {
                females++;
            }
            rs = st.executeQuery(String.format("SELECT * FROM everydayDetails WHERE date " +
                    "like '%s'", sample.common.getToday()));
            if(rs.next()){
                st.executeUpdate(String.format("UPDATE everydayDetails SET visitsToday='%d'" +
                        ",maleVisits='%d',femaleVisits='%d' WHERE date like '%s'",
                        visits,males,females,sample.common.getToday()));

            }else{
                st.executeUpdate(String.format("INSERT INTO everydayDetails VALUES " +
                        "('%s',0,0,1,'%d','%d')",
                        sample.common.getToday(), males, females));
            }

            if(!st.executeQuery(String.format("SELECT * FROM visits WHERE Date like '%s'", sample.common.getToday())).next()){
                st.executeUpdate(String.format("INSERT INTO visits (Date) value ('%s')",sample.common.getToday()));
            }
            rs = st.executeQuery(String.format("SELECT * FROM visits WHERE Date like '%s'", sample.common.getToday()));
            while(rs.next()){

            }

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
