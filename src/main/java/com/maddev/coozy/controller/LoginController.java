package com.maddev.coozy.controller;
import com.maddev.coozy.model.User;
import com.maddev.coozy.model.UserDAO;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
            // Check if the password is correct (using hashed password verification) for user
            if (User.hashPassword(password).equals(user.getPassword())) {
                // Successful login

            } else {
                // Incorrect password

            }
        } else {
            // User not found

        }
    }

    @FXML
    private void handleRegisterLinkAction() {
        // Handle the button click for register acccount
        // Open new stage for register form
    }

    }