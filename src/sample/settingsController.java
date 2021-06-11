package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class settingsController {
    @FXML
    public StackPane mainpane;
    @FXML
    public TabPane tabPane;
    @FXML
    public Label name;
    @FXML
    public Label userId;
    @FXML
    public Label gender;
    @FXML
    public Label role;
    @FXML
    public Label designation;
    @FXML
    public Label phno;
    @FXML
    public Label emailWork;
    @FXML
    public Label emailPers;
    @FXML
    public Label joiningDate;
    @FXML
    public Label addr;
    @FXML
    public Button resetBtn;
    @FXML
    public PasswordField oldPassword;
    @FXML
    public PasswordField newPassword;
    @FXML
    public PasswordField confNewPass;
    public Label wrongOldPass;
    public Label newPassErr;
    public StackPane navbarStackpane;
    @FXML
    private Button dashboardBTN;
    @FXML
    private Button logOutBTN;
    @FXML
    private Button settingsBTN;
    @FXML
    private Button notificationBTN;
    @FXML
    private Button chatBubbleBTN;

    @FXML
    private Button accountsManagementBTN;


    @FXML
    public void initialize(){
        tabPane.widthProperty().addListener((observable, oldValue, newValue) ->
        {
            tabPane.setTabMinWidth((tabPane.getWidth()-80) / tabPane.getTabs().size());
            tabPane.setTabMaxWidth((tabPane.getWidth()-80) / tabPane.getTabs().size());
        });

        name.setText(name.getText()+user.getUserName());
        userId.setText(userId.getText()+user.getUserID());
        gender.setText(gender.getText()+user.getGender());
        role.setText(role.getText()+user.getRole());
        phno.setText(phno.getText()+user.getPhno());
        emailWork.setText(emailWork.getText()+user.getWorkEmail());
        emailPers.setText(emailPers.getText()+user.getPersonalEmail());
        addr.setText(addr.getText()+user.getAddress());
        joiningDate.setText(joiningDate.getText()+user.getJoiningDate());
        designation.setText(designation.getText()+user.getDesignation());

    }


    @FXML
    void callChatBubble() throws IOException {
        Popup popup = new Popup();

        popup.getContent().add(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/chatBubble.fxml"))));

        popup.show(notificationBTN.getScene().getWindow(),
                mainpane.getWidth()-20,
                mainpane.getHeight()-20 );

    }
    @FXML
    void callDashboard() throws IOException {
        Stage stage = (Stage) dashboardBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(user.userID.substring(0, 3) + "/dashboard.fxml")));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callAccountsManagement() throws IOException {
        Stage stage = (Stage) accountsManagementBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("accountsManagement.fxml")));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callLogOut() throws IOException {


        Popup popup = new Popup();



        popup.getContent().add(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/logout.fxml"))));

        popup.show(logOutBTN.getScene().getWindow());

        BoxBlur blur = new BoxBlur(3,3,3);

        mainpane.setEffect(blur);


    }

    @FXML
    void callNotifications() throws IOException {
        Popup popup = new Popup();

        popup.getContent().add(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/notifications.fxml"))));

        popup.show(notificationBTN.getScene().getWindow(),
                mainpane.getWidth()-20,
                100 );


    }

    @FXML
    void callSettings() throws IOException {
        Stage stage = (Stage) settingsBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/settings.fxml")));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    public void callTryReset() {
        wrongOldPass.setText("");
        newPassErr.setText("");
        Connection con = common.getConnect();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT PassWord FROM HMS WHERE UserID like '"+ user.getUserID()+"'");
            if(rs.next()){
                String pass = rs.getString("PassWord");
                if(pass.equals(oldPassword.getText())){
                    wrongOldPass.setText("");
                    if(newPassword.getText().length()!=0 && confNewPass.getText().length()!=0){
                        newPassErr.setText("");
                        if(newPassword.getText().equals(confNewPass.getText())){
                            newPassErr.setText("");
                            st.executeUpdate("UPDATE HMS SET PassWord = '"+newPassword.getText()+"' WHERE UserID LIKE '"+user.getUserID()+"'");
                            con.close();
                        }else{
                            newPassErr.setText("Password Do Not Match!!");
                        }
                    }else{
                        newPassErr.setText("Some Of The Fields Seems Empty!!");
                    }
                }else{
                    if(oldPassword.getText().length()==0){
                        wrongOldPass.setText("Enter Old Password!!!");
                    }
                    else{
                        wrongOldPass.setText("Wrong Old PassWord!!!");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        oldPassword.setText("");
        newPassword.setText("");
        confNewPass.setText("");
    }

}
