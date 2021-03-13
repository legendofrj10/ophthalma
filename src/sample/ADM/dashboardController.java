package sample.ADM;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.common;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class dashboardController {

    public BarChart patientsBarChart;
    public PieChart ratioPieChart;
    public ImageView userImg;
    public Label userNameTxt;
    public Label roleTxt;
    public Label designationTxt;
    public Label emailTxt;
    public Label phnoTxt;
    public Label joiningDateTxt;
    public PieChart genderPieChart;
    public Button yearlyBTN;
    public Button monthlyBTN;
    public Button weaklyBTN;
    public CategoryAxis barX;
    public NumberAxis barY;
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
    private Button accountsManagementBTN;

    static Connection getConnect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/OPHTHALMA",sample.common.getN(),sample.common.getP()
            );
            System.out.println("Connection established");
            return con;
        }catch (Exception e){
            e.printStackTrace();
        }
        System.exit(0);
        return getConnect();
    }

    static void closeConnect(Connection con){
        try{
            con.close();
            System.out.println("Connection closed");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize(){
        String user = sample.common.getUserLoggedIn();

        Connection con = getConnect();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM HMS WHERE UserID='"+user+"'");
            while (rs.next()) {
                userNameTxt.setText(rs.getString("userName"));
                roleTxt.setText(rs.getString("Role"));
                emailTxt.setText(rs.getString("workEmail"));
                phnoTxt.setText(rs.getString("mobileNumber"));
                designationTxt.setText(rs.getString("Designation"));
                joiningDateTxt.setText(rs.getString("Joining"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            closeConnect(con);
        }

        callYearly();
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
        Parent root = FXMLLoader.load(getClass().getResource("../logout.fxml"));
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
    void callAccountsManagement() throws IOException {
        Stage stage = (Stage) accountsManagementBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("accountsManagement.fxml"));
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

    public void callYearly( ) {
        monthlyBTN.setDisable(false);
        yearlyBTN.setDisable(true);
        weaklyBTN.setDisable(false);
        //patientsBarChart.getData().add(null);
        barX.setLabel("Year");
        barY.setLabel("Number of patients");
        Connection con = getConnect();

        int currentYear = common.getToday().getYear();

        int yearCount=0;
        List<Integer> yearList = new ArrayList<Integer>();
        Statement st;

        XYChart.Series series = new XYChart.Series();

        patientsBarChart.getData().clear();

        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM visits");
            int lastYear = 0;
            while (rs.next()){
                LocalDate  date = rs.getDate(1).toLocalDate();
                int year = date.getYear();
                if(year!=lastYear){
                    yearCount++;
                    lastYear=year;
                    yearList.add(lastYear);
                }
            }

            int[] malePatients = new int[yearCount];
            int[] femalePatients = new int[yearCount];
            int[] agegrp1 = new int[yearCount];
            int[] agegrp2 = new int[yearCount];
            int[] agegrp3 = new int[yearCount];
            int[] agegrp4 = new int[yearCount];
            int[] agegrp5 = new int[yearCount];
            int[] agegrp6 = new int[yearCount];
            int[] agegrp7 = new int[yearCount];
            int[] totalPatients = new int[yearCount];

            rs = st.executeQuery("SELECT * FROM visits");
            lastYear = 0;
            int i=-1;
            while(rs.next()){
                LocalDate date = rs.getDate(1).toLocalDate();
                int year = date.getYear();
                if(year!=lastYear){
                    i++;
                    lastYear=year;
                }
                malePatients[i]+=rs.getInt(2);
                femalePatients[i]+=rs.getInt(3);
                agegrp1[i]+=rs.getInt(4);
                agegrp2[i]+=rs.getInt(5);
                agegrp3[i]+=rs.getInt(6);
                agegrp4[i]+=rs.getInt(7);
                agegrp5[i]+=rs.getInt(8);
                agegrp6[i]+=rs.getInt(9);
                agegrp7[i]+=rs.getInt(10);
                totalPatients[i]=malePatients[i]+femalePatients[i];

            }

            series.setName("patients");
            for(int j=0;j<yearCount;j++){
                series.getData().add(new XYChart.Data(String.valueOf(yearList.get(j)),totalPatients[j]));
            }

            patientsBarChart.getData().addAll(series);

            ObservableList<PieChart.Data> pieChartGender = FXCollections.observableArrayList(
              new PieChart.Data("Male",malePatients[0]),
              new PieChart.Data("Female",femalePatients[0])
            );

            genderPieChart.setData(pieChartGender);
            //ratioPieChart = new PieChart(pieChartGender);
            genderPieChart.setClockwise(true);

            ObservableList<PieChart.Data> pieChartAge = FXCollections.observableArrayList(
                    new PieChart.Data("1-10",agegrp1[0]),
                    new PieChart.Data("11-20",agegrp2[0]),
                    new PieChart.Data("21-30",agegrp3[0]),
                    new PieChart.Data("31-40",agegrp4[0]),
                    new PieChart.Data("41-50",agegrp5[0]),
                    new PieChart.Data("51-60",agegrp6[0]),
                    new PieChart.Data("61+",agegrp7[0])
            );

            ratioPieChart.setData(pieChartAge);
            ratioPieChart.setClockwise(true);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }






    }

    public void callMonthly(ActionEvent actionEvent) {
        monthlyBTN.setDisable(true);
        yearlyBTN.setDisable(false);
        weaklyBTN.setDisable(false);
        barX.setLabel("Month");
        barY.setLabel("Number of patients");
        patientsBarChart.getData().clear();

    }

    public void callWeakly(ActionEvent actionEvent) {
        monthlyBTN.setDisable(false);
        yearlyBTN.setDisable(false);
        weaklyBTN.setDisable(true);
        barX.setLabel("Days");
        barY.setLabel("Number of patients");
        patientsBarChart.getData().clear();

    }
}
