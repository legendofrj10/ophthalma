package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

public class databaseController {
    public Label databaseFoundTxt;
    public Label settingDatabaseTxt;
    public StackPane databaseStackpane;
    public TextField ipTxt;
    @FXML
    private Label errorLoginTxt;
    @FXML
    private TextField userIdTxt;

    @FXML
    private Button loginBTN;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    void callLogin() throws Exception {

        common.IP =ipTxt.getText();
        FileOutputStream ip = new FileOutputStream(".ospitality/ip");
        char[] ipS = common.getIP().toCharArray();
        int p=0;
        while(p<ipS.length){
            ip.write(ipS[p++]);
        }ip.close();

        String uname = userIdTxt.getText();
        char[] Uname = uname.toCharArray();
        String password = passwordTxt.getText();
        char[] Password = password.toCharArray();

        Connection con;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://"+common.getIP()+":3306", uname, password
            );
            errorLoginTxt.setText("");
            DatabaseMetaData dbm;
            dbm = con.getMetaData();
            ResultSet rst;
            rst = dbm.getCatalogs();


            try(FileOutputStream fos = new FileOutputStream(".ospitality/dbUName")) {
                int i=0;
                while (i< Uname.length){
                    fos.write(Uname[i]);
                    i++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try(FileOutputStream fos = new FileOutputStream(".ospitality/dbPass")) {
                int i=0;
                while (i<password.length()){
                    fos.write(Password[i]);
                    i++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            boolean databaseFound = false;


            while (rst.next()) {
                if(rst.getString(1).equals("OSPITALITY")){

                    databaseFound=true;
                    con.close();
                }

            }

            databaseStackpane.setVisible(true);
            if(databaseFound){
                databaseFoundTxt.setText("DATABASE ALREADY PRESENT.");
                settingDatabaseTxt.setText("GOOD TO GO.");

            }else {
                databaseFoundTxt.setText("DATABASE NOT FOUND!!");
                settingDatabaseTxt.setText("CREATING DATABASE....");
                con = DriverManager.getConnection(
                        "jdbc:mysql://"+common.getIP()+":3306", uname, password
                );
                con.createStatement().executeUpdate("CREATE DATABASE OSPITALITY");
                con.close();

                databaseFoundTxt.setText("DATABASE CREATED SUCCESSFULLY.");
                settingDatabaseTxt.setText("CREATING TABLES...");
                con = DriverManager.getConnection(
                        "jdbc:mysql://"+common.getIP()+":3306/OSPITALITY", uname, password
                );

                createScript();

                ScriptRunner sr = new ScriptRunner(con,false,false);
                sr.runScript(".ospitality/script.sql");
                con.createStatement().executeUpdate(String.format("INSERT INTO HMS VALUES ('ADMIN','ADM1001',1001,0,'aa','male','Admin','DBA','+01234567890','email@example.com','NULL','exmaple@gmial.com','%s')", common.getToday()));
                settingDatabaseTxt.setText("TABLES CREATED SUCCESSFULLY.\nBASIC ADMIN ACCOUNT CREATED.\n" +
                        "USER ID : ADM1001   PASSWORD : aa");
                con.close();

            }

        }catch (SQLException e){
            e.printStackTrace();
            errorLoginTxt.setText("PLEASE CHECK YOUR CREDENTIALS!!!");
        }catch (Exception e){
            e.printStackTrace();
        }

        loginController.login((Stage)loginBTN.getScene().getWindow());


    }


    private void createScript() throws IOException {
        String script = "create table HMS\n" +
                "(\n" +
                "    userName        varchar(25)  default 'NULL' not null,\n" +
                "    UserID          varchar(30)                 not null,\n" +
                "    NumericID       int                         not null,\n" +
                "    profileComplete tinyint(1)   default 0      not null,\n" +
                "    PassWord        varchar(45)  default 'aa'   null,\n" +
                "    gender          varchar(10)  default 'NULL' null,\n" +
                "    Role            varchar(30)  default 'NULL' not null,\n" +
                "    Designation     varchar(30)  default 'NULL' not null,\n" +
                "    mobileNumber    bigint                      null,\n" +
                "    workEmail       varchar(45)  default 'NULL' null,\n" +
                "    Address         varchar(255) default 'NULL' null,\n" +
                "    personalEmail   varchar(45)  default 'NULL' null,\n" +
                "    Joining         varchar(15)  default 'NULL' not null,\n" +
                "    constraint HMS_UserID_uindex\n" +
                "        unique (UserID)\n" +
                ");\n" +
                "\n" +
                "alter table HMS\n" +
                "    add primary key (UserID);\n" +
                "\n" +
                "create table doctors\n" +
                "(\n" +
                "    id         varchar(10) not null,\n" +
                "    department varchar(30) not null,\n" +
                "    constraint doctors_department_uindex\n" +
                "        unique (department),\n" +
                "    constraint doctors_id_uindex\n" +
                "        unique (id),\n" +
                "    constraint doctors_HMS_UserID_fk\n" +
                "        foreign key (id) references HMS (UserID)\n" +
                ");\n" +
                "\n" +
                "create table everydayDetails\n" +
                "(\n" +
                "    date              date          not null,\n" +
                "    totalAppointments int default 0 not null,\n" +
                "    newPatients       int default 0 not null,\n" +
                "    visitsToday       int default 0 not null,\n" +
                "    maleVisits        int default 0 not null,\n" +
                "    femaleVisits      int default 0 not null,\n" +
                "    constraint everydayDetails_pk\n" +
                "        unique (date)\n" +
                ");\n" +
                "\n" +
                "alter table everydayDetails\n" +
                "    add primary key (date);\n" +
                "\n" +
                "create table medicine\n" +
                "(\n" +
                "    ApplNo            text null,\n" +
                "    ProductNo         text null,\n" +
                "    Form              text null,\n" +
                "    Strength          text null,\n" +
                "    ReferenceDrug     text null,\n" +
                "    DrugName          text null,\n" +
                "    ActiveIngredient  text null,\n" +
                "    ReferenceStandard text null\n" +
                ");\n" +
                "\n" +
                "create table patients\n" +
                "(\n" +
                "    name           text                    not null,\n" +
                "    age            int                     not null,\n" +
                "    dob            text                    not null,\n" +
                "    gender         enum ('male', 'female') not null,\n" +
                "    last_diagnosed text                    null,\n" +
                "    patient_id     varchar(10)             not null,\n" +
                "    numeric_id     int                     null,\n" +
                "    remarks        longtext                null,\n" +
                "    constraint patients_patient_id_uindex\n" +
                "        unique (patient_id)\n" +
                ");\n" +
                "\n" +
                "alter table patients\n" +
                "    add primary key (patient_id);\n" +
                "\n" +
                "create table appointments\n" +
                "(\n" +
                "    date       date                    not null,\n" +
                "    patient    varchar(10)             not null,\n" +
                "    department text                    not null,\n" +
                "    gender     enum ('male', 'female') not null,\n" +
                "    constraint appointments_patients_patient_id_fk\n" +
                "        foreign key (patient) references patients (patient_id)\n" +
                ");\n" +
                "\n" +
                "create table visits\n" +
                "(\n" +
                "    Date              date          null,\n" +
                "    maleVisits        int default 0 null,\n" +
                "    femaleVisits      int default 0 null,\n" +
                "    patientAge1_10    int default 0 null,\n" +
                "    `patientAge11-20` int default 0 null,\n" +
                "    patientAge21_30   int default 0 null,\n" +
                "    patientAge31_40   int default 0 null,\n" +
                "    patientAge41_50   int default 0 null,\n" +
                "    patientAge51_60   int default 0 null,\n" +
                "    patientAge60Above int default 0 null,\n" +
                "    constraint visits_everydayDetails_date_fk\n" +
                "        foreign key (Date) references everydayDetails (date)\n" +
                ");\n" +
                "\n" +
                "\n";

        char[] scr = script.toCharArray();

        FileOutputStream scriptGen = new FileOutputStream(".ospitality/script.sql");
        int i=0;
        while(i<scr.length){
            scriptGen.write(scr[i]);
            i++;
        }
        scriptGen.close();

    }



}
