package sample.DOC;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class patientController {

    static String patID="";
    public Label patientAge;

    @FXML
    private Button backBtn;

    @FXML
    private TextArea remarks;

    @FXML
    private Label patientName;

    @FXML
    private ImageView gender;

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
    public void initialize() {
        remarks.setWrapText(true);
        System.out.println(patID);
        String pName = "";
        String pGender = "";
        String pRemark = "";
        String pAge = "";
        String Query = "SELECT * FROM patients WHERE patient_id='" + patID + "'";
        System.out.println(Query);
        try{
            Connection con = getConnect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(Query);
            while (rs.next()) {
                pName = rs.getString("name");
                pGender = rs.getString("gender");
                pRemark = rs.getString("remarks");
                pAge = rs.getString("age");
            }
            closeConnect(con);

        }catch(Exception e){
            e.printStackTrace();
        }
        patientName.setText(pName);
        patientAge.setText("( " + pAge + " )");
        Image image;
        if(pGender.equals("male")){
            image = new Image("file:src/photos/male.png");
        }else{
            image = new Image("file:src/photos/female.png");
        }
        gender.setImage(image);

        remarks.setText(pRemark);

    }

    @FXML
    void callBack() throws IOException {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("patientsAndScans.fxml"));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }


    @FXML
    void callEditRemarks() {
        String remark = remarks.getText();
        String Query = "UPDATE patients SET remarks = '" + remark + "',last_diagnosed = '"
                + LocalDate.now() + " " + DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalTime.now())
                + "' WHERE patient_id = '" + patID + "'";
        try{
            Connection con = getConnect();
            Statement st = con.createStatement();
            st.executeUpdate(Query);

            closeConnect(con);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

}
