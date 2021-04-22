package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class completeProfileController {
    public Label errorphno;
    String Query;
    public static String IDLoggedIn;
    public static String nameLoggedIn;

    @FXML
    private ComboBox gendr;

    @FXML
    private Label NameUser,UID;

    @FXML
    private TextField phNo, Designation,secEmail,Address;

    @FXML
    private Button submitBTN;

    @FXML
    public void initialize(){
        IDLoggedIn=user.getUserID();
        nameLoggedIn=user.getUserName();
        NameUser.setText(nameLoggedIn);
        UID.setText(IDLoggedIn);
    }




    public void CompleteSubmit() {
        String Gender = (String) gendr.getValue();
        String Desig = Designation.getText();
        String PHNo = phNo.getText();
        String secondaryEmail = secEmail.getText();
        String Addr = Address.getText();

        try {
            double d = Double.parseDouble(PHNo);
            errorphno.setText("");
            try{
                Connection con = common.getConnect();
                Query = String.format("UPDATE HMS SET profileComplete=1, " +
                                "gender='%s',Designation='%s',mobileNumber=%s," +
                                "workEmail='%s',Address='%s' WHERE UserID='%s'"
                        , Gender, Desig, PHNo, secondaryEmail, Addr,
                        IDLoggedIn);
                Statement st = con.createStatement();
                System.out.println(Query);
                st.executeUpdate(Query);
                con.close();
                Stage stage = (Stage) submitBTN.getScene().getWindow();

                user.profileCompleted=true;
                user.gender=Gender;
                user.designation=Desig;
                user.phno=PHNo;
                user.workEmail=secondaryEmail;
                user.address=Addr;

                con.close();

                loginController.Dashboard(stage);

            }catch (Exception e){
                e.printStackTrace();
            }
        }catch(NumberFormatException e){
            errorphno.setText("Enter Correct Number");
        }






    }



}
