package com.maddev.coozy.controller;
import com.maddev.coozy.model.User;
import com.maddev.coozy.model.UserDAO;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

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
            // Check if input password(hashed) matches db instances
            if (hashedInputPassword.equals(user.getPassword())) {
                // Successful login
                try {
                    // Load home-page.fxml as new window
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("com.maddev.coozy/resources/home-page.fxml"));
                    Parent homePage = loader.load();

                    // Get the current stage
                    Stage stage = (Stage) loginButton.getScene().getWindow();

                    // Set New Window Scene
                    stage.setScene(new Scene(homePage));
                    stage.setTitle("Home");

                } catch (Exception e) {

                    // Create Error Alert
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("An error occurred");
                    alert.setContentText("There was an issue loading the home page. Please try again later.");
                    alert.showAndWait();
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
    private void handleRegisterLinkAction() {
        // Handle the button click for register acccount
        // Open new stage for register form
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