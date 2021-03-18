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
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Locale;
import java.util.Scanner;


public class loginController {

    public Button quitBTN;
    static String uid="";
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

    public static boolean profile=false;


    @FXML
    public static void init(Stage primaryStage) throws Exception{
        File fUName = new File(common.getWorkingDirectory()+"dbUName");
        File fPass = new File(common.getWorkingDirectory()+"dbPass");
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

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("OSPITALITY");
        primaryStage.show();

    }

    @FXML
    void callLogin() {
        Scanner reader;

        try (FileInputStream ipFile = new FileInputStream(sample.common.getWorkingDirectory()+"ip")) {
            reader = new Scanner(ipFile);
            while (reader.hasNextLine()){
                common.IP = reader.nextLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        uid = loginIdField.getText();
        pass = loginPassField.getText();
        System.out.println(uid+"    "+pass);
        if(uid.length()!=0 && pass.length()!=0){
            String Query;
            try{
                Connection con = sample.common.getConnect();
                Statement st = con.createStatement();
                ResultSet rs=st.executeQuery(String.format("SELECT * FROM HMS WHERE userID='%s'", uid));
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
                    user.profileCompleted=rs.getInt(4)==1;
                }

                if(pass.equals(password)){
                    user.setPassword(password);
                    Stage stage = (Stage)loginbtn.getScene().getWindow();

                    common.setUserLoggedIn(uid);
                    Dashboard(stage);
                }else{
                    loginerror.setText("PLEASE CHECK CREDENTIALS!!!!");
                }
                con.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            loginerror.setText("ID OR PASSWORD CAN'T BE EMPTY!!!!");
        }

    }


    @FXML
    static void Dashboard(Stage stage) throws Exception{
        String dashboardPath = user.userID.substring(0, 3);
        System.out.println(dashboardPath);
        Parent root = FXMLLoader.load(loginController.class.getResource(dashboardPath + "/dashboard.fxml"));

        if(!user.profileCompleted){
            root=FXMLLoader.load(loginController.class.getResource("completeProfile.fxml"));
        }
        Scene sc = stage.getScene();
        assert root != null;
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void troubleLogin() {
    }


    public void callQuit() {
        System.exit(0);
    }
}
