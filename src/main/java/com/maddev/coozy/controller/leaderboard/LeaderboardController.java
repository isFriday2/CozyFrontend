package com.maddev.coozy.controller.leaderboard;

import com.maddev.coozy.model.User;
import com.maddev.coozy.model.Chore;
import com.maddev.coozy.model.UserDAO;
import com.maddev.coozy.model.ChoreDAO;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.geometry.Pos;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class LeaderboardController {

    @FXML
    private VBox entriesContainer;
    @FXML
    private VBox leaderboardContainer;

    private UserDAO userDAO;
    private ChoreDAO choreDAO;
    private User currentUser;


    public LeaderboardController() {
        userDAO = new UserDAO();
        choreDAO = new ChoreDAO();

        System.out.println("LeaderboardController initialize called");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/maddev/coozy/leaderboard.fxml"));
        fxmlLoader.setController(this);

        try {
            leaderboardContainer = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load leaderboard.fxml", e);
        }
    }

    public void setUser(User user) {
        this.currentUser = user;
    }

    // Renamed from init() to initializeLeaderboard()
    public void initLeaderboard() {
        updateLeaderboard();
    }

    private void updateLeaderboard() {

    }

    public void refreshLeaderboard() {
        updateLeaderboard();
    }
}
