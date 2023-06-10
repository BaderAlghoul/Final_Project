/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller.Admin;

import Model.User;
import View.ViewManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Bader M
 */
public class PageControlPatientsController implements Initializable {

    @FXML
    private Label textFiled;
    @FXML
    private Button PatientManagmentPageBtn;
    @FXML
    private Button appointmentPageBtn;
    @FXML
    private Button logOutBtn;
    @FXML
    private Button patientAppointmentBtn;
    @FXML
    private TableView<?> tableView;
    @FXML
    private TableColumn<?, ?> id;
    @FXML
    private TableColumn<?, ?> Date;
    @FXML
    private TableColumn<?, ?> Day;
    @FXML
    private TableColumn<?, ?> Time;
    @FXML
    private TableColumn<?, ?> Status;
    @FXML
    private TableColumn<?, ?> StatusFinished;
    @FXML
    private TableColumn<?, ?> commitDoctor;
    @FXML
    private Button btnShowAllAppointments;
    @FXML
    private Button createNewAppointmentsButton;
    
 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnShowPatientManagmentPage(ActionEvent event) {
            Alert warnAlert = new Alert(Alert.AlertType.WARNING);
            warnAlert.setTitle("Doctor");
            warnAlert.setContentText("يمنع وصول المريض لهذه الصفحة");
            warnAlert.show(); 

    }

    @FXML
    private void btnShowAppointmentPage(ActionEvent event) {
            Alert warnAlert = new Alert(Alert.AlertType.WARNING);
            warnAlert.setTitle("Doctor");
            warnAlert.setContentText("يمنع وصول المريض لهذه الصفحة");
            warnAlert.show(); 
     
        
    }

    @FXML
    private void btnLogOut(ActionEvent event) {
        ViewManager.adminPage.changeSceneToSingInPatient();
    }

    @FXML
    private void btnShowPatientAppointment(ActionEvent event) {
    }

    @FXML
    private void btnShowAllAppointments(ActionEvent event) {
    }

    @FXML
    private void btnCreateNewAppointments(ActionEvent event) {
        ViewManager.adminPage.changeSceneToCreateNewAppointment();
    }
    
}
