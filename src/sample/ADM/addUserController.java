package sample.ADM;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.completeProfileController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

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





    int newUserID(){
        int ID=1000;
        try{
            Connection con1 = sample.common.getConnect();
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
                Connection con = sample.common.getConnect();
                Statement st = con.createStatement();
                Query = "INSERT INTO HMS (userName,UserID,NumericID,Role,personalEmail,PassWord,Joining) VALUES " +
                        "('" + completeProfileController.nameLoggedIn + "','" + fullUID + "','" + uID + "','" + job + "','" + emailAddress + "','" + pass + "','" + LocalDate.now() +"')";
                st.executeUpdate(Query);
                {
                    String userAddedGreet = "User Account for "+userName+" has been created.\nUSER ID : "+fullUID+"\nPASSWORD : "+confPass;
                    userAddedTxt.setText(userAddedGreet);
                    Name.setText("");
                    email.setText("");
                    two_pass.setText("");
                    two_passconf.setText("");
                }
                con.close();
            }catch(Exception e){
                e.printStackTrace();
            }

        }else{
            passnotmatch.setText("passwords don't match!!!");
        }


    }

}
