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
import javafx.stage.Modality;


import java.io.IOException;

public class RegisterController {
    private boolean testing=false;

    // Create an instance of UserDAO to interact with the database
    private UserDAO userDAO = new UserDAO();
    public void setTesting(){
        testing=true;
        userDAO=new UserDAO(true);
    }

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
            if(!testing) showAlert(Alert.AlertType.ERROR, "Missing Fields", "Please fill in all fields.");
            return;
        }

        // Create new object
        User newuser = new User(username, email, nickname, home, hashedPassword);

        boolean isUserAdded = userDAO.insert(newuser);

        // Add new user to DB using new object - complete check in same step
        if (isUserAdded) { // If new user insert boolean output true
            if(!testing) showAlert(Alert.AlertType.INFORMATION, "Registration Successful!", "User registered successfully.");
            //Redirect to Log in
            // Get the current stage
            Stage currentStage = (Stage) registerButton.getScene().getWindow();
            redirectToLogin(currentStage);

        } else { // If new user insert boolean output false
            if(!testing) showAlert(Alert.AlertType.ERROR, "Registration Failed!", "There was an error registering the user.");
        }

    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();  // This makes it modal and waits for user interaction
    }

    @FXML
    private void handleCancelButtonAction() {
        // Close the registration window
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        redirectToLogin(stage);
    }

    private void redirectToLogin(Stage stage) {
        try {
            // Load login.fxml
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/maddev/coozy/login.fxml"));
            Parent root = fxmlLoader.load();
            LoginController controller = fxmlLoader.getController();
            if(testing) controller.setTesting();

            // Create a new stage for the login screen
            stage.setTitle("Login Page");
            stage.setScene(new Scene(root, 600, 400)); // Set the size if needed

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load login screen.");
        }
    }
}