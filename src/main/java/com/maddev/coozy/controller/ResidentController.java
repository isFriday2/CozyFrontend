package com.maddev.coozy.controller;

import com.maddev.coozy.model.User;
import com.maddev.coozy.model.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.List;

/**
 * Controller class for the resident view.
 * Handles the display of residents belonging to the same home as the current user.
 */
public class ResidentController {
    private boolean testing = false;
    private UserDAO userDAO;
    private User user;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox ResidentContainer;

    /**
     * Constructs a new ResidentController.
     * Initializes the UserDAO for database operations.
     */
    public ResidentController() {
        userDAO = new UserDAO();
    }

    /**
     * Sets the controller to testing mode.
     * In testing mode, it uses a test database.
     */
    public void setTesting() {
        testing = true;
        userDAO = new UserDAO(true);
    }

    /**
     * Sets the current user for the controller.
     * @param user The current User object.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Initializes the view by displaying residents.
     */
    @FXML
    public void init() {
        this.displayResidents();
    }

    /**
     * Displays all residents belonging to the same home as the current user.
     */
    private void displayResidents() {
        VBox entriesContainer = new VBox(10.0);
        entriesContainer.setStyle("-fx-padding: 15;");

        List<User> users = this.userDAO.getAllByHome(user.getHome());

        for (User user : users) {
            BorderPane entryPane = createResidentEntryPane(user);
            entriesContainer.getChildren().add(entryPane);
        }

        this.scrollPane.setContent(entriesContainer);
    }

    /**
     * Creates a pane displaying a resident's information.
     * @param user The User object representing the resident.
     * @return A BorderPane containing the resident's information.
     */
    private BorderPane createResidentEntryPane(User user) {
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #CCE3DE; -fx-background-radius: 25; -fx-padding: 15;");

        VBox leftBox = new VBox(5.0);

        Label idLabel = new Label(user.getNickname() + "   ID: " + user.getId());
        idLabel.setFont(Font.font("Arial Rounded MT Bold", 18.0));
        idLabel.setStyle("-fx-text-fill: #6B9080;");

        Label usernameLabel = new Label("Username: " + user.getUsername());
        usernameLabel.setFont(Font.font("Arial Rounded MT Bold", 17.0));
        usernameLabel.setStyle("-fx-text-fill: #6B8F7F80;");

        leftBox.getChildren().addAll(idLabel, usernameLabel);

        pane.setLeft(leftBox);

        return pane;
    }
}