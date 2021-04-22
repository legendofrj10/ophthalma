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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public Label confermationTxt;
    public Button confermBTN;
    public StackPane confermPane;

    String test="";

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
    int pAge = 0;


    @FXML
    public void initialize() {
        remarks.setWrapText(true);
        System.out.println(patID);

        String Query = String.format("SELECT * FROM patients WHERE patient_id='%s'", patID);
        System.out.println(Query);
        try{
            Connection con = sample.common.getConnect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(Query);
            while (rs.next()) {
                pName = rs.getString("name");
                pGender = rs.getString("gender");
                pRemark = rs.getString("remarks");
                pAge = rs.getInt("age");
            }
            con.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        patientName.setText(pName);
        patientAge.setText(String.valueOf(pAge));
        if(pGender.equals("male")){
            gender.setImage(new Image("sample/Assets/male.png"));
        }else{
            gender.setImage(new Image("sample/Assets/female.png"));
        }
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
            int visits;
            if(rs.next()){
                visits = rs.getInt(4);
                visits++;
                st.executeUpdate(String.format("UPDATE everydayDetails SET visitsToday='%d'" +
                                " WHERE date like '%s'",
                        visits,sample.common.getToday()));
            }else{
                st.executeUpdate(String.format("INSERT INTO everydayDetails VALUES " +
                        "('%s',0,0,1)",
                        sample.common.getToday()));
            }

            rs = st.executeQuery(String.format("SELECT * FROM visits WHERE Date like '%s'", sample.common.getToday()));
            String ageGrp= String.format("patientAge%d_%d", pAge + 1 - pAge % 10, pAge + 10 - pAge%10) ;
            String gender= String.format("%sVisits",pGender.equals("male")?"male":"female");
            if(rs.next()){
                int genderCount = rs.getInt(gender);
                genderCount++;
                int count = rs.getInt(ageGrp);
                count++;
                st.executeUpdate(String.format("UPDATE visits SET %s='%d',%s='%d' WHERE Date like '%s'",ageGrp,count,gender,genderCount,sample.common.getToday()));
            }else{
                st.executeUpdate(String.format("INSERT INTO visits (Date,%s,%s) value ('%s',1,1)",ageGrp,gender,sample.common.getToday()));
            }

            st.executeUpdate(String.format("INSERT INTO visited VALUES ('%s','%s','%s','%s')",sample.common.getToday(),patID,sample.user.getDesignation(),sample.user.getUserName()));

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
        test = labtestName.getText();
        confermPane.setVisible(true);
        confermationTxt.setText(String.format("Patient %s will be assigned with\nlabtest %s",patientName.getText(),test));
    }

    public void callAssignIt( ) {
        Connection con = sample.common.getConnect();
        try {
            con.createStatement().executeUpdate("INSERT INTO labtests (assignmentDate,patient,testName) VALUES ('" + sample.common.getToday() + "','" + patID + "','" + test + "')");
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        confermPane.setVisible(false);
    }

    public void callDontAssign(MouseEvent mouseEvent) {
        confermPane.setVisible(false);
        labtestName.setText("");
    }
}
