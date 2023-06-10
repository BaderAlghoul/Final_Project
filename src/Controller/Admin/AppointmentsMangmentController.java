/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller.Admin;

import Model.Appointments;
import Model.AppointmentsJpaController;
import Model.BookedAppointments;
import Model.BookedAppointmentsJpaController;
import Model.User;
import Model.UserJpaController;
import Model.exceptions.IllegalOrphanException;
import Model.exceptions.NonexistentEntityException;
import View.ViewManager;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author Bader M
 */
public class AppointmentsMangmentController implements Initializable {

    @FXML
    private Button PatientManagmentPageBtn;
    @FXML
    private Button appointmentPageBtn;
    @FXML
    private Button logOutBtn;
    @FXML
    private Button patientAppointmentBtn;
    @FXML
    private TableView<Appointments> tableViewAppo;
    @FXML
    private TableColumn<Appointments, Integer> id;
    @FXML
    private TableColumn<Appointments, Date> Date;
    @FXML
    private TableColumn<Appointments, String> Day;
    @FXML
    private TableColumn<Appointments, Time> Time;
    @FXML
    private TableColumn<Appointments, String> Status;
    @FXML
    private Button searchDay;
    @FXML
    private TextField DaySearchTF;
    @FXML
    private Button btnShowAllAppointments;
    @FXML
    private Button UpdateAppointmentsButton;
    @FXML
    private Button deleteAppointmentsButton;
    @FXML
    private Button createNewAppointmentsButton;
    @FXML
    private Button finishAppointmentsButton;
    @FXML
    private TextField appointmentDayTF;
    @FXML
    private TextField miunteTimeTF;
    @FXML
    private TextField hourTimeTF;
    @FXML
    private DatePicker appointmentDatePicker;
    @FXML
    private RadioButton radoiFree;
    @FXML
    private ToggleGroup statusGroup;
    @FXML
    private RadioButton radoiBooked;
    @FXML
    private TextField textFieldAddCommit;
    @FXML
    private RadioButton radoiFinish;
    @FXML
    private ToggleGroup finishGroup;
    @FXML
    private RadioButton radoiWating;
    @FXML
    private Button addCommitButton;
    
    private EntityManagerFactory emf ;
    private LocalDate myDate ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        id.setCellValueFactory(new PropertyValueFactory("id"));
        Date.setCellValueFactory(new PropertyValueFactory("date"));
        Day.setCellValueFactory(new PropertyValueFactory("day"));
        Time.setCellValueFactory(new PropertyValueFactory("time"));
        Status.setCellValueFactory(new PropertyValueFactory("appoStatus"));
        
