package com.maddev.coozy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

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

}
