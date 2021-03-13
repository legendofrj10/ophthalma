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

@SuppressWarnings("ALL")
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

    @FXML
    public void initialize(){
        String user = sample.common.getUserLoggedIn();

        Connection con = common.getConnect();
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
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
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
        barX.setLabel("Year");
        barY.setLabel("Number of patients");
        Connection con = common.getConnect();

        int yearCount=0;
        List<Integer> yearList = new ArrayList<>();
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

            common.setYearsOfData(yearCount);

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

            int totalMaleAllTime = common.getArraySum(malePatients,malePatients.length);
            int totalFemaleAllTime = common.getArraySum(femalePatients,femalePatients.length);
            int totalGrp1AllTime = common.getArraySum(agegrp1,agegrp1.length);
            int totalGrp2AllTime = common.getArraySum(agegrp2,agegrp2.length);
            int totalGrp3AllTime = common.getArraySum(agegrp3,agegrp3.length);
            int totalGrp4AllTime = common.getArraySum(agegrp4,agegrp4.length);
            int totalGrp5AllTime = common.getArraySum(agegrp5,agegrp5.length);
            int totalGrp6AllTime = common.getArraySum(agegrp6,agegrp6.length);
            int totalGrp7AllTime = common.getArraySum(agegrp7,agegrp7.length);
            int totalTotalAllTime = totalFemaleAllTime+totalMaleAllTime;

            series.setName("patients");
            for(int j=0;j<yearCount;j++){
                series.getData().add(new XYChart.Data(String.valueOf(yearList.get(j)),totalPatients[j]));
            }

            patientsBarChart.getData().addAll(series);

            ObservableList<PieChart.Data> pieChartGender = FXCollections.observableArrayList(
              new PieChart.Data("Male",totalMaleAllTime),
              new PieChart.Data("Female",totalFemaleAllTime)
            );

            genderPieChart.setData(pieChartGender);
            //ratioPieChart = new PieChart(pieChartGender);
            genderPieChart.setClockwise(true);

            ObservableList<PieChart.Data> pieChartAge = FXCollections.observableArrayList(
                    new PieChart.Data("1-10",totalGrp1AllTime),
                    new PieChart.Data("11-20",totalGrp2AllTime),
                    new PieChart.Data("21-30",totalGrp3AllTime),
                    new PieChart.Data("31-40",totalGrp4AllTime),
                    new PieChart.Data("41-50",totalGrp5AllTime),
                    new PieChart.Data("51-60",totalGrp6AllTime),
                    new PieChart.Data("61+",totalGrp7AllTime)
            );

            ratioPieChart.setData(pieChartAge);
            ratioPieChart.setClockwise(true);
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void callMonthly() {
        monthlyBTN.setDisable(true);
        yearlyBTN.setDisable(false);
        weaklyBTN.setDisable(false);
        barX.setLabel("Month");
        barY.setLabel("Number of patients");
        patientsBarChart.getData().clear();

        Connection con = common.getConnect();

        int lastMonth = common.getToday().getMonthValue();
        List<Integer> monthList = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM visits");

            int[] malePatients = new int[12];
            int[] femalePatients = new int[12];
            int[] agegrp1 = new int[12];
            int[] agegrp2 = new int[12];
            int[] agegrp3 = new int[12];
            int[] agegrp4 = new int[12];
            int[] agegrp5 = new int[12];
            int[] agegrp6 = new int[12];
            int[] agegrp7 = new int[12];
            int[] totalPatients = new int[12];

            int i = 0;

            while(rs.next()){
                int month = rs.getDate(1).toLocalDate().getMonthValue();
                monthList.add(month);
                if(month!=lastMonth){
                    i++;
                    lastMonth=month;
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

            int totalMaleThisYear = common.getArraySum(malePatients,malePatients.length);
            int totalFemaleThisYear = common.getArraySum(femalePatients,femalePatients.length);
            int totalGrp1ThisYear = common.getArraySum(agegrp1,agegrp1.length);
            int totalGrp2ThisYear = common.getArraySum(agegrp2,agegrp2.length);
            int totalGrp3ThisYear = common.getArraySum(agegrp3,agegrp3.length);
            int totalGrp4ThisYear = common.getArraySum(agegrp4,agegrp4.length);
            int totalGrp5ThisYear = common.getArraySum(agegrp5,agegrp5.length);
            int totalGrp6ThisYear = common.getArraySum(agegrp6,agegrp6.length);
            int totalGrp7ThisYear = common.getArraySum(agegrp7,agegrp7.length);
            int totalTotalThisYear = totalFemaleThisYear+totalMaleThisYear;


            XYChart.Series series = new XYChart.Series();
            series.setName("patients");
            lastMonth=0;
            for(int j=0;j<monthList.size();j++) {
                if (monthList.get(j) != lastMonth) {
                    lastMonth=monthList.get(j);
                    System.out.println(malePatients[j] + "  " + femalePatients[j]);
                    series.getData().add(new XYChart.Data(String.valueOf(common.getMonthName(monthList.get(j)-1)), totalPatients[0]));
                }
            }

            patientsBarChart.getData().addAll(series);

            ObservableList<PieChart.Data> pieChartGender = FXCollections.observableArrayList(
                    new PieChart.Data("Male",totalMaleThisYear),
                    new PieChart.Data("Female",totalFemaleThisYear)
            );

            genderPieChart.setData(pieChartGender);
            //ratioPieChart = new PieChart(pieChartGender);
            genderPieChart.setClockwise(true);

            ObservableList<PieChart.Data> pieChartAge = FXCollections.observableArrayList(
                    new PieChart.Data("1-10",totalGrp1ThisYear),
                    new PieChart.Data("11-20",totalGrp2ThisYear),
                    new PieChart.Data("21-30",totalGrp3ThisYear),
                    new PieChart.Data("31-40",totalGrp4ThisYear),
                    new PieChart.Data("41-50",totalGrp5ThisYear),
                    new PieChart.Data("51-60",totalGrp6ThisYear),
                    new PieChart.Data("61+",totalGrp7ThisYear)
            );

            ratioPieChart.setData(pieChartAge);
            ratioPieChart.setClockwise(true);
            con.close();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public void callWeakly(ActionEvent actionEvent) {
        monthlyBTN.setDisable(false);
        yearlyBTN.setDisable(false);
        weaklyBTN.setDisable(true);
        barX.setLabel("Days");
        barY.setLabel("Number of patients");
        patientsBarChart.getData().clear();


        Connection con = common.getConnect();

        int lastMonth = common.getToday().getMonthValue();
        List<Integer> DaysList = new ArrayList<Integer>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM visits");

            int[] malePatients = new int[28];
            int[] femalePatients = new int[28];
            int[] agegrp1 = new int[28];
            int[] agegrp2 = new int[28];
            int[] agegrp3 = new int[28];
            int[] agegrp4 = new int[28];
            int[] agegrp5 = new int[28];
            int[] agegrp6 = new int[28];
            int[] agegrp7 = new int[28];
            int[] totalPatients = new int[28];

            int i = 0;

            while(rs.next() && i<28){
                int day = rs.getDate(1).toLocalDate().getDayOfMonth();
                DaysList.add(day);

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
                i++;
            }

            int totalMaleThisMonth = common.getArraySum(malePatients,malePatients.length);
            int totalFemaleThisMonth = common.getArraySum(femalePatients,femalePatients.length);
            int totalGrp1ThisMonth = common.getArraySum(agegrp1,agegrp1.length);
            int totalGrp2ThisMonth = common.getArraySum(agegrp2,agegrp2.length);
            int totalGrp3ThisMonth = common.getArraySum(agegrp3,agegrp3.length);
            int totalGrp4ThisMonth = common.getArraySum(agegrp4,agegrp4.length);
            int totalGrp5ThisMonth = common.getArraySum(agegrp5,agegrp5.length);
            int totalGrp6ThisMonth = common.getArraySum(agegrp6,agegrp6.length);
            int totalGrp7ThisMonth = common.getArraySum(agegrp7,agegrp7.length);
            int totalTotalThisMonth = totalFemaleThisMonth+totalMaleThisMonth;

            XYChart.Series series = new XYChart.Series();
            series.setName("patients");
            lastMonth=0;
            for(int j=0;j<DaysList.size();j++) {
                System.out.println(malePatients[j] + "  " + femalePatients[j]);
                series.getData().add(new XYChart.Data(String.valueOf(DaysList.get(j)), totalPatients[j]));
            }

            patientsBarChart.getData().addAll(series);



            ObservableList<PieChart.Data> pieChartGender = FXCollections.observableArrayList(
                    new PieChart.Data("Male",totalMaleThisMonth),
                    new PieChart.Data("Female",totalFemaleThisMonth)
            );

            genderPieChart.setData(pieChartGender);
            //ratioPieChart = new PieChart(pieChartGender);
            genderPieChart.setClockwise(true);

            ObservableList<PieChart.Data> pieChartAge = FXCollections.observableArrayList(
                    new PieChart.Data("1-10",totalGrp1ThisMonth),
                    new PieChart.Data("11-20",totalGrp2ThisMonth),
                    new PieChart.Data("21-30",totalGrp3ThisMonth),
                    new PieChart.Data("31-40",totalGrp4ThisMonth),
                    new PieChart.Data("41-50",totalGrp5ThisMonth),
                    new PieChart.Data("51-60",totalGrp6ThisMonth),
                    new PieChart.Data("61+",totalGrp7ThisMonth)
            );

            ratioPieChart.setData(pieChartAge);
            ratioPieChart.setClockwise(true);

            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
