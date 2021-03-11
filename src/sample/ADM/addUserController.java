package sample.ADM;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.completeProfileController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class addUserController {

    public Label userAddedTxt;
    String Query;

    @FXML
    private ComboBox<?> jobChoice;

    @FXML
    private Label passnotmatch;

    @FXML
    private TextField Name,email,two_pass,two_passconf;

    @FXML
    private Button backButton,two_SignUPbtn;



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


    int newUserID(){
        int ID=1000;
        try{
            Connection con1 = getConnect();
            Statement stmt = con1.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NumericID FROM HMS");
            while(rs.next()){
                ID=rs.getInt("NumericID");
            }
            con1.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        ID++;
        return ID;
    }

    @FXML
    public void submitSignUp() {
        String userName = Name.getText();
        String job = (String) jobChoice.getValue();
        String emailAddress = email.getText();
        String pass = two_pass.getText();
        String confPass = two_passconf.getText();
        int uID = newUserID();//==0?1001:newUserID()+1;
        String fullUID;
        if (job.equals("Doctor")) {
            fullUID = "DOC"+uID;
        } else {
            if (job.equals("Admin")) {
                fullUID = ("ADM"+uID);
            } else {
                if (job.equals("Receptionist")) fullUID = "RCP" + uID;
                else {
                    if (job.equals("Lab Technician")) fullUID = "LBT" + uID;
                    else fullUID = "MDC" + uID;
                }
            }
        }
        System.out.println(userName +" "+ completeProfileController.IDLoggedIn);
        if(pass.equals(confPass)){
            passnotmatch.setText("");
            try{
                Connection con = getConnect();
                Statement st = con.createStatement();
                Query = "INSERT INTO HMS (userName,UserID,NumericID,Role,personalEmail,PassWord) VALUES " +
                        "('" + completeProfileController.nameLoggedIn + "','" + fullUID + "','" + uID + "','" + job + "','" + emailAddress + "','" + pass +"')";
                st.executeUpdate(Query);
                {
                    String userAddedGreet = "User Account for "+userName+" has been created.\nUSER ID : "+fullUID+"\nPASSWORD : "+confPass;
                    userAddedTxt.setText(userAddedGreet);
                    Name.setText("");
                    email.setText("");
                    two_pass.setText("");
                    two_passconf.setText("");
                }
                closeConnect(con);
            }catch(Exception e){
                e.printStackTrace();
            }

        }else{
            passnotmatch.setText("passwords don't match!!!");
        }


    }

}
