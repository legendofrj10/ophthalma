package sample.RCP;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.sql.*;
import java.time.LocalDate;

public class modifyAppointmentController {

    public ListView appointmentList;
    public Label patientName;
    public ComboBox departmentCB;
    public DatePicker datePicker;
    @FXML
    private TextField patientIDTxt;

    @FXML
    private Button saveBTN;

    @FXML
    private Button deleteBTN;

    @FXML
    private StackPane innerStack;

    Date date;
    String department="";
    String data;

    @FXML
    public void initialize(){
        innerStack.setVisible(false);
        desabler();
    }

    @FXML
    void callDelete( ) {
        Connection con = sample.common.getConnect();
        try {
            Statement st = con.createStatement();
            st.executeUpdate(
                    String.format("DELETE FROM appointments  WHERE patient like '%s' AND date like '%s' AND department like '%s'",
                            patientIDTxt.getText(), date, data.substring(12))
            );


            ResultSet rs = st.executeQuery(
                    String.format("SELECT * FROM everydayDetails WHERE date like '%s'",date)
            );

            if (rs.next()){
                int appointmentsCount=rs.getInt(2);
                appointmentsCount--;
                st.executeUpdate(
                        String.format("UPDATE everydayDetails SET totalAppointments='%d' WHERE date like '%s'",
                                appointmentsCount,date)
                );
            }

            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        desabler();
        callSearch();
    }

    @FXML
    void callSave( ) {
        Connection con = sample.common.getConnect();
        try {
            Statement st = con.createStatement();
            st.executeUpdate(
                    String.format("UPDATE appointments SET department='%s',date='%s' WHERE patient like '%s' AND date like '%s' AND department like '%s'",
                            departmentCB.getValue(), datePicker.getValue(), patientIDTxt.getText(), date, data.substring(12))
            );

            LocalDate newDate = datePicker.getValue();

            ResultSet rs = st.executeQuery(
                    String.format("SELECT * FROM everydayDetails WHERE date like '%s'",date)
            );
            if (rs.next()){
                if(!newDate.equals(date.toLocalDate())){
                    int appointmentsCount = rs.getInt(2);
                    appointmentsCount--;
                    st.executeUpdate(String.format("UPDATE everydayDetails SET totalAppointments='%d' WHERE date like '%s'",appointmentsCount,date));

                    ResultSet rs1=st.executeQuery(String.format("SELECT * FROM everydayDetails WHERE date like '%s'",newDate));
                    if(rs1.next()){
                        appointmentsCount=rs1.getInt(2);
                        appointmentsCount++;
                        st.executeUpdate(String.format("UPDATE everydayDetails SET totalAppointments='%d' WHERE date like '%s'",appointmentsCount,newDate));
                    }else{
                        st.executeUpdate(String.format("INSERT INTO everydayDetails VALUES ('%s',1,0,0)", newDate));
                    }

                }
            }

            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        desabler();
        callSearch();
    }

    @FXML
    void callSearch( ) {
        innerStack.setVisible(true);
        appointmentList.getItems().clear();
        Connection con = sample.common.getConnect();

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(String.format("SELECT * FROM appointments WHERE patient='%s'", patientIDTxt.getText()));
            while(rs.next()){
                date = rs.getDate(1);
                department = rs.getString(3);
                String app = date + "  "+department;
                appointmentList.getItems().add(app);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }



    public void callGetAppointment() {
        data = (String) appointmentList.getSelectionModel().getSelectedItem();
        enabler();

        datePicker.setValue(LocalDate.parse(data.substring(0,10)));
        date = Date.valueOf(datePicker.getValue());
        departmentCB.setValue(data.substring(12));

    }

    void enabler(){
        saveBTN.setVisible(true);
        deleteBTN.setVisible(true);
        departmentCB.setVisible(true);
        datePicker.setVisible(true);
    }

    void desabler(){
        saveBTN.setVisible(false);
        deleteBTN.setVisible(false);
        departmentCB.setVisible(false);
        datePicker.setVisible(false);
    }
}
