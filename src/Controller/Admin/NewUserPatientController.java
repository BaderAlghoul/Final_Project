/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller.Admin;

import Model.User;
import Model.UserJpaController;
import View.ViewManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author Bader M
 */
public class NewUserPatientController implements Initializable {

    @FXML
    private TextField txtfiledusername;
    @FXML
    private TextField txtfiledEmail;
    @FXML
    private TextField txtfiledPhone;
    @FXML
    private TextField txtfiledLastName;
    @FXML
    private TextField txtfiledFirstName;
    @FXML
    private TextField txtfiledAge;
    @FXML
    private RadioButton radoiMale;
    @FXML
    private ToggleGroup genderGroup;
    @FXML
    private RadioButton radoiFemale;
    @FXML
    private Button saveNewUserPatinetBtn;
    @FXML
    private Button cancelPatinetBtn;
    @FXML
    private PasswordField txtfiledPassword;
    @FXML
    private PasswordField txtfiledConfirmPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void saveNewUserPatinet(ActionEvent event) {
         // get data
        String userName = txtfiledusername.getText();
        String password = txtfiledPassword.getText();
        String firstName = txtfiledFirstName.getText();
        String lastName = txtfiledLastName.getText();
        String agetxt = txtfiledAge.getText();
        double age = Double.parseDouble(agetxt);
        String email = txtfiledEmail.getText();
        String phone = txtfiledPhone.getText();
        String gender = ((RadioButton)genderGroup.getSelectedToggle()).getText();

        if(!userName.isEmpty()&&!password.isEmpty()&&!firstName.isEmpty()&&!lastName.isEmpty()
                &&!agetxt.isEmpty()&&!email.isEmpty()){
        if( txtfiledPassword.getText().length() < 6 && !txtfiledConfirmPassword.getText().equals(password)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("تاكد من كلمة المرور ");
            alert.setContentText("تاكد من كلمة المرور");
            alert.showAndWait();
            }else{
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("ClinicAppointmentPU");
                UserJpaController userJpaController = new UserJpaController(emf);
                User user = new User(null , userName , password , firstName,lastName , age , email , phone , gender , "patient");
                userJpaController.create(user);
                ViewManager.adminPage.changeSceneToPageControlPatient();
                
                txtfiledusername.clear();
                txtfiledPassword.clear();
                txtfiledFirstName.clear();
                txtfiledLastName.clear();
                txtfiledAge.clear();
                txtfiledEmail.clear();
                txtfiledPhone.clear();
                txtfiledConfirmPassword.clear();
        } 
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Register");
            alert.setContentText("الرجاء ادخال البيانات");
            alert.showAndWait();
        }  
    }

    @FXML
    private void cancelUserPatinetCreation(ActionEvent event) {
        ViewManager.adminPage.changeSceneToSingInPatient();

    }
    
}
