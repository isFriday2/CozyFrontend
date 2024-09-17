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
        entriesContainer.getChildren().clear();
        List<User> users = userDAO.getAll();

        Map<User, Integer> userPoints = calculateUserPoints(users);

        List<Map.Entry<User, Integer>> sortedEntries = userPoints.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .collect(Collectors.toList());

        for (int i = 0; i < sortedEntries.size(); i++) {
            Map.Entry<User, Integer> entry = sortedEntries.get(i);
            HBox userEntry = createUserEntry(entry.getKey(), entry.getValue(), i + 1);
            entriesContainer.getChildren().add(userEntry);
        }
    }

    private Map<User, Integer> calculateUserPoints(List<User> users) {
        Map<User, Integer> userPoints = new HashMap<>();
        for (User user : users) {
            List<Chore> userChores = choreDAO.getAllByUser(user.getId());
            int points = userChores.stream()
                    .filter(Chore::isCompleted)
                    .mapToInt(Chore::getReward)
                    .sum();
            userPoints.put(user, points);
        }
        return userPoints;
    }

    private HBox createUserEntry(User user, int points, int rank) {
        HBox userEntry = new HBox(10);
        userEntry.setAlignment(Pos.CENTER_LEFT);
        userEntry.getStyleClass().add("leaderboard-entry");

        Label rankLabel = new Label(String.valueOf(rank));
        rankLabel.getStyleClass().add("rank-label");

        ImageView avatar = new ImageView(new Image(getClass().getResourceAsStream("/image/profilePic.png"))); // Replace with actual path
        avatar.setFitHeight(40);
        avatar.setFitWidth(40);
        Circle clip = new Circle(20, 20, 20);
        avatar.setClip(clip);

        VBox userInfo = new VBox(5);
        Label nameLabel = new Label(user.getNickname());
        Label roleLabel = new Label(user.getHome());
        userInfo.getChildren().addAll(nameLabel, roleLabel);

        Label pointsLabel = new Label(points + "c");
        pointsLabel.getStyleClass().add("points-label");

        HBox.setHgrow(userInfo, Priority.ALWAYS);
        userEntry.getChildren().addAll(rankLabel, avatar, userInfo, pointsLabel);

        return userEntry;
    }
    public void refreshLeaderboard() {
        updateLeaderboard();
    }
}