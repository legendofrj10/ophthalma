package sample.DOC;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class prescriptionController {

    @FXML
    public ListView selectedMedicineListView;

    @FXML
    public ScrollPane prescriptionPage;

    @FXML
    public StackPane pane;

    @FXML
    public StackPane prescripStack;

    @FXML
    private Button backBTN;

    @FXML
    private Label hospitalNameTxt;

    @FXML
    private Label doctorNameTxt;

    @FXML
    private Label departmentTxt;

    @FXML
    private Label phnoTxt;

    @FXML
    private Label patientNameTxt;

    @FXML
    private Label patientAgeTxt;

    @FXML
    private Label patientGenderTxt;

    @FXML
    private Label dateOfPrescriptionTxt;

    @FXML
    private ListView<?> medicineListView;

    @FXML
    private Button printBTN;

    @FXML
    private TextField searchMedicineTB;

    @FXML
    private Button searchMedicineBTN;

    @FXML
    void initialize(){
        doctorNameTxt.setText("Dr. "+sample.user.getUserName());
        phnoTxt.setText(sample.user.getPhno());
        departmentTxt.setText(sample.user.getDesignation());

        dateOfPrescriptionTxt.setText(String.valueOf(sample.common.getToday()));

        try {
            ResultSet rs = sample.common.getConnect().createStatement().executeQuery(String.format("SELECT * FROM patients WHERE patient_id like '%s'", patientController.patID));
            if(rs.next()){
                patientNameTxt.setText(rs.getString("name"));
                patientAgeTxt.setText(String.valueOf(rs.getInt("age")));
                patientGenderTxt.setText(rs.getString("gender"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    void callBack( ) throws IOException {
        Stage stage = (Stage) backBTN.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("patient.fxml")));
        Scene sc = stage.getScene();
        Scene scene = new Scene(root,sc.getWidth(),sc.getHeight());
        stage.setScene(scene);
    }

    @FXML
    void callPrint( ) {
        PrinterJob pj = PrinterJob.createPrinterJob();
        if(pj!=null){
            Printer printer = Printer.getDefaultPrinter();
            PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT,0,20,0,20);
            JobSettings jobSettings = pj.getJobSettings();
            jobSettings.setPageLayout(pageLayout);
            boolean printed = pj.printPage(prescripStack);
            if(printed){
                pj.endJob();
            }

        }

    }

    @FXML
    void callSearchMedicine( ) {

    }

    public void callPopulateList() {
        String nameMedicine = searchMedicineTB.getText();
        System.out.println(nameMedicine);
    }
}
