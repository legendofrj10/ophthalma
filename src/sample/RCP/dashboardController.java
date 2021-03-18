package sample.RCP;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.common;
import sample.user;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Objects;

public class dashboardController {

    public Button receiptSystemBTN;
    public Button appointmentBTN;
    public Button labtestsBTN;
    public ImageView userImg;
    public Label userNameTxt;
    public Label roleTxt;
    public StackPane navbarStackpane;
    public Label designationTxt;
    public Label emailTxt;
    public Label phnoTxt;
    public Label joiningDateTxt;
    public LineChart weaklyLineChart;
    public Label newPatientsTxt;
    public Label appointmentsTodayTxt;
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
    public void initialize(){
        userNameTxt.setText(user.getUserName());
        roleTxt.setText(user.getRole());
        designationTxt.setText(user.getDesignation());
        phnoTxt.setText(user.getPhno());
        emailTxt.setText(user.getWorkEmail());
        joiningDateTxt.setText(user.getJoiningDate());


        Connection con = common.getConnect();

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(String.format("select * from everydayDetails where date='%s'",sample.common.getToday()));

            while(rs.next()){
                appointmentsTodayTxt.setText(String.valueOf(rs.getInt(2)));
                newPatientsTxt.setText(String.valueOf(rs.getInt(3)));
            }




            rs = st.executeQuery("SELECT * FROM everydayDetails");

            XYChart.Series seriesAppointment = new XYChart.Series();
            seriesAppointment.setName("Appointments");

            XYChart.Series seriesNewPatients = new XYChart.Series();
            seriesNewPatients.setName("New Patients");

            LocalDate lastDay = common.getToday();
            int i=0;
            while(rs.next() && i<7){
                seriesAppointment.getData().add(new XYChart.Data(rs.getDate(1).toLocalDate().getDayOfWeek().name(),rs.getInt(2)));
                seriesNewPatients.getData().add(new XYChart.Data(rs.getDate(1).toLocalDate().getDayOfWeek().name(),rs.getInt(3)));
                if(!rs.getDate(1).toLocalDate().equals(lastDay)) i++;
            }
            weaklyLineChart.getData().add(seriesAppointment);
            weaklyLineChart.getData().add(seriesNewPatients);



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    void callChatBubble() throws IOException {
        Stage stage = (Stage) chatBubbleBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("chatBubble.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callDashboard() throws IOException {
        Stage stage = (Stage) dashboardBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
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
        Stage stage = (Stage) notificationBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("notifications.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callSettings() throws IOException {
        Stage stage = (Stage) settingsBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("settings.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    public void callAppointment() throws IOException{
        Stage stage = (Stage) appointmentBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("appointment.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    public void callReceiptSystem() throws IOException {
        Stage stage = (Stage) receiptSystemBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("receipt.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

}
