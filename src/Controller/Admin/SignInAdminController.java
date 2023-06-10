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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 * FXML Controller class
 *
 * @author Bader M
 */
public class SignInAdminController implements Initializable {

    @FXML
    private TextField tfSignInDoctorEmail;
    @FXML
    private Button saveNewUserPatinetBtn;
    @FXML
    private Button cancelPatinetBtn;
    @FXML
    private PasswordField tfSignInDoctorPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnSaveSingDoctor(ActionEvent event) {
        String sEmail = tfSignInDoctorEmail.getText();
        String sPassword = tfSignInDoctorPassword.getText();
        
        if(!sEmail.isEmpty() && !sPassword.isEmpty()){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ClinicAppointmentPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email and u.role = :role", User.class);
        query.setParameter("email", sEmail);
        query.setParameter("role", "admin");
        User user = query.getSingleResult();
        if(user != null && user.getPassword().equals(sPassword) && user.getRole().equals("admin")){
            
//            if(user.getRole() == "admin"){
            ViewManager.adminPage.changeSceneToPatientMang();
//            }else{
//                Alert alert = new Alert(Alert.AlertType.WARNING);
//                alert.setTitle("Error Sing In");
//                alert.setContentText("تسجيل الدخول فقط للدكتور");
//                alert.showAndWait();
//            }
            }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Sing Up");
            alert.setContentText("الرجاء التاكد من البيانات المدخلة");
            alert.showAndWait();
        }    
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Sing In");
            alert.setContentText("الرجاء ادخال البيانات");
            alert.showAndWait();
        }

    }

    @FXML
    private void btnCancelSingDoctor(ActionEvent event) {
        ViewManager.adminPage.changeSceneToSingInPatient();
    }
    
}
