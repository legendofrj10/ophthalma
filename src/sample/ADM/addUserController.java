package sample.ADM;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.completeProfileController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class addUserController {

    String Query;

    @FXML
    private ComboBox<?> jobChoice;

    @FXML
    private Label passnotmatch;

    @FXML
    private TextField Name,email,two_pass,two_passconf;

    @FXML
    private Button backButton,two_SignUPbtn;

    public void backtologin() throws Exception{
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../login.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    static Connection getConnect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/OPHTHALMA","yashraj","Raj"
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
    void profileComplete(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("completeProfile.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
        stage.show();
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
    public void submitSignUp() throws Exception {
        completeProfileController.nameLoggedIn = Name.getText();
        String job = (String) jobChoice.getValue();
        String emailAddress = email.getText();
        String pass = two_pass.getText();
        String confPass = two_passconf.getText();
        int uID = newUserID();//==0?1001:newUserID()+1;
        completeProfileController.IDLoggedIn = job.equals("Doctor")?"DOC"+uID:"LAB"+uID;
        System.out.println(completeProfileController.nameLoggedIn+" "+ completeProfileController.IDLoggedIn);
        if(pass.equals(confPass)){
            passnotmatch.setText("");
            try{
                Connection con = getConnect();
                Statement st = con.createStatement();
                Query = "INSERT INTO HMS (userName,UserID,NumericID,Role,personalEmail,PassWord) VALUES " +
                        "('" + completeProfileController.nameLoggedIn + "','" + completeProfileController.IDLoggedIn + "','" + uID + "','" + job + "','" + emailAddress + "','" + pass +"')";
                st.executeUpdate(Query);
                closeConnect(con);
            }catch(Exception e){
                e.printStackTrace();
            }
            Stage stage = (Stage)two_SignUPbtn.getScene().getWindow();
            profileComplete(stage);
        }else{
            passnotmatch.setText("passwords don't match!!!");
        }


    }

}
