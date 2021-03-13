package sample.ADM;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.sql.*;

public class modifyUserController {

    public StackPane detailsPane;
    public TextField userNameTxt;
    public TextField roleTxt;
    public TextField passwordTxt;
    public TextField designationTxt;
    public TextField genderTxt;
    public TextField phnoTxt;
    public TextField workEmailTxt;
    public TextArea addressTxt;
    public Button yesBTN;
    public Button noBTN;
    public StackPane savePane;
    public StackPane deletePane;
    public Button noDeleteBTN;
    public Button yesDeleteBTN;
    public ImageView lockImg;
    Image image;
    @FXML
    private TextField userIDTxt;

    @FXML
    private Button searchUserBtn;

    @FXML
    private Button editAddressBtn;

    @FXML
    private Button editWorkEmailBtn;

    @FXML
    private Button editGenderBtn;

    @FXML
    private Button editPhoneBtn;

    @FXML
    private Button editDesignationBtn;

    @FXML
    private Button editRoleBtn;

    @FXML
    private Button editPasswordBtn;

    @FXML
    private Button editNameBtn;

    @FXML
    private Button deleteUserBtn;

    @FXML
    private Button saveBtn;


    static Connection getConnect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/OPHTHALMA",sample.common.getN(),sample.common.getP()
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
    void callDeleteUser() {
        if(savePane.isVisible())    savePane.setVisible(false);
        deletePane.setVisible(true);
    }

    @FXML
    void callEdit() {
        if(!userNameTxt.isEditable()){
            addressTxt.setEditable(true);
            designationTxt.setEditable(true);
            genderTxt.setEditable(true);
            userNameTxt.setEditable(true);
            passwordTxt.setEditable(true);
            phnoTxt.setEditable(true);
            roleTxt.setEditable(true);
            workEmailTxt.setEditable(true);

            image = new Image("file:src/photos/unlockDark.png");

            lockImg.setImage(image);
        }else{
            addressTxt.setEditable(false);
            designationTxt.setEditable(false);
            genderTxt.setEditable(false);
            userNameTxt.setEditable(false);
            passwordTxt.setEditable(false);
            phnoTxt.setEditable(false);
            roleTxt.setEditable(false);
            workEmailTxt.setEditable(false);

            image = new Image("file:src/photos/lockDark.png");
            lockImg.setImage(image);
        }


    }

    @FXML
    void callSave( ) {
        if(deletePane.isVisible())  deletePane.setVisible(false);
        savePane.setVisible(true);
    }

    @FXML
    void callSearchUser( ) {
        String user = userIDTxt.getText();
        Connection con = getConnect();
        try {
            Statement st = con.createStatement();
            String Query = "SELECT * FROM HMS WHERE UserID = '"+user+"'";
            ResultSet rs = st.executeQuery(Query);
            if(rs.next()){
                detailsPane.setVisible(true);
                userNameTxt.setText(rs.getString("userName"));
                passwordTxt.setText(rs.getString("PassWord"));
                roleTxt.setText(rs.getString("Role"));
                designationTxt.setText(rs.getString("Designation"));
                genderTxt.setText(rs.getString("gender"));
                workEmailTxt.setText(rs.getString("workEmail"));
                phnoTxt.setText(rs.getString("mobileNumber"));
                addressTxt.setText(rs.getString("Address"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        closeConnect(con);
    }

    public void callYes(ActionEvent actionEvent) {
        Connection con = getConnect();
        String Query = "UPDATE HMS set userName='" + userNameTxt.getText() + "',PassWord='"+passwordTxt.getText()
                +"',Role='"+roleTxt.getText()+"',Address='"+addressTxt.getText()+"',gender='"+genderTxt.getText()
                +"',mobileNumber='"+phnoTxt.getText()+"',workEmail='"+workEmailTxt.getText()
                +"',Designation='"+designationTxt.getText()+"' WHERE UserID='"+userIDTxt.getText()+"'";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(Query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        closeConnect(con);
        savePane.setVisible(false);
    }

    public void callNo(ActionEvent actionEvent) {
        savePane.setVisible(false);
    }

    public void callYesDelete(ActionEvent actionEvent) {
        Connection con = getConnect();
        String Query = "DELETE FROM HMS WHERE UserID='"+userIDTxt.getText()+"'";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(Query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        closeConnect(con);
        deletePane.setVisible(false);
    }

    public void callNoDelete(ActionEvent actionEvent) {
        deletePane.setVisible(false);
    }
}
