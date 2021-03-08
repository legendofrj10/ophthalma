package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class loginController {

    String uid="";
    String pass="";

    @FXML
    public Label loginerror;

    @FXML
    private TextField loginIdField;

    @FXML
    private PasswordField loginPassField;

    @FXML
    private Button loginbtn,signupbtn,troublebtn;

    @FXML
    public static void login(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(loginController.class.getResource("login.fxml"));
        primaryStage.setTitle("OPHTHALMA");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML
    void signUp() throws Exception{
        Stage stage;
        Parent root;
        stage = (Stage) signupbtn.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("DOC/signup.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(), sc.getHeight());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void Dashboard(Stage stage) throws Exception{
        String dashboardPath = uid.substring(0, 3);
        System.out.println(dashboardPath);
        Parent root = null;
        if(dashboardPath.equals("ADM")){
            root = FXMLLoader.load(getClass().getResource("ADM/dashboard.fxml"));
        }else if(dashboardPath.equals("RCP")){
            root = FXMLLoader.load(getClass().getResource("RCP/dashboard.fxml"));
        }else if(dashboardPath.equals("DOC")){
            root = FXMLLoader.load(getClass().getResource("DOC/dashboard.fxml"));
        }else if(dashboardPath.equals("LBT")){
            root = FXMLLoader.load(getClass().getResource("LBT/dashboard.fxml"));
        }else if(dashboardPath.equals("MDC")){
            root = FXMLLoader.load(getClass().getResource("MDC/dashboard.fxml"));
        }
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
        stage.show();
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
    void checkLogin() {
        uid = loginIdField.getText();
        pass = loginPassField.getText();
        System.out.println(uid+"    "+pass);
        if(uid.length()!=0 && pass.length()!=0){
            String Query = "SELECT PassWord FROM HMS WHERE userID='"+uid+"'";
            try{
                Connection con = getConnect();
                Statement st = con.createStatement();
                ResultSet rs=st.executeQuery(Query);
                String password="";
                while(rs.next()){
                    password=rs.getString("PassWord");
                }

                if(pass.equals(password)){
                    Stage stage = (Stage)loginbtn.getScene().getWindow();
                    Dashboard(stage);
                }else{
                    loginerror.setText("Wrong password!!!!");
                }
                closeConnect(con);
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


}
