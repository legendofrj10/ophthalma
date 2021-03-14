package sample.RCP;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import static sample.common.getConnect;

public class patientsRegisterController {

    String Query;

    @FXML
    private TextField patientNameField;

    @FXML
    private ComboBox<?> patientGenderCB;

    @FXML
    private DatePicker patientDOB;

    @FXML
    private ComboBox<?> patientEyeCB;

    @FXML
    private Button registerBTN;

    @FXML
    private Button backBTN;



    /*String newPatient(){
        int ID=1000;
        try{
            Connection con = getConnect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select patient_id from patients");
            while(rs.next()){
                ID++;
            }
            closeConnect(con);
        }catch (Exception e){
            e.printStackTrace();
        }
        ID++;
        String i = "PAT"+ID;
        return i;
    }*/

    int newPatientID(){
        int ID=1000;
        int IDt;
        try{
            Connection con1 = sample.common.getConnect();
            Statement stmt = con1.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT numeric_id FROM patients");
            while(rs.next()){
                IDt=rs.getInt("numeric_id");
                ID=Math.max(IDt, ID);
            }
            con1.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        ID++;
        return ID;
    }


    @FXML
    void callBack() throws IOException {
        Stage stage = (Stage) backBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("appointment.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);

    }

    @FXML
    void callRegister() {
        String name = patientNameField.getText();
        String gender = (String) patientGenderCB.getValue();
        LocalDate DOB = patientDOB.getValue();
        String eye = (String) patientEyeCB.getValue();
        LocalDate lastCheckup = LocalDate.now();
        Period age = Period.between(DOB,lastCheckup);
        int PatientNumeric_id;
        String patient_id;
        int ageYears = age.getYears();
        System.out.println(name+"  "+gender+"  "+DOB+"  "+eye+"  "+ageYears);
        try{
            Connection con= getConnect();
            Statement st = con.createStatement();
            Query = "SELECT * FROM patients WHERE name='"+name+"' AND dob='"+DOB+"'";
            ResultSet rs = st.executeQuery(Query);
            if(rs.next()){
                Query = "UPDATE patients SET last_diagnosed='" + lastCheckup + " " +
                        DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalTime.now()) + "' WHERE name='"+name+"' AND dob='"+DOB+"'";
            }
            else{
                PatientNumeric_id=newPatientID();
                patient_id = "Pat"+PatientNumeric_id;
                Query = "INSERT INTO patients (name,age,dob,gender,numeric_id,last_diagnosed,problematicEye,patient_id) VALUES " +
                        "('" + name + "','" + ageYears + "','" + DOB + "','" + gender + "','" + PatientNumeric_id + "','" +
                        lastCheckup + " " + DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalTime.now()) + "','" + eye + "','" + patient_id + "')";
            }
            st.executeUpdate(Query);

            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
