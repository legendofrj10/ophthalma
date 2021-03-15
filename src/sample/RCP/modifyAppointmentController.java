package sample.RCP;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;

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
    private Button searchBTN;

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
        departmentCB.setVisible(false);
        datePicker.setVisible(false);
        saveBTN.setVisible(false);
        deleteBTN.setVisible(false);
    }

    @FXML
    void callDelete( ) {
        Connection con = sample.common.getConnect();
        try {
            Statement st = con.createStatement();
            st.executeUpdate(
                    "DELETE FROM appointments  WHERE patient " +
                            "like '"+patientIDTxt.getText()+"' AND date like '"+LocalDate.parse(data.substring(0,10)) +
                            "' AND department like '"+data.substring(12)+"'");
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        callSearch();
    }

    @FXML
    void callSave( ) {
        Connection con = sample.common.getConnect();
        try {
            Statement st = con.createStatement();
            st.executeUpdate(
                    "UPDATE appointments SET department='"+ departmentCB.getValue() +"',date='"+datePicker.getValue()+"' WHERE patient " +
                            "like '"+patientIDTxt.getText()+"' AND date like '"+LocalDate.parse(data.substring(0,10)) +
                            "' AND department like '"+data.substring(12)+"'");
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        callSearch();
    }

    @FXML
    void callSearch( ) {
        innerStack.setVisible(true);
        appointmentList.getItems().clear();
        Connection con = sample.common.getConnect();

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM appointments WHERE patient='"+patientIDTxt.getText()+"'");
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



    public void callGetAppointment(MouseEvent mouseEvent) {
        data = (String) appointmentList.getSelectionModel().getSelectedItem();
        saveBTN.setVisible(true);
        deleteBTN.setVisible(true);
        departmentCB.setVisible(true);
        datePicker.setVisible(true);

        datePicker.setValue(LocalDate.parse(data.substring(0,10)));
        departmentCB.setValue(data.substring(12));

    }
}
