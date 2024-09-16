package com.maddev.coozy.controller;
import com.maddev.coozy.model.User;
import com.maddev.coozy.model.UserDAO;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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


            } else {
                // Incorrect Login
                showAlert(AlertType.ERROR, "Login Failed", "Incorrect Password");

            }
        } else {
            // User not found
            showAlert(AlertType.ERROR, "Incorrect Username", "User not found.");

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