package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Scanner;


public class loginController {

    public Button quitBTN;
    static String uid="";
    public AnchorPane troublePane;
    public Button callRequest;
    public Label callHide;
    public StackPane mainPane;
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
        root = FXMLLoader.load(Objects.requireNonNull(loginController.class.getResource("login.fxml")));

        if(fUName.exists() && fPass.exists() && fUName.length()!=0 && fPass.length()!=0){
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

            System.out.println(common.getN());
            System.out.println(common.getP());
        }else{
            root = FXMLLoader.load(Objects.requireNonNull(loginController.class.getResource("database.fxml")));
        }

        Scanner reader;

        try (FileInputStream ipFile = new FileInputStream(sample.common.getWorkingDirectory()+"ip")) {  //1
            reader = new Scanner(ipFile);   //2
            while (reader.hasNextLine()){   //3
                common.IP = reader.nextLine();  //4
            }   //5
        }   //6
        catch (IOException e) {   //7
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("OSPITALITY");
        primaryStage.show();

    }

    @FXML
    void callLogin() {


        uid = loginIdField.getText();
        pass = loginPassField.getText();
        System.out.println(uid+"    "+pass);
        if(uid.length()!=0 && pass.length()!=0){    //1
            try{    //2
                Connection con = sample.common.getConnect();
                Statement st = con.createStatement();   //3
                ResultSet rs=st.executeQuery(String.format("SELECT * FROM HMS WHERE userID='%s'", uid));    //4
                String password="";
                while(rs.next()){   //5
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
                }   //6

                if(pass.equals(password)){  //7
                    user.setPassword(password); //8
                    Stage stage = (Stage)loginbtn.getScene().getWindow();

                    common.setUserLoggedIn(uid);
                    Dashboard(stage);
                }
                else{   //9
                    loginerror.setText("PLEASE CHECK CREDENTIALS!!!!");
                }
                con.close();
            }   //10
            catch (Exception e){    //11
                e.printStackTrace();
            }
        }
        else{   //12
            loginerror.setText("ID OR PASSWORD CAN'T BE EMPTY!!!!");
        }

    }   //13


    @FXML
    static void Dashboard(Stage stage) throws Exception{
        String dashboardPath = user.userID.substring(0, 3);
        System.out.println(dashboardPath);
        Parent root = FXMLLoader.load(Objects.requireNonNull(loginController.class.getResource(dashboardPath + "/dashboard.fxml")));

        if(!user.profileCompleted){
            root=FXMLLoader.load(Objects.requireNonNull(loginController.class.getResource("completeProfile.fxml")));
        }
        Scene sc = stage.getScene();
        assert root != null;
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void troubleLogin() {
        //mainPane.setDisable(true);
        Connection con = common.getConnect();

        troublePane.setVisible(true);
    }


    public void callQuit() {
        System.exit(0);
    }

    public void callRequest() {
        Connection con = common.getConnect();
        String id = loginIdField.getText();
        System.out.println(id);
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(String.format("INSERT INTO passwordRequests VALUES ('%s')", id));
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void callHide() {
        troublePane.setVisible(false);
        mainPane.setDisable(false);
    }
}
