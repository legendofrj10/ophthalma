package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class completeProController {
    String Query;
    static String IDLoggedIn;
    static String nameLoggedIn;

    @FXML
    private ComboBox gendr;

    @FXML
    private Label NameUser,UID;

    @FXML
    private TextField phNo, Designation,secEmail,Address;

    @FXML
    private Button three_submit,three_skip,three_refresh;

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

    void Dashboard(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void CompleteSubmit() {
        String Gender = (String) gendr.getValue();
        String Desig = Designation.getText();
        String PHNo = phNo.getText();
        String secmail = secEmail.getText();
        String Addr = Address.getText();

        try{
            Connection con = getConnect();
            Query = "UPDATE HMS SET gender='"+Gender+"',Designation='"
                    +Desig+"',mobileNumber="+PHNo+",workEmail='"+secmail
                    +"',Address='"+Addr+"' WHERE UserID='"+IDLoggedIn+"'";
            Statement st = con.createStatement();
            System.out.println(Query);
            st.executeUpdate(Query);
            closeConnect(con);
            Stage stage = (Stage)three_submit.getScene().getWindow();
            Dashboard(stage);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void SkipCom() throws Exception {
        Stage stage = (Stage)three_skip.getScene().getWindow();
        Dashboard(stage);

    }

    public void refresh() {
        NameUser.setText(nameLoggedIn);
        UID.setText(IDLoggedIn);
    }

}
