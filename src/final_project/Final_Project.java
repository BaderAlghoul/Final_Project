/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_project;

import Model.User;
import Model.UserJpaController;
import View.ViewManager;
import java.io.IOException;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Bader M
 */
public class Final_Project extends Application{


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
//        ViewManager.openAdminPage();
        Pane pane = FXMLLoader.load(getClass().getResource("/View/AdminFXML/SingInPatient.fxml"));
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();


/*
         // Example JPA
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ClinicAppointmentPU");
        UserJpaController userController = new UserJpaController(emf);
        User user = new User(null, "bader alghou", "123456789", "bader", "Alghoul", 22.5, "bader1@gmail.com" ,"059999999" , "male", "patient");
        userController.create(user);
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("User.findByFirtName").setParameter("firtName", "bader");
        List<User> users = query.getResultList();
        users.forEach(e->System.out.println(e.getUsername()));
*/
        
    }
    
}
