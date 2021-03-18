package sample;

public class user {
    public static boolean profileCompleted;
    static String userName="";
    static String userID="";
    static String password="";
    static String gender="";
    static String role="";
    static String designation="";
    static String phno="";
    static String workEmail="";
    static String personalEmail="";
    static String address="";
    static String joiningDate="";

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        user.password = password;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getUserID() {
        return userID;
    }

    public static String getGender() {
        return gender;
    }

    public static String getRole() {
        return role;
    }

    public static String getDesignation() {
        return designation;
    }

    public static String getPhno() {
        return phno;
    }

    public static String getWorkEmail() {
        return workEmail;
    }

    public static String getPersonalEmail() {
        return personalEmail;
    }

    public static String getAddress() {
        return address;
    }

    public static String getJoiningDate() {
        return joiningDate;
    }
}
