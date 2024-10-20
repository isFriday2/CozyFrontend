package com.maddev.coozy.controller;
import com.maddev.coozy.model.ChoreDAO;
import com.maddev.coozy.model.User;
import com.maddev.coozy.model.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.List;

public class ResidentController {
    private boolean testing=false;
    private UserDAO userDAO;

    public ResidentController() {
        userDAO =  new UserDAO();
    }

    public void setTesting(){
        testing=true;
        userDAO=new UserDAO(true);
    }

    private User user;
    public void setUser(User user){
        this.user = user;
    }

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox ResidentContainer;

    @FXML
    public void init() {
        this.displayResidents();
    }

    private void displayResidents() {
        VBox entriesContainer = new VBox(10.0);
        entriesContainer.setStyle("-fx-padding: 15;");

        // Retrieve all users and display only their username and id
        List<User> users = this.userDAO.getAllByHome(user.getHome());

        for (User user : users) {
            BorderPane entryPane = createResidentEntryPane(user);
            entriesContainer.getChildren().add(entryPane);
        }

        this.scrollPane.setContent(entriesContainer);
    }

    // Creates a pane that shows the user's username and ID
    private BorderPane createResidentEntryPane(User user) {
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #CCE3DE; -fx-background-radius: 25; -fx-padding: 15;");

        VBox leftBox = new VBox(5.0);

        // Display user id and username
        Label idLabel = new Label(user.getNickname()+ "   ID: " + user.getId());
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
