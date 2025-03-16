import dao.UserDAO;
import Jdbc.DbConnection;
import models.User;
import ui.HomeScreen;
import ui.LoginScreen;
import ui.RegisterScreen;
import ui.SplashScreen;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting application...");

        UserDAO userDAO = new UserDAO();
        
        try {
            SplashScreen splashScreen = new SplashScreen();
            splashScreen.setVisible(true);
            
            try {
                Thread.sleep(5000); 
                splashScreen.setVisible(false);
                splashScreen.dispose(); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("Error initializing SplashScreen: " + e.getMessage());
            e.printStackTrace();
        }
        
        boolean isLoggedIn = false;
        
        if (isLoggedIn) {
            try {
                User currentUser = new User(); 
                HomeScreen homeScreen = new HomeScreen(currentUser);
                homeScreen.setVisible(true);
            } catch (Exception e) {
                System.out.println("Error initializing HomeScreen: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            try {
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.setVisible(true);
            } catch (Exception e) {
                System.out.println("Error initializing LoginScreen: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        try {
            DbConnection.getConnection();
            System.out.println("Database connection successful.");
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("Application initialized.");
    }
}