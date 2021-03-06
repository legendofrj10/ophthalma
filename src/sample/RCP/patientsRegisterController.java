package sample.RCP;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static sample.common.getConnect;

public class patientsRegisterController {

    public Label patientAddedTxt;
    public Button registerBTN;
    String Query;

    @FXML
    private TextField patientNameField;

    @FXML
    private ComboBox<?> patientGenderCB;

    @FXML
    private DatePicker patientDOB;

    @FXML
    private Button backBTN;


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
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("appointment.fxml")));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);

    }

    @FXML
    void callRegister() {
        String name = patientNameField.getText();
        String gender = (String) patientGenderCB.getValue();
        LocalDate DOB = patientDOB.getValue();
        LocalDate lastCheckup = LocalDate.now();
        int ageYears = Period.between(DOB,sample.common.getToday()).getYears();
        int PatientNumeric_id;
        String patient_id;
        System.out.println(name+"  "+gender+"  "+DOB+"  "+ageYears);
        try{
            Connection con= getConnect();
            Statement st = con.createStatement();
            ResultSet rs;
            PatientNumeric_id=newPatientID();
            patient_id = "Pat"+PatientNumeric_id;

            Query = String.format("INSERT INTO patients (name,age,dob,gender,numeric_id,last_diagnosed,patient_id) VALUES ('%s','%d','%s','%s','%d','%s %s','%s')", name, ageYears, DOB, gender, PatientNumeric_id, lastCheckup, DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalTime.now()), patient_id);
            st.executeUpdate(Query);

            patientAddedTxt.setText("New Patient Registered\nPatient ID : "+patient_id);
            patientNameField.setText("");
            patientGenderCB.setValue(null);
            patientDOB.setValue(null);

            rs = st.executeQuery(String.format("SELECT * FROM everydayDetails WHERE date like '%s'", sample.common.getToday()));
            int patCount;
            if(rs.next()){
                patCount = rs.getInt(3);
                patCount++;
                st.executeUpdate(String.format("UPDATE everydayDetails SET newPatients='%d' WHERE date like '%s'", patCount, sample.common.getToday()));

            }else{
                st.executeUpdate(String.format("INSERT INTO everydayDetails VALUES ('%s',0,1,0)", sample.common.getToday()));
            }

            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
