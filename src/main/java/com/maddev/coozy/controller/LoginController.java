package com.maddev.coozy.controller;
import com.maddev.coozy.controller.chore.ChoreViewController;
import com.maddev.coozy.controller.leaderboard.LeaderboardController;
import com.maddev.coozy.model.User;
import com.maddev.coozy.model.UserDAO;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink registerLink;

    // Create an instance of UserDAO to interact with the database
    private UserDAO userDAO = new UserDAO();

    // Handle button click logic
    @FXML
    private void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Fetch user from database using username
        User user = userDAO.getByUsername(username);

        // Check user input
        if (user != null) {
            // Hash password input
            String hashedInputPassword = User.hashPassword(password);
            System.out.println(hashedInputPassword);
            // Check if input password(hashed) matches db instances
            if (hashedInputPassword.equals(user.getPassword())) {
                // Successful login
                try {


//                    FXMLLoader leaderboardFXML = new FXMLLoader(getClass().getResource("/com/maddev/coozy/leaderboard.fxml"));
//                    LeaderboardController leaderboard = new LeaderboardController(user);
//                    leaderboardFXML.setController(leaderboard);
//                    Object leaderboardView = leaderboardFXML.load();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/maddev/coozy/home-page.fxml"));
                    Parent homePage = loader.load();
                    ChoreViewController controller=loader.getController();
                    controller.setUser(user);
                    controller.init();



                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.setScene(new Scene(homePage));
                    stage.setTitle("Home");
                } catch (IOException e) {
                    e.printStackTrace(); // This will print the stack trace to help diagnose the problem
                    showAlert(AlertType.ERROR, "Error", "Failed to load home page.");
                }


            } else {
                // Incorrect Login
                showAlert(AlertType.ERROR, "Login Failed", "Incorrect Password");

            }
        } else {
            // User not found
            showAlert(AlertType.ERROR, "Incorrect Details", "User not found.");

        }
    }

    @FXML
    private void handleRegisterAction() {
        // Handle the button click for register account
        // Open new stage for register form
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/maddev/coozy/register.fxml"));
            Parent registerForm = loader.load();
            Stage stage = (Stage) registerLink.getScene().getWindow();
            stage.setScene(new Scene(registerForm));
            stage.setTitle("Register");
        } catch (IOException e) {
            e.printStackTrace(); // This will print the stack trace to help diagnose the problem
            showAlert(AlertType.ERROR, "Error", "Failed to load Registration Form.");
        }
    }

    // Method for showing alerts using Alert
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}