package sample.RCP;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;


public class newAppointmentController {

    public Label successTxt;
    @FXML
    private TextField patientIDTxt;

    @FXML
    private ComboBox<?> chooseDepartmentCB;

    @FXML
    private Button newPatientBTN;

    @FXML
    private Button submitBTN;

    @FXML
    private DatePicker datePicker;

    @FXML
    void callNewPatient( ) throws IOException {
        Stage stage = (Stage) newPatientBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("patientsRegister.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callSubmit( ) {
        String id  = patientIDTxt.getText();
        String department = (String) chooseDepartmentCB.getValue();
        LocalDate date = datePicker.getValue();

        Connection con = sample.common.getConnect();

        try {
            Statement st  = con.createStatement();
            st.executeUpdate("INSERT INTO appointments (date,patient,department) values('"+date+"','"+id+"','"+department+"')");

            successTxt.setText("Patient "+ id +" now Has an appointment for "+ department +" on "+date+" .");
            patientIDTxt.setText("");
            chooseDepartmentCB.setValue(null);
            datePicker.setValue(null);

            ResultSet rs;
            rs = st.executeQuery(String.format("SELECT * FROM everydayDetails WHERE date like '%s'", sample.common.getToday()));
            int appCount=0;
            if(rs.next()){
                appCount = rs.getInt(2);
                appCount++;
            }
            rs = st.executeQuery(String.format("SELECT * FROM everydayDetails WHERE date like '%s'", sample.common.getToday()));
            if(rs.next()){
                st.executeUpdate(String.format("UPDATE everydayDetails SET totalAppointments='%d'", appCount));

            }else{
                st.executeUpdate(String.format("INSERT INTO everydayDetails VALUES ('%s',1,0,0)", sample.common.getToday()));
            }

            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

}
