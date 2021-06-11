package sample;

import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;

public class common {

    public static Stage mainStage;

    public static String workingDirectory="";

    public static String getWorkingDirectory() {
        return workingDirectory;
    }

    public static void setWorkingDirectory(String workingDirectory) {
        common.workingDirectory = workingDirectory;
    }

    public static String getIP() {
        return IP;
    }

    public static String IP = "";

    public static Connection getConnect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://"+common.getIP()+":3306/OSPITALITY",sample.common.getN(),sample.common.getP()
            );
            System.out.println("Connection established");
            return con;
        }catch (Exception e){
            e.printStackTrace();
        }
        System.exit(0);
        return getConnect();
    }


    private static String dbn="";
    public static void setN(String n){
        dbn=n;
    }
    public static String getN(){
        return dbn;
    }

    private static String dbp="";
    public static void setP(String p){
        dbp=p;
    }
    public static String getP(){
        return dbp;
    }

    private static String userLoggedIn="";
    public static void setUserLoggedIn(String n){
        userLoggedIn=n;
    }
    public static String getUserLoggedIn(){
        return userLoggedIn;
    }

    public static LocalDate getToday(){
        return LocalDate.now();
    }

    private static int yearsOfData=0;
    public static void setYearsOfData(int n)  {
        yearsOfData=n;
    }
    public static int getYearsOfData() {
        return yearsOfData;
    }

    private static final String[] months = {"january","february","march","april",
    "may","june","july","august","september","october",
    "november","december"};

    private static final String[] days = {"monday","tuesday","wednesday","thursday","friday","saturday"};

    public static String getMonthName(int i){
        return months[i];
    }

    public static String getDays(int i) {
        return days[i];
    }

    public static int getArraySum(int[] arr, int size) {
        int sum = 0;
        for(int i=0;i<size;i++) sum += arr[i];
        return sum;
    }


}
