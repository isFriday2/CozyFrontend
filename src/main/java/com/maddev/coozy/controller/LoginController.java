package com.maddev.coozy.controller;

import com.maddev.coozy.controller.chore.ChoreViewController;
import com.maddev.coozy.model.User;
import com.maddev.coozy.model.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller class for the login view.
 * Handles user authentication and navigation to other views.
 */
public class LoginController {
    private boolean testing = false;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink registerLink;

    private UserDAO userDAO = new UserDAO();

    /**
     * Sets the controller to testing mode.
     * In testing mode, it uses a test database and disables alerts.
     */
    public void setTesting() {
        testing = true;
        userDAO = new UserDAO(true);
    }

    /**
     * Handles the login button click event.
     * Authenticates the user and navigates to the home page if successful.
     */
    @FXML
    private void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = userDAO.getByUsername(username);

        if (user != null) {
            String hashedInputPassword = User.hashPassword(password);
            System.out.println(hashedInputPassword);
            if (hashedInputPassword.equals(user.getPassword())) {
                try {
                    navigateToHomePage(user);
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to load home page.");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Incorrect Password");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Incorrect Details", "User not found.");
        }
    }

    /**
     * Handles the register link click event.
     * Navigates to the registration form.
     */
    @FXML
    private void handleRegisterAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/maddev/coozy/register.fxml"));
            Parent registerForm = loader.load();
            RegisterController controller = loader.getController();
            if (testing) controller.setTesting();
            Stage stage = (Stage) registerLink.getScene().getWindow();
            stage.setScene(new Scene(registerForm));
            stage.requestFocus();
            stage.setTitle("Register");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load Registration Form.");
        }
    }

    /**
     * Displays an alert dialog.
     * @param type The type of the alert.
     * @param title The title of the alert.
     * @param message The message content of the alert.
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        if (testing) return;
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Navigates to the home page.
     * @param user The authenticated user.
     * @throws IOException If the home page FXML file cannot be loaded.
     */
    private void navigateToHomePage(User user) throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/maddev/coozy/home-page.fxml"));
        Parent homePage = loader.load();
        ChoreViewController controller = loader.getController();
        controller.setUser(user);
        if (testing) controller.setTesting();
        controller.init();
        Scene scene = new Scene(homePage, 1133, 744);
        stage.setScene(scene);
    }
}