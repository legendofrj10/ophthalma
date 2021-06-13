package sample.ADM;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import sample.common;
import sample.completeProfileController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static javafx.geometry.Insets.EMPTY;

public class accountsManagementController {

    public StackPane mainpane;
    public TabPane tabPane;
    public StackPane navbarStackpane;
    @FXML
    private Button accountsManagementBtn;

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
    private BorderPane innerStage;

    @FXML
    void callAccountsManagement() {

    }




    @FXML
    void callChatBubble() throws IOException {
        Popup popup = new Popup();

        popup.getContent().add(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample/chatBubble.fxml"))));

        popup.show(notificationBTN.getScene().getWindow(),
                mainpane.getWidth()-20,
                mainpane.getHeight()-20 );

    }    @FXML
    void callDashboard() throws IOException {
        Stage stage = (Stage) dashboardBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dashboard.fxml")));
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


    public Label userAddedTxt;
    public ComboBox departmentCB;
    String Query;

    @FXML
    private ComboBox<?> jobChoice;

    @FXML
    private Label passnotmatch;

    @FXML
    private TextField Name,email,two_pass,two_passconf;


    int newUserID(){
        int ID=1000;
        try{
            Connection con1 = sample.common.getConnect();
            Statement stmt = con1.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NumericID FROM HMS");
            while(rs.next()){
                if(rs.getInt("NumericID")>ID) ID=rs.getInt("NumericID");
            }
            con1.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        ID++;
        return ID;
    }

    @FXML
    public void submitSignUp() {
        String userName = Name.getText();
        String job = (String) jobChoice.getValue();
        String emailAddress = email.getText();
        String pass = two_pass.getText();
        String confPass = two_passconf.getText();
        int uID = newUserID();//==0?1001:newUserID()+1;
        String fullUID;
        if (job.equals("Doctor")) {
            fullUID = "DOC"+uID;
        } else {
            if (job.equals("Admin")) {
                fullUID = ("ADM"+uID);
            } else {
                if (job.equals("Receptionist")) fullUID = "RCP" + uID;
                else {
                    if (job.equals("Lab Technician")) fullUID = "LBT" + uID;
                    else fullUID = "MDC" + uID;
                }
            }
        }
        System.out.println(userName +" "+ completeProfileController.IDLoggedIn);
        if(pass.equals(confPass)){
            passnotmatch.setText("");
            try{
                Connection con = sample.common.getConnect();
                Statement st = con.createStatement();
                Query = String.format("INSERT INTO HMS (userName,UserID,NumericID,Role,personalEmail,PassWord,Joining) VALUES ('%s','%s','%d','%s','%s','%s','%s')", userName, fullUID, uID, job, emailAddress, pass, LocalDate.now());
                if(job.equals("Doctor")) Query = String.format("INSERT INTO HMS (userName,UserID,NumericID,Role,personalEmail,PassWord,Joining,Designation) VALUES ('%s','%s','%d','%s','%s','%s','%s','%s')", userName, fullUID, uID, job, emailAddress, pass, LocalDate.now(), departmentCB.getValue());
                st.executeUpdate(Query);
                {
                    String userAddedGreet = String.format("User Account for %s has been created.\nUSER ID : %s\nPASSWORD : %s", userName, fullUID, confPass);
                    userAddedTxt.setText(userAddedGreet);
                    Name.setText("");
                    email.setText("");
                    two_pass.setText("");
                    two_passconf.setText("");
                }
                con.close();
            }catch(Exception e){
                e.printStackTrace();
            }

        }else{
            passnotmatch.setText("passwords don't match!!!");
        }


    }

    public void callCheckRole() {
        String job= (String) jobChoice.getValue();
        departmentCB.setVisible(job.equals("Doctor"));
    }


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
    public Button editBtn;
    public Button saveBtn;
    public ComboBox designationCB;
    public ComboBox<String> genderCB;
    public ComboBox<String> roleCB;
    public GridPane gridPane;
    public ScrollPane scrollPane;
    public TilePane tilePane;
    public StackPane stackPane;
    public StackPane mainPane;
    Image image;
    @FXML
    private TextField userIDTxt;

    @FXML
    Background background = new Background(new BackgroundFill(new Color(0.16,0.16,0.16,1), CornerRadii.EMPTY, EMPTY));

    @FXML
    private Button searchUserBtn;

    @FXML
    private Button deleteUserBtn;

    @FXML
    public void initialize() throws IOException {
        tabPane.widthProperty().addListener((observable, oldValue, newValue) ->
        {
            tabPane.setTabMinWidth((tabPane.getWidth()-80) / tabPane.getTabs().size());
            tabPane.setTabMaxWidth((tabPane.getWidth()-80) / tabPane.getTabs().size());
        });
        //int wid = (int) ;
        //System.out.println(searchUserBtn.getScene().getWidth());
        tilePane.setHgap(15);
        tilePane.setVgap(15);
        int pref = (int) ((mainPane.getWidth()-(mainPane.getWidth()/15))/150  );
        tilePane.setPrefColumns(pref);
        System.out.println(pref);
        System.out.println(mainPane.getWidth());

        Connection con = common.getConnect();

        AtomicInteger finalR = new AtomicInteger();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT userName,UserID,Role FROM HMS");

            while(rs.next()){


                Label name = new Label(rs.getString("userName"));
                Label userID = new Label(rs.getString("UserID"));
                Label role = new Label(rs.getString("Role"));

                name.setStyle("-fx-text-fill: white");
                userID.setStyle("-fx-text-fill: white");
                userID.setUnderline(true);
                role.setStyle("-fx-text-fill: #c5c5c5");


                StackPane pane = new StackPane(name,userID,role);
                pane.setBackground(background);
                pane.setStyle("-fx-background-color: #3d3d3d");
                pane.setStyle("-fx-background-radius: 50px");
                pane.setStyle("-fx-border-radius: 50px");
                pane.setStyle("-fx-min-height: 100px");
                pane.setStyle("-fx-min-width: 150px");
                name.setPadding(new Insets(20,0,0,0));
                userID.setPadding(new Insets(20,0,0,0));
                role.setPadding(new Insets(0,0,10,0));
                StackPane.setAlignment(name, Pos.TOP_CENTER);
                StackPane.setAlignment(userID,Pos.CENTER);
                StackPane.setAlignment(role,Pos.BOTTOM_CENTER);
                Effect effect = new DropShadow(BlurType.THREE_PASS_BOX,Color.BLACK,1,1,1,0);
                pane.setEffect(effect);

                pane.setOnMouseClicked(mouseEvent -> {
                    callSearchUser(userID.getText());
                });

                pane.setOnMouseEntered(mouseEvent -> {
                    pane.setStyle("-fx-background-color: #181818");
                });

                pane.setOnMouseExited(mouseEvent -> {
                    pane.setStyle("-fx-background-color: #292929");
                });


                tilePane.getChildren().add(pane);


            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
        int finalR1 = finalR.get();

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
            userNameTxt.setEditable(true);
            passwordTxt.setEditable(true);
            phnoTxt.setEditable(true);
            workEmailTxt.setEditable(true);
            genderCB.setDisable(false);
            roleCB.setDisable(false);

            image = new Image("file:src/sample/Assets/unlockDark.png");

        }else{
            addressTxt.setEditable(false);
            designationTxt.setEditable(false);
            userNameTxt.setEditable(false);
            passwordTxt.setEditable(false);
            phnoTxt.setEditable(false);
            workEmailTxt.setEditable(false);
            genderCB.setDisable(true);
            roleCB.setDisable(true);

            image = new Image("file:src/sample/Assets/lockDark.png");
        }
        lockImg.setImage(image);


    }

    @FXML
    void callSave( ) {
        if(deletePane.isVisible())  deletePane.setVisible(false);
        savePane.setVisible(true);
    }

    @FXML
    void callSearchUser( ) {
        String user = userIDTxt.getText();
        Connection con = common.getConnect();
        try {
            Statement st = con.createStatement();
            String Query = "SELECT * FROM HMS WHERE UserID = '"+user+"'";
            ResultSet rs = st.executeQuery(Query);
            if(rs.next()){
                scrollPane.setVisible(false);
                detailsPane.setVisible(true);
                userNameTxt.setText(rs.getString("userName"));
                passwordTxt.setText(rs.getString("PassWord"));
                roleCB.getSelectionModel().select(rs.getString("Role"));
                roleCB.setStyle("-fx-backgrouond-color: transparent");
                designationTxt.setText(rs.getString("Designation"));
                genderCB.getSelectionModel().select(rs.getString("gender"));
                workEmailTxt.setText(rs.getString("workEmail"));
                phnoTxt.setText(rs.getString("mobileNumber"));
                addressTxt.setText(rs.getString("Address"));

            }
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    void callSearchUser(String userId) {
        String user = userId;
        Connection con = common.getConnect();
        try {
            Statement st = con.createStatement();
            String Query = "SELECT * FROM HMS WHERE UserID = '"+user+"'";
            ResultSet rs = st.executeQuery(Query);
            if(rs.next()){
                scrollPane.setVisible(false);
                detailsPane.setVisible(true);
                userNameTxt.setText(rs.getString("userName"));
                passwordTxt.setText(rs.getString("PassWord"));
                roleCB.getSelectionModel().select(rs.getString("Role"));
                roleCB.setStyle("-fx-backgrouond-color: transparent");
                designationTxt.setText(rs.getString("Designation"));
                genderCB.getSelectionModel().select(rs.getString("gender"));
                workEmailTxt.setText(rs.getString("workEmail"));
                phnoTxt.setText(rs.getString("mobileNumber"));
                addressTxt.setText(rs.getString("Address"));
            }
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void callYes() {
        Connection con = common.getConnect();
        String Query = "UPDATE HMS set userName='" + userNameTxt.getText() + "',PassWord='"+passwordTxt.getText()
                +"',Role='"+roleCB.getValue()+"',Address='"+addressTxt.getText()+"',gender='"+genderCB.getValue()
                +"',mobileNumber='"+phnoTxt.getText()+"',workEmail='"+workEmailTxt.getText()
                +"',Designation='"+designationTxt.getText()+"' WHERE UserID='"+userIDTxt.getText()+"'";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(Query);
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        savePane.setVisible(false);
    }

    public void callNo() {
        savePane.setVisible(false);
    }

    public void callYesDelete() {
        Connection con = sample.common.getConnect();
        String Query = "DELETE FROM HMS WHERE UserID='"+userIDTxt.getText()+"'";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(Query);
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        deletePane.setVisible(false);
    }

    public void callNoDelete() {
        deletePane.setVisible(false);
    }

    public void callTileClicked(MouseEvent mouseEvent) {
        //Node node  = tilePane.getChildren().get();
        System.out.println(tilePane.getChildren().size());
    }

    public void closeModify(ActionEvent actionEvent) throws IOException {
        detailsPane.setVisible(false);
        scrollPane.setVisible(true);
    }

}
