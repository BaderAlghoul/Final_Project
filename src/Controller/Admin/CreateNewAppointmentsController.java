/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller.Admin;

import Model.Appointments;
import Model.AppointmentsJpaController;
import Model.User;
import Model.UserJpaController;
import View.ViewManager;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author Bader M
 */
public class CreateNewAppointmentsController implements Initializable {


    @FXML
    private DatePicker appointmentDatePicker;
    @FXML
    private TextField appointmentDayTF;
    @FXML
    private RadioButton radoiFree;
    @FXML
    private TextField hourTimeTF;
    @FXML
    private TextField minuteTimeTF;
    @FXML
    private ToggleGroup statusGroup;
    @FXML
    private RadioButton radoiBooked;
    @FXML
    private HBox appointmentCancelButton;
    @FXML
    private Button appointmentSaveButton;
    @FXML
    private Button cancelPatinetBtn;

    private LocalDate myDate ;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    void datePicker(ActionEvent event) {
        
        myDate = appointmentDatePicker.getValue();
        appointmentDayTF.setText(myDate.getDayOfWeek().toString());
    }
    
    @FXML
    private void btnAppointmentSave(ActionEvent event) throws ParseException {
        
        String strHour = hourTimeTF.getText();
        String strMinute = minuteTimeTF.getText();
        int hour = Integer.parseInt(strHour);
        int minute = Integer.parseInt(strMinute);
        LocalTime currentTime = LocalTime.of(hour , minute);
        DayOfWeek selectedDayOfWeek = appointmentDatePicker.getValue().getDayOfWeek();
        String dayString = selectedDayOfWeek.toString();
        String appoStatus = ((RadioButton)statusGroup.getSelectedToggle()).getText();

                EntityManagerFactory emf = Persistence.createEntityManagerFactory("ClinicAppointmentPU");
                AppointmentsJpaController appointmentsJpaController = new AppointmentsJpaController(emf);
                Appointments appointments = new Appointments(null , myDate , dayString , currentTime , appoStatus );
                appointmentsJpaController.create(appointments);
                ViewManager.adminPage.changeSceneToAppointmentMang();
          
    }
    @FXML
    void btnAppointmentCancel(ActionEvent event) {
        appointmentDayTF.clear();
        hourTimeTF.clear();
        minuteTimeTF.clear();
        appointmentDatePicker.setValue(null);
        ViewManager.adminPage.changeSceneToAppointmentMang();
    }

}
