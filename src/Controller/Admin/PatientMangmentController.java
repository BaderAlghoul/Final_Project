package Controller.Admin;

import Model.User;
import Model.UserJpaController;
import Model.exceptions.IllegalOrphanException;
import Model.exceptions.NonexistentEntityException;
import View.ViewManager;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

public class PatientMangmentController implements Initializable {

    @FXML
    private Button PatientManagmentPageBtn;
    @FXML
    private Button appointmentPageBtn;
    @FXML
    private Button logOutBtn;
    @FXML
    private Button patientAppointmentBtn;
    @FXML
    private TableView<User> patientTableView;
    @FXML
    private TableColumn<User, Integer> id;
    @FXML
    private TableColumn<User, String> username;
    @FXML
    private TableColumn<User, String> firstName;
    @FXML
    private TableColumn<User, String> lastName;
    @FXML
    private TableColumn<User, String> email;
    @FXML
    private TableColumn<User, String> password;
    @FXML
    private TableColumn<User, Double> age;
    @FXML
    private TableColumn<User, String> phone;
    @FXML
    private TableColumn<User, String> gender;
    @FXML
    private TableColumn<User, String> role;
    @FXML
    private Button searchNamePatient;
    @FXML
    private TextField patitentSearchTF;
    @FXML
    private TextField usernameTFUpdate;
    @FXML
    private TextField passwordTFUpdate;
    @FXML
    private TextField lastNameTFUpdate;
    @FXML
    private TextField firstNameTFUpdate;
    @FXML
    private TextField emailTFUpdate;
    @FXML
    private TextField phoneTFUpdate;
    @FXML
    private TextField ageTFUpdate;
    @FXML
    private RadioButton radoiMaleUpadate;
    @FXML
    private ToggleGroup genderGroup;
    @FXML
    private RadioButton radoiFemaleUpadate;
    @FXML
    private Button btnShowAllPatient;
    @FXML
    private Button UpdateB;
    @FXML
    private Button deleteB;
    @FXML
    private Button createNewPatientButton;
    
    EntityManagerFactory emf ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        id.setCellValueFactory(new PropertyValueFactory("id"));
        username.setCellValueFactory(new PropertyValueFactory("username"));
        firstName.setCellValueFactory(new PropertyValueFactory("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory("lastName"));
        email.setCellValueFactory(new PropertyValueFactory("email"));
        password.setCellValueFactory(new PropertyValueFactory("password"));
        age.setCellValueFactory(new PropertyValueFactory("age"));
        phone.setCellValueFactory(new PropertyValueFactory("phone"));
        gender.setCellValueFactory(new PropertyValueFactory("gender"));
        role.setCellValueFactory(new PropertyValueFactory("role"));


        patientTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            usernameTFUpdate.setText(newValue.getUsername());
            passwordTFUpdate.setText(newValue.getPassword());
            emailTFUpdate.setText(newValue.getEmail());
            firstNameTFUpdate.setText(newValue.getFirtName());
            lastNameTFUpdate.setText(newValue.getLastName());
            phoneTFUpdate.setText(newValue.getPhone());
            ageTFUpdate.setText(newValue.getAge()+"");
            if(newValue.getGender().equals("male")){
                radoiMaleUpadate.setSelected(true);
                radoiFemaleUpadate.setSelected(false);
            }else if (newValue.getGender().equals("female")){
                radoiFemaleUpadate.setSelected(true);
                radoiMaleUpadate.setSelected(false);
            }
            
            
        }
    });
        
    }    

    @FXML
    private void btnShowPatientManagmentPage(ActionEvent event) {
        
    }

    @FXML
    private void btnShowAppointmentPage(ActionEvent event) {
        ViewManager.adminPage.changeSceneToAppointmentMang();
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
    private void btnSearchNamePatient(ActionEvent event) {
        emf= Persistence.createEntityManagerFactory("ClinicAppointmentPU");
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("User.findByFirtName").setParameter("firtName", patitentSearchTF.getText());
        List<User> users = query.getResultList();
        ObservableList<User> usersList = FXCollections.observableArrayList();
        usersList.addAll(users);
        patientTableView.setItems(usersList);
        patitentSearchTF.clear();
    }

    @FXML
    private void btnShowAllPatient(ActionEvent event) {
        emf= Persistence.createEntityManagerFactory("ClinicAppointmentPU");
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("User.findAllPatient").setParameter("role", "patient");
        List<User> users = query.getResultList();
        ObservableList<User> usersList = FXCollections.observableArrayList();
        usersList.addAll(users);
        patientTableView.setItems(usersList);
    }

    @FXML
    private void btnUpdate(ActionEvent event) throws NonexistentEntityException, Exception {
        if(patientTableView.getSelectionModel().getSelectedItem() != null){
            String userName = usernameTFUpdate.getText();
            String password = passwordTFUpdate.getText();
            String firstName = firstNameTFUpdate.getText();
            String lastName = lastNameTFUpdate.getText();
            String agetxt = ageTFUpdate.getText();
            double age = Double.parseDouble(agetxt);
            String email = emailTFUpdate.getText();
            String phone = phoneTFUpdate.getText();
            String gender = ((RadioButton)genderGroup.getSelectedToggle()).getText();
            
            emf= Persistence.createEntityManagerFactory("ClinicAppointmentPU");
            UserJpaController userJpaUpdate = new UserJpaController(emf);
            User u = patientTableView.getSelectionModel().getSelectedItem();
            u.setUsername(userName);
            u.setPassword(password);
            u.setFirtName(firstName);
            u.setLastName(lastName);
            u.setAge(age);
            u.setEmail(email);
            u.setPhone(phone);
            u.setGender(gender);
            userJpaUpdate.edit(u);
            Alert warnAlert = new Alert(Alert.AlertType.WARNING);
            warnAlert.setTitle("Update User");
            warnAlert.setContentText("تم تعديل المستخدم");
            warnAlert.show(); 
        }else{
                Alert warnAlert = new Alert(Alert.AlertType.WARNING);
                warnAlert.setTitle("Select an user");
                warnAlert.setContentText("الرجاء حدد المستخدم المراد تعديله");
                warnAlert.show();  
        }

    }

    @FXML
    private void btnDelete(ActionEvent event) throws IllegalOrphanException, NonexistentEntityException {
        if(patientTableView.getSelectionModel().getSelectedItem() != null){
            User u = patientTableView.getSelectionModel().getSelectedItem();
            emf= Persistence.createEntityManagerFactory("ClinicAppointmentPU");
            UserJpaController userJpaDelete = new UserJpaController(emf);
            userJpaDelete.destroy(u.getId());
                Alert warnAlert = new Alert(Alert.AlertType.WARNING);
                warnAlert.setTitle("Delete User");
                warnAlert.setContentText("تم حذف المستخدم");
                warnAlert.show(); 

        }else{
            Alert warnAlert = new Alert(Alert.AlertType.WARNING);
            warnAlert.setTitle("Select an user");
            warnAlert.setContentText("الرجاء حدد المستخدم المراد خذفه");
            warnAlert.show();  
        }
        
    }

    @FXML
    private void btnCreateNewPatient(ActionEvent event) {
        ViewManager.adminPage.changeSceneToCreateNewUsre();
    }
    
}
