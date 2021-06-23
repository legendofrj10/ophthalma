package sample.RCP;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;

public class appointmentController {

    public Button receiptSystemBTN;
    public Button appointmentBTN;
    public Button newAppointmentBTN;
    public Button modifyAppointmentBTN;
    @FXML
    public BorderPane appointmentPane;
    public StackPane navbarStackpane;
    public StackPane mainpane;
    public TabPane tabPane;
    @FXML
    private Button dashboardBTN;
    @FXML
    private Button logOutBTN;
    @FXML
    private Button settingsBTN;
    @FXML
    private Button notificationBTN;
    @FXML
    private Button chatBubbleBTN;


    @FXML
    void callChatBubble() throws IOException {
        Popup popup = new Popup();

        popup.getContent().add(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/chatBubble.fxml"))));

        popup.show(notificationBTN.getScene().getWindow(),
                mainpane.getWidth()-20,
                mainpane.getHeight()-20 );

    }
    @FXML
    void callDashboard() throws IOException {
        Stage stage = (Stage) dashboardBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dashboard.fxml")));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callLogOut() throws IOException {
        Stage stage = (Stage) logOutBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/logout.fxml"))
));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callNotifications() throws IOException {
        Popup popup = new Popup();

        popup.getContent().add(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/notifications.fxml"))));

        popup.show(notificationBTN.getScene().getWindow(),
                mainpane.getWidth()-20,
                100 );


    }

    @FXML
    void callSettings() throws IOException {
        Stage stage = (Stage) settingsBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/settings.fxml")));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    public void callAppointment() throws IOException {
        Stage stage = (Stage) appointmentBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("appointment.fxml")));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    public void callReceiptSystem() throws IOException {
        Stage stage = (Stage) receiptSystemBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("receipt.fxml")));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    public void callNewAppointment() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("newAppointment.fxml")));
        appointmentPane.setCenter(root);
        newAppointmentBTN.setDisable(true);
        modifyAppointmentBTN.setDisable(false);
    }

    public void callModifyAppointment() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("modifyAppointment.fxml")));
        appointmentPane.setCenter(root);
        newAppointmentBTN.setDisable(false);
        modifyAppointmentBTN.setDisable(true);
    }

    public Label successTxt;
    public Button submitBTN;
    @FXML
    private TextField patientIDTxt1;

    @FXML
    private ComboBox<?> chooseDepartmentCB;

    @FXML
    private Button newPatientBTN;

    @FXML
    private DatePicker datePicker1;

    @FXML
    void callNewPatient( ) throws IOException {
        Stage stage = (Stage) newPatientBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("patientsRegister.fxml")));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callSubmit( ) {
        String id  = patientIDTxt1.getText();
        String department = (String) chooseDepartmentCB.getValue();
        LocalDate date = datePicker1.getValue();

        Connection con = sample.common.getConnect();

        try {
            Statement st  = con.createStatement();
            st.executeUpdate(String.format("INSERT INTO appointments (date,patient,department) values('%s','%s','%s')", date, id, department));

            successTxt.setText("Patient "+ id +" now Has an appointment for "+ department +" on "+date+" .");
            patientIDTxt1.setText("");
            chooseDepartmentCB.setValue(null);
            datePicker1.setValue(null);

            ResultSet rs;
            rs = st.executeQuery(String.format("SELECT * FROM everydayDetails WHERE date like '%s'", date));
            int appCount=0;
            if(rs.next()){
                appCount = rs.getInt(2);
                appCount++;
            }
            rs = st.executeQuery(String.format("SELECT * FROM everydayDetails WHERE date like '%s'", date));
            if(rs.next()){
                st.executeUpdate(String.format("UPDATE everydayDetails SET totalAppointments='%d' WHERE date like '%s'", appCount,date));

            }else{
                st.executeUpdate(String.format("INSERT INTO everydayDetails VALUES ('%s',1,0,0)", date));
            }

            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public ListView appointmentList;
    public Label patientName;
    public ComboBox departmentCB;
    public DatePicker datePicker;
    public Button searchBTN;
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
        tabPane.widthProperty().addListener((observable, oldValue, newValue) ->
        {
            tabPane.setTabMinWidth((tabPane.getWidth()-80) / tabPane.getTabs().size());
            tabPane.setTabMaxWidth((tabPane.getWidth()-80) / tabPane.getTabs().size());
        });
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
