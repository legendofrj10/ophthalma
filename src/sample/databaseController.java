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

        createScript();


        common.IP = ipTxt.getText();
        FileOutputStream ip = new FileOutputStream(common.getWorkingDirectory() + "ip");
        char[] ipS = common.getIP().toCharArray();
        int p = 0;
        while (p < ipS.length) {
            ip.write(ipS[p++]);
        }
        ip.close();

        String uname = userIdTxt.getText();
        char[] Uname = uname.toCharArray();
        String password = passwordTxt.getText();
        char[] Password = password.toCharArray();

        Connection con;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://" + common.getIP() + ":3306", uname, password
            );
            errorLoginTxt.setText("");
            DatabaseMetaData dbm;
            dbm = con.getMetaData();

            if (dbm != null) {
                ResultSet rst;
                rst = dbm.getCatalogs();


                try (FileOutputStream fos = new FileOutputStream(common.getWorkingDirectory() + "dbUName")) {
                    int i = 0;
                    while (i < Uname.length) {
                        fos.write(Uname[i]);
                        i++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try (FileOutputStream fos = new FileOutputStream(common.getWorkingDirectory() + "dbPass")) {
                    int i = 0;
                    while (i < password.length()) {
                        fos.write(Password[i]);
                        i++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                boolean databaseFound = false;


                while (rst.next()) {
                    if (rst.getString(1).equals("OSPITALITY")) {

                        databaseFound = true;
                        con.close();
                    }

                }

                databaseStackpane.setVisible(true);
                if (databaseFound) {
                    databaseFoundTxt.setText("DATABASE ALREADY PRESENT.");
                    settingDatabaseTxt.setText("GOOD TO GO.");

                } else {
                    databaseFoundTxt.setText("DATABASE NOT FOUND!!");
                    settingDatabaseTxt.setText("CREATING DATABASE....");
                    con = DriverManager.getConnection(
                            "jdbc:mysql://" + common.getIP() + ":3306", uname, password
                    );

                    ScriptRunner sr = new ScriptRunner(con, false, false);
                    sr.runScript(common.getWorkingDirectory() + ".ospitality/script.sql");
                    con.createStatement().executeUpdate(String.format("INSERT INTO HMS VALUES ('ADMIN','ADM1001',1001,0,'aa','male','Admin','DBA','+01234567890','email@example.com','NULL','exmaple@gmial.com','%s')", common.getToday()));
                    databaseFoundTxt.setText("DATABASE CREATED SUCCESSFULLY.");
                    settingDatabaseTxt.setText("TABLES CREATED SUCCESSFULLY.\nBASIC ADMIN ACCOUNT CREATED.\n" +
                            "USER ID : ADM1001   PASSWORD : aa");
                    con.close();
                }
                loginController.init((Stage) loginBTN.getScene().getWindow());

            } else {
                errorLoginTxt.setText("PLEASE CHECK YOUR CREDENTIALS!!!");
            }
        } catch(SQLException e) {
            errorLoginTxt.setText("PLEASE CHECK YOUR CREDENTIALS!!!");
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void createScript() throws IOException {
        String script = "create database OSPITALITY;\n"+
                "use OSPITALITY;\n"+
                "create table HMS\n" +
                "(\n" +
                "\tuserName varchar(25) default 'NULL' not null,\n" +
                "\tUserID varchar(30) not null,\n" +
                "\tNumericID int not null,\n" +
                "\tprofileComplete tinyint(1) default 0 not null,\n" +
                "\tPassWord varchar(45) default 'aa' null,\n" +
                "\tgender varchar(10) default 'NULL' null,\n" +
                "\tRole varchar(30) default 'NULL' not null,\n" +
                "\tDesignation varchar(30) default 'NULL' not null,\n" +
                "\tmobileNumber bigint null,\n" +
                "\tworkEmail varchar(45) default 'NULL' null,\n" +
                "\tAddress varchar(255) default 'NULL' null,\n" +
                "\tpersonalEmail varchar(45) default 'NULL' null,\n" +
                "\tJoining varchar(15) default 'NULL' not null,\n" +
                "\tconstraint HMS_UserID_uindex\n" +
                "\t\tunique (UserID)\n" +
                ");\n" +
                "\n" +
                "alter table HMS\n" +
                "\tadd primary key (UserID);\n" +
                "\n" +
                "create table doctors\n" +
                "(\n" +
                "\tid varchar(10) not null,\n" +
                "\tdepartment varchar(30) not null,\n" +
                "\tconstraint doctors_department_uindex\n" +
                "\t\tunique (department),\n" +
                "\tconstraint doctors_id_uindex\n" +
                "\t\tunique (id),\n" +
                "\tconstraint doctors_HMS_UserID_fk\n" +
                "\t\tforeign key (id) references HMS (UserID)\n" +
                ");\n" +
                "\n" +
                "create table everydayDetails\n" +
                "(\n" +
                "\tdate date not null,\n" +
                "\ttotalAppointments int default 0 not null,\n" +
                "\tnewPatients int default 0 not null,\n" +
                "\tvisitsToday int default 0 not null,\n" +
                "\tconstraint everydayDetails_pk\n" +
                "\t\tunique (date)\n" +
                ");\n" +
                "\n" +
                "alter table everydayDetails\n" +
                "\tadd primary key (date);\n" +
                "\n" +
                "create table labtests\n" +
                "(\n" +
                "\tassignmentDate date not null,\n" +
                "\tpatient varchar(10) not null,\n" +
                "\ttestName varchar(30) not null,\n" +
                "\ttestDoneOn date null\n" +
                ");\n" +
                "\n" +
                "create table medicine\n" +
                "(\n" +
                "\tApplNo text null,\n" +
                "\tProductNo text null,\n" +
                "\tForm text null,\n" +
                "\tStrength text null,\n" +
                "\tReferenceDrug text null,\n" +
                "\tDrugName text null,\n" +
                "\tActiveIngredient text null,\n" +
                "\tReferenceStandard text null\n" +
                ");\n" +
                "\n" +
                "create table passwordRequests\n" +
                "(\n" +
                "\tuserID varchar(10) null,\n" +
                "\tconstraint passwordRequests_HMS_UserID_fk\n" +
                "\t\tforeign key (userID) references HMS (UserID)\n" +
                ");\n" +
                "\n" +
                "create table patients\n" +
                "(\n" +
                "\tname text not null,\n" +
                "\tage int not null,\n" +
                "\tdob text not null,\n" +
                "\tgender enum('male', 'female') not null,\n" +
                "\tlast_diagnosed text null,\n" +
                "\tpatient_id varchar(10) not null,\n" +
                "\tnumeric_id int null,\n" +
                "\tremarks longtext null,\n" +
                "\tconstraint patients_patient_id_uindex\n" +
                "\t\tunique (patient_id)\n" +
                ");\n" +
                "\n" +
                "alter table patients\n" +
                "\tadd primary key (patient_id);\n" +
                "\n" +
                "create table appointments\n" +
                "(\n" +
                "\tdate date not null,\n" +
                "\tpatient varchar(10) not null,\n" +
                "\tdepartment text not null,\n" +
                "\tgender enum('male', 'female') not null,\n" +
                "\tconstraint appointments_patients_patient_id_fk\n" +
                "\t\tforeign key (patient) references patients (patient_id)\n" +
                ");\n" +
                "\n" +
                "create table visited\n" +
                "(\n" +
                "\tdate date not null,\n" +
                "\tpatient varchar(10) not null,\n" +
                "\tdepartment varchar(30) not null,\n" +
                "\tdoctor varchar(20) not null\n" +
                ");\n" +
                "\n" +
                "create table visits\n" +
                "(\n" +
                "\tDate date null,\n" +
                "\tmaleVisits int default 0 null,\n" +
                "\tfemaleVisits int default 0 null,\n" +
                "\tpatientAge1_10 int default 0 null,\n" +
                "\t`patientAge11-20` int default 0 null,\n" +
                "\tpatientAge21_30 int default 0 null,\n" +
                "\tpatientAge31_40 int default 0 null,\n" +
                "\tpatientAge41_50 int default 0 null,\n" +
                "\tpatientAge51_60 int default 0 null,\n" +
                "\tpatientAge60Above int default 0 null,\n" +
                "\tconstraint visits_everydayDetails_date_fk\n" +
                "\t\tforeign key (Date) references everydayDetails (date)\n" +
                ");\n" +
                "\n";

        char[] scr = script.toCharArray();

        FileOutputStream scriptGen = new FileOutputStream(common.getWorkingDirectory() + "script.sql");
        int i = 0;
        while (i < scr.length) {
            scriptGen.write(scr[i]);
            i++;
        }
        scriptGen.close();

    }
}