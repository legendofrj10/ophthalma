package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

public class databaseController {
    @FXML
    private Label errorLoginTxt;
    @FXML
    private TextField userIdTxt;

    @FXML
    private Button loginBTN;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    void callLogin(ActionEvent event) throws Exception {
        String uname = userIdTxt.getText();
        char[] Uname = uname.toCharArray();
        String password = passwordTxt.getText();
        char[] Password = password.toCharArray();

        Connection con;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306", uname, password
            );
            errorLoginTxt.setText("");
            DatabaseMetaData dbm;
            dbm = con.getMetaData();
            ResultSet rst;
            rst = dbm.getCatalogs();


            try(FileOutputStream fos = new FileOutputStream("dbUName")) {
                int i=0;
                while (i< Uname.length){
                    fos.write(Uname[i]);
                    i++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try(FileOutputStream fos = new FileOutputStream("dbPass")) {
                int i=0;
                while (i<password.length()){
                    fos.write(Password[i]);
                    i++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            boolean databaseFound=false;

            while (rst.next()) {
                if(rst.getString(1).equals("OPHTHALMA")){

                    databaseFound=true;
                    con.close();
                    //System.out.println(rst.getString(1));
                    /*ResultSet rstt = con.getMetaData().getTables(null,null,null,null);
                    while(rstt.next() && rst.getString(1).equals("OPHTHALMA")){
                        System.out.println(rstt.getString("TABLE_NAME"));
                    }*/
                }

            }
            if(databaseFound==true){
                System.out.println("Database Found");
                //NOW CHECK FOR TABLES
                con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/OPHTHALMA", uname, password
                );
                rst = con.getMetaData().getTables(null,null,"%",null);
                while(rst.next()){
                    System.out.println(rst.getString("TABLE_NAME"));
                }

                loginController.login((Stage)loginBTN.getScene().getWindow());
            }else{
                System.out.println("Database not found!!");

            }




        }catch (SQLException e){
            errorLoginTxt.setText("Please check your credentials!!!");
        }catch (Exception e){
            e.printStackTrace();
        }


    }

}