        tableViewAppo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            appointmentDatePicker.setValue(newValue.getDate());
            appointmentDayTF.setText(newValue.getDay());
            hourTimeTF.setText(String.valueOf(newValue.getTime().getHour()));
            miunteTimeTF.setText(String.valueOf(newValue.getTime().getMinute()));
            if(newValue.getAppoStatus().equals("free")){
                radoiFree.setSelected(true);
                radoiBooked.setSelected(false);
            }else if (newValue.getAppoStatus().equals("booked")){
                radoiBooked.setSelected(true);
                radoiFree.setSelected(false);
            }
    }
    });

    }    
    
    @FXML
    void datePicker(ActionEvent event) {
        myDate = appointmentDatePicker.getValue();
        appointmentDayTF.setText(myDate.getDayOfWeek().toString());

    }

    @FXML
    private void btnShowPatientManagmentPage(ActionEvent event) {
        ViewManager.adminPage.changeSceneToPatientMang();
    }

    @FXML
    private void btnShowAppointmentPage(ActionEvent event) {
    }

    @FXML
    private void btnLogOut(ActionEvent event) {
        ViewManager.adminPage.changeSceneToSingInPatient();
    }

    @FXML
    private void btnShowPatientAppointment(ActionEvent event) {
            Alert warnAlert = new Alert(Alert.AlertType.WARNING);
            warnAlert.setTitle("Doctor");
            warnAlert.setContentText("صفحة المريض");
            warnAlert.show(); 
    }

    @FXML
    private void btnSearchDay(ActionEvent event) {
        emf = Persistence.createEntityManagerFactory("ClinicAppointmentPU");
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Appointments.findByDay").setParameter("day", DaySearchTF.getText());
        List<Appointments> appointments = query.getResultList();
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        appointmentsList.addAll(appointments);
        tableViewAppo.setItems(appointmentsList);
        DaySearchTF.clear();
    }

    @FXML
    private void btnShowAllAppointments(ActionEvent event) {
        emf= Persistence.createEntityManagerFactory("ClinicAppointmentPU");
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Appointments.findAll");
        List<Appointments> appointments = query.getResultList();
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        appointmentsList.addAll(appointments);
        tableViewAppo.setItems(appointmentsList);

    }

    @FXML
    private void btnUpdateAppointments(ActionEvent event) throws NonexistentEntityException, Exception  {
        if(tableViewAppo.getSelectionModel().getSelectedItem() != null){
            String day = appointmentDayTF.getText();
            String strHour = hourTimeTF.getText();
            String strMiunte = miunteTimeTF.getText();
            int hour = Integer.parseInt(strHour);
            int miunte = Integer.parseInt(strMiunte);
            LocalTime time = LocalTime.of(hour, miunte);
            String appoStatus = ((RadioButton) statusGroup.getSelectedToggle()).getText();
            
            emf = Persistence.createEntityManagerFactory("ClinicAppointmentPU");
            AppointmentsJpaController appointmentsJpaUpDate = new AppointmentsJpaController(emf);
            Appointments a = tableViewAppo.getSelectionModel().getSelectedItem();
            a.setDay(day);
            a.setDate(myDate);
            a.setTime(time);
            a.setAppoStatus(appoStatus);
            appointmentsJpaUpDate.edit(a);
            Alert warnAlert = new Alert(Alert.AlertType.WARNING);
            warnAlert.setTitle("Update Appointments");
            warnAlert.setContentText("تم تعديل الموعد");
            warnAlert.show(); 
        }else{
                Alert warnAlert = new Alert(Alert.AlertType.WARNING);
                warnAlert.setTitle("Select an Appointments");
                warnAlert.setContentText("الرجاء حدد الموعد المراد تعديله");
                warnAlert.show();  
        }
    }

    @FXML
    private void btnDeleteAppointments(ActionEvent event) throws IllegalOrphanException, NonexistentEntityException {
        if(tableViewAppo.getSelectionModel().getSelectedItem() != null){
            Appointments a = tableViewAppo.getSelectionModel().getSelectedItem();
            emf= Persistence.createEntityManagerFactory("ClinicAppointmentPU");
            AppointmentsJpaController appointmentsJpaDelete = new AppointmentsJpaController(emf);
            appointmentsJpaDelete.destroy(a.getId());
                Alert warnAlert = new Alert(Alert.AlertType.WARNING);
                warnAlert.setTitle("Delete Appointments");
                warnAlert.setContentText("تم حذف الموعد");
                warnAlert.show(); 

        }else{
            Alert warnAlert = new Alert(Alert.AlertType.WARNING);
            warnAlert.setTitle("Select an Appointments");
            warnAlert.setContentText("الرجاء حدد الموعد المراد خذفه");
            warnAlert.show();  
        }
    }

    @FXML
    private void btnCreateNewAppointments(ActionEvent event) {
        ViewManager.adminPage.changeSceneToCreateNewAppointment();
    }

    @FXML
    private void btnAddCommit(ActionEvent event) {
        if(!textFieldAddCommit.getText().isEmpty()){
            Appointments a = tableViewAppo.getSelectionModel().getSelectedItem();
            String commit = textFieldAddCommit.getText();
            String finish = ((RadioButton) finishGroup.getSelectedToggle()).getText();
            
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ClinicAppointmentPU");
            BookedAppointmentsJpaController appointmentsBookedJpaController = new BookedAppointmentsJpaController(emf);
            BookedAppointments ba = new BookedAppointments( finish, commit , a );
            appointmentsBookedJpaController.create(ba);
            
        }
    }
    
}
