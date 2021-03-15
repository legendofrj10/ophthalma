package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;


public class loginController {

    public Button quitBTN;
    String uid="";
    String pass="";

    @FXML
    public Label loginerror;

    @FXML
    private TextField loginIdField;

    @FXML
    private PasswordField loginPassField;

    @FXML
    private Button loginbtn;
    @FXML
    private Button troublebtn;

    @FXML
    public static void login(Stage primaryStage) throws Exception{
        File fUName = new File("dbUName");
        File fPass = new File("dbPass");
        Parent root;
        root = FXMLLoader.load(loginController.class.getResource("login.fxml"));
        if(fUName.exists() && fPass.exists()){
            FileInputStream fisName = new FileInputStream(fUName);
            FileInputStream fisPass = new FileInputStream(fPass);
            Scanner reader;

            reader = new Scanner(fisName);

            while(reader.hasNextLine()){
            common.setN(reader.nextLine());
            }
            reader.close();
            reader = new Scanner(fisPass);

            while (reader.hasNextLine()){
                common.setP(reader.nextLine());
            }
            reader.close();

            System.out.println(sample.common.getN());
            System.out.println(sample.common.getP());
        }else{
            root = FXMLLoader.load(loginController.class.getResource("database.fxml"));
        }
        primaryStage.setTitle("OSPITALITY");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    @FXML
    void Dashboard(Stage stage) throws Exception{
        String dashboardPath = uid.substring(0, 3);
        System.out.println(dashboardPath);
        Parent root = null;
        switch (dashboardPath) {
            case "ADM":
                root = FXMLLoader.load(getClass().getResource("ADM/dashboard.fxml"));
                break;
            case "RCP":
                root = FXMLLoader.load(getClass().getResource("RCP/dashboard.fxml"));
                break;
            case "DOC":
                root = FXMLLoader.load(getClass().getResource("DOC/dashboard.fxml"));
                break;
            case "LBT":
                root = FXMLLoader.load(getClass().getResource("LBT/dashboard.fxml"));
                break;
            case "MDC":
                root = FXMLLoader.load(getClass().getResource("MDC/dashboard.fxml"));
                break;
        }
        Scene sc = stage.getScene();
        assert root != null;
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
        stage.show();
    }




    @FXML
    void checkLogin() {
        uid = loginIdField.getText();
        pass = loginPassField.getText();
        System.out.println(uid+"    "+pass);
        if(uid.length()!=0 && pass.length()!=0){
            String Query = "SELECT * FROM HMS WHERE userID='"+uid+"'";
            try{
                Connection con = sample.common.getConnect();
                Statement st = con.createStatement();
                ResultSet rs=st.executeQuery(Query);
                String password="";
                while(rs.next()){
                    password=rs.getString("PassWord");
                    user.userName=rs.getString("userName");
                    user.userID=rs.getString("UserID");
                    user.gender=rs.getString("gender");
                    user.role=rs.getString("Role");
                    user.designation=rs.getString("Designation");
                    user.phno=rs.getString("mobileNumber");
                    user.workEmail=rs.getString("workEmail");
                    user.personalEmail=rs.getString("personalEmail");
                    user.address=rs.getString("Address");
                    user.joiningDate=rs.getString("Joining");
                }

                if(pass.equals(password)){
                    Stage stage = (Stage)loginbtn.getScene().getWindow();

                    common.setUserLoggedIn(uid);
                    Dashboard(stage);
                }else{
                    loginerror.setText("Wrong password!!!!");
                }
                con.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            loginerror.setText("ID or Password Cannot be empty!!!!");
        }

    }

    @FXML
    void troubleLogin() {
    }


    public void callQuit() {
        System.exit(0);
    }
}
