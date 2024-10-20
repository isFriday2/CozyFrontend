package com.maddev.coozy.controller;

import com.maddev.coozy.HelloApplication;
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

import java.io.IOException;

/**
 * Controller class for the user registration view.
 * Handles user registration and navigation to the login view.
 */
public class RegisterController {
    private boolean testing = false;

    private UserDAO userDAO = new UserDAO();

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

    /**
     * Sets the controller to testing mode.
     * In testing mode, it uses a test database and disables alerts.
     */
    public void setTesting() {
        testing = true;
        userDAO = new UserDAO(true);
    }

    /**
     * Handles the register button click event.
     * Validates input, creates a new user, and redirects to login on success.
     */
    @FXML
    private void handleRegisterButtonAction() {
        String username = usernameField.getText();
        String nickname = nicknameField.getText();
        String email = emailField.getText();
        String home = homeField.getText();
        String password = passwordField.getText();
        String hashedPassword = User.hashPassword(password);

        if (username.isEmpty() || nickname.isEmpty() || email.isEmpty() || home.isEmpty() || password.isEmpty()) {
            if (!testing) showAlert(Alert.AlertType.ERROR, "Missing Fields", "Please fill in all fields.");
            return;
        }

        User newUser = new User(username, email, nickname, home, hashedPassword);

        boolean isUserAdded = userDAO.insert(newUser);

        if (isUserAdded) {
            if (!testing) showAlert(Alert.AlertType.INFORMATION, "Registration Successful!", "User registered successfully.");
            Stage currentStage = (Stage) registerButton.getScene().getWindow();
            redirectToLogin(currentStage);
        } else {
            if (!testing) showAlert(Alert.AlertType.ERROR, "Registration Failed!", "There was an error registering the user.");
        }
    }

    /**
     * Displays an alert dialog.
     * @param alertType The type of the alert.
     * @param title The title of the alert.
     * @param message The message content of the alert.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Handles the cancel button click event.
     * Redirects to the login view.
     */
    @FXML
    private void handleCancelButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        redirectToLogin(stage);
    }

    /**
     * Redirects to the login view.
     * @param stage The current stage to be updated with the login view.
     */
    private void redirectToLogin(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/maddev/coozy/login.fxml"));
            Parent root = fxmlLoader.load();
            LoginController controller = fxmlLoader.getController();
            if (testing) controller.setTesting();

            stage.setTitle("Login Page");
            stage.setScene(new Scene(root, 600, 400));
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load login screen.");
        }
    }
}