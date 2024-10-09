package com.maddev.coozy.controller;


import com.maddev.coozy.AlertUtil;
import com.maddev.coozy.model.User;
import com.maddev.coozy.model.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Modality;


import java.io.IOException;

public class RegisterController {

    // Create an instance of UserDAO to interact with the database
    private UserDAO userDAO = new UserDAO();

    // Declare IDs for fields
    @FXML
    private TextField usernameField;
    @FXML
    private TextField nicknameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField homeField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerButton;
    @FXML
    private Button cancelButton;

    // Handle Button Clicks
    // Register
    @FXML
    private void handleRegisterButtonAction() {
        // Get User Inputs
        String username = usernameField.getText();
        String nickname = nicknameField.getText();
        String email = emailField.getText();
        String home = homeField.getText();
        String password = passwordField.getText();
        // Hash Password
        String hashedPassword = User.hashPassword(password);

        // Mandatory Inputs
        if (username.isEmpty() || nickname.isEmpty() || email.isEmpty() || home.isEmpty() || password.isEmpty()) {
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Empty Fields", "Please fill in all fields and try again");

            return;
        }

        // Create new object
        User newuser = new User(username, email, nickname, home, hashedPassword);

        boolean isUserAdded = userDAO.insert(newuser);

        // Add new user to DB using new object - complete check in same step
        if (isUserAdded) { // If new user insert boolean output true
            AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "You have successfully registerd!");

            //Redirect to Login
            // Get the current stage
            Stage currentStage = (Stage) registerButton.getScene().getWindow();
            currentStage.close();
            redirectToLogin();

        } else { // If new user insert boolean output false
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Registration Failed", "Could not register the user at this time.");

        }

    }

    @FXML
    private void handleCancelButtonAction() {
        // Close the registration window
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        redirectToLogin();
    }

    private void redirectToLogin() {
        try {
            // Load login.fxml
            Parent root = FXMLLoader.load(getClass().getResource("/com/maddev/coozy/login.fxml"));

            // Create a new stage for the login screen
            Stage loginStage = new Stage();
            loginStage.setTitle("Login Page");
            loginStage.setScene(new Scene(root, 600, 400)); // Set the size if needed
            loginStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Login Error", "Failed to load login screen.");

        }
    }
}