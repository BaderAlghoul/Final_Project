/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Bader M
 */
public class AdminDashboard extends Stage{
    
    private Scene patientMangScene ; //                 6
    private Scene appointmentMangScene ; //             1
    private Scene createNewAppointmentScene ; //        2
    private Scene createNewUsreScene ; //               3
    private Scene newUserPatientScene ; //              4
    private Scene pageControlPatientScene ; //          5
    private Scene singInAdminScene ; //                 7
    private Scene singInPatientScene ; //                 8

    
    public AdminDashboard() throws IOException{
        
        FXMLLoader patientMangLoader = new FXMLLoader(getClass().getResource("/View/AdminFXML/PatientMangment.fxml"));
        Parent patientMangRoot = patientMangLoader.load();
        patientMangScene = new Scene(patientMangRoot);
        
        FXMLLoader appointmentMangLoader = new FXMLLoader(getClass().getResource("/View/AdminFXML/AppointmentsMangment.fxml"));
        Parent appointmentMangRoot = appointmentMangLoader.load();
        appointmentMangScene = new Scene(appointmentMangRoot);
        
        FXMLLoader createNewAppointmentLoader = new FXMLLoader(getClass().getResource("/View/AdminFXML/CreateNewAppointments.fxml"));
        Parent createNewAppointmentRoot = createNewAppointmentLoader.load();
        createNewAppointmentScene = new Scene(createNewAppointmentRoot);
        
        FXMLLoader createNewUsreLoader = new FXMLLoader(getClass().getResource("/View/AdminFXML/CreateNewUsers.fxml"));
        Parent createNewUsretRoot = createNewUsreLoader.load();
        createNewUsreScene = new Scene(createNewUsretRoot);
        
        FXMLLoader newUserPatientLoader = new FXMLLoader(getClass().getResource("/View/AdminFXML/NewUserPatient.fxml"));
        Parent newUserPatienttRoot = newUserPatientLoader.load();
        newUserPatientScene = new Scene(newUserPatienttRoot);
        
        FXMLLoader pageControlPatientLoader = new FXMLLoader(getClass().getResource("/View/AdminFXML/PageControlPatients.fxml"));
        Parent pageControlPatientRoot = pageControlPatientLoader.load();
        pageControlPatientScene = new Scene(pageControlPatientRoot);
        
        FXMLLoader singInAdminLoader = new FXMLLoader(getClass().getResource("/View/AdminFXML/SignInAdmin.fxml"));
        Parent singInAdminRoot = singInAdminLoader.load();
        singInAdminScene = new Scene(singInAdminRoot);
        
        FXMLLoader singInPatientLoader = new FXMLLoader(getClass().getResource("/View/AdminFXML/SingInPatient.fxml"));
        Parent singInPatientRoot = singInPatientLoader.load();
        singInPatientScene = new Scene(singInPatientRoot);
        
        this.setScene(singInPatientScene);
        this.setTitle("Clinic Appointment");
        
        
    }
    public void changeSceneToPatientMang(){
        this.setScene(patientMangScene);
    }
    public void changeSceneToAppointmentMang(){
        this.setScene(appointmentMangScene);
    }
    public void changeSceneToCreateNewAppointment(){
        this.setScene(createNewAppointmentScene);
    }
    
    public void changeSceneToCreateNewUsre(){
        this.setScene(createNewUsreScene);
    }
    
    public void changeSceneToNewUserPatient(){
        this.setScene(newUserPatientScene);
    }
    
    public void changeSceneToPageControlPatient(){
        this.setScene(pageControlPatientScene);
    }
    
    public void changeSceneToSingInAdmin(){
        this.setScene(singInAdminScene);
    }
    
    public void changeSceneToSingInPatient(){
        this.setScene(singInPatientScene);
    }
    
}
