/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller.Admin;

import Model.User;
import View.SignInPatientPage;
import View.ViewManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 * FXML Controller class
 *
 * @author Bader M
 */
public class SingInPatientController implements Initializable {

    @FXML
    private TextField tfSignInPatientEmail;
    @FXML
    private Button saveNewUserPatinetBtn;
    @FXML
    private Button cancelPatinetBtn;
    @FXML
    private PasswordField tfSignInPatientPassword;
    @FXML
    private Label regesterPatient;
    @FXML
    private Label SingInAdmin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnSaveSingPatient(ActionEvent event) throws IOException {
        String sEmail = tfSignInPatientEmail.getText();
        String sPassword = tfSignInPatientPassword.getText();
        if(!sEmail.isEmpty() && !sPassword.isEmpty()){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ClinicAppointmentPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email and u.role = :role", User.class);
        query.setParameter("email", sEmail);
        query.setParameter("role", "patient");
        User user = query.getSingleResult();
        if(user != null && user.getPassword().equals(sPassword)){
            ViewManager.adminPage.changeSceneToPageControlPatient();
            }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Sing In");
            alert.setContentText("الرجاء التاكد من البيانات المدخلة");
            alert.showAndWait();
                }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Sing In");
            alert.setContentText("الرجاء ادخال البيانات");
            alert.showAndWait();
        }

                


    }

    @FXML
    private void btnCancelSingPatient(ActionEvent event) {
       System.exit(0);
//        tfSignInPatientEmail.clear();
//        tfSignInPatientPassword.clear();
    }

    @FXML
    private void labelRegesterPatient(MouseEvent event) {
        ViewManager.adminPage.changeSceneToNewUserPatient();
    }

    @FXML
    private void labelSingInAdmin(MouseEvent event) {
        ViewManager.adminPage.changeSceneToSingInAdmin();
    }

    
}
