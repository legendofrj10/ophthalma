package sample;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class common {

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


}
