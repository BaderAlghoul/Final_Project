/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Bader M
 */
public class SignInPatientPage extends Stage{
    final Label txtEmilLabel ;
    final TextField email ;
    final Label txtPasswordLabel ;
    final PasswordField password ;
    final Button btnSignIn ;
    final Label regesterPatientLabel ;
    final Label signInAdminLabel ;
    
    public SignInPatientPage(){
        txtEmilLabel = new Label("Email: ");
        email = new TextField();
        HBox hboxEmail = new HBox();
        hboxEmail.getChildren().addAll(txtEmilLabel , email);
        hboxEmail.setAlignment(Pos.CENTER);
        hboxEmail.setSpacing(15);
        
        txtPasswordLabel = new Label("Password: ");
        password = new PasswordField();
        HBox hboxPassword = new HBox();
        hboxPassword.getChildren().addAll( txtPasswordLabel , password);
        hboxPassword.setSpacing(15);
        hboxPassword.setAlignment(Pos.CENTER);
        
        btnSignIn = new Button("Sign In");
        btnSignIn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                String sEmail = email.getText();
                String sPassword = password.getText();
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("ClinicAppointmentPU");
                EntityManager em = emf.createEntityManager();
                TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email and u.role = :role", User.class);
                query.setParameter("email", sEmail);
                query.setParameter("role", "patient");
                User user = query.getSingleResult();
                if(user != null && user.getPassword().equals(sPassword)){
                try{
                    ViewManager.openAdminPage();
                    ViewManager.closeSignInPatientPage();
                 }catch(IOException ex){
                    Logger.getLogger(SignInPatientPage.class.getName()).log(Level.SEVERE, null, ex);

                    }
                }else{
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Error Sing Up");
                        alert.setContentText("الرجاء التاكد من البيانات المدخلة");
                        alert.showAndWait();
                }

         
            }
        });
        regesterPatientLabel = new Label("Regester Patient ? ");
        signInAdminLabel = new Label("Sing In Doctor ");
        
        VBox vbox = new VBox();
        vbox.getChildren().addAll(hboxEmail , hboxPassword , btnSignIn 
                , regesterPatientLabel , signInAdminLabel);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        
        Scene scene = new Scene(vbox , 500 , 600);
        
        this.setTitle("Sign In Patient");
        this.setScene(scene);
        this.show();
    }
    
    

}
