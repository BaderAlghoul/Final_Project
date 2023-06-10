/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.io.IOException;

/**
 *
 * @author Bader M
 */
public class ViewManager {
    
    public static SignInPatientPage signPage ;
    public static AdminDashboard adminPage;


    
    public ViewManager(){
    }

    public static void openSignInPatientPage(){
        if (signPage == null) {
            signPage = new SignInPatientPage();
            signPage.show();
        } else {
            signPage.show();
        }
        
    }
    
        public static void closeSignInPatientPage(){
        if(signPage != null)
            signPage.close();
    }
        
            public static void openAdminPage() throws IOException{
        if (adminPage == null) {
            adminPage = new AdminDashboard();
            adminPage.show();
        } else {
            adminPage.show();
        }
        
    }
            
        public static void closeAdminPage(){
        if(adminPage != null)
            adminPage.close();
    }
}
