package com.maddev.coozy.controller.leaderboard;

import com.maddev.coozy.model.User;
import com.maddev.coozy.model.Chore;
import com.maddev.coozy.model.UserDAO;
import com.maddev.coozy.model.ChoreDAO;
import com.maddev.coozy.model.LeaderboardEntry;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.util.*;

public class LeaderboardController {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox leaderboardContainer;
    private VBox leaderboardContent;
    private User currentUser;
    private UserDAO userDAO;
    private ChoreDAO choreDAO;
    private List<LeaderboardEntry> leaderboard;


    public LeaderboardController() {
        this.userDAO = new UserDAO();
        this.choreDAO = new ChoreDAO();
        this.leaderboard = new ArrayList<>();
        this.leaderboardContent = new VBox(10);
//        this.CurrentUser = user;

    }

    public void setUser(User user){
        currentUser = user;
    }

    @FXML
    public void init(){
        System.out.println("Leaderboard container initialised with:" + leaderboardContent.getChildren().size());
        generateLeaderboard();
        displayLeaderboard();
        scrollPane.setContent(leaderboardContent);
    }

    private void  generateLeaderboard(){
        List<User> users = userDAO.getAll();
        System.out.println(currentUser.getUsername());
        for (User user : users) {
            List<Chore> completedChores = choreDAO.getAllCompletedByUser(user.getId());
//            System.out.println("Current User" + CurrentUser.getHome()+ "user" + user.getHome());
//            if(!Objects.equals(CurrentUser.getHome(), user.getHome())) continue;
//            System.out.println("Current User" + CurrentUser.getHome()+ "user" + user.getHome());
            int totalReward = calculateTotalReward(completedChores);
            leaderboard.add(new LeaderboardEntry(user, totalReward));
        }

        // Sort the leaderboard by total reward in descending order
        leaderboard.sort((a, b) -> Integer.compare(b.getTotalReward(), a.getTotalReward()));
    }


    private void displayLeaderboard() {
        leaderboardContent.setStyle("-fx-padding: 15;");
        leaderboardContent.getChildren().clear(); // 10 is the spacing between entries

        for (int i = 0; i < leaderboard.size(); i++) {
            LeaderboardEntry entry = leaderboard.get(i);
            BorderPane entryPane = createLeaderboardEntryPane(entry, i + 1);
            leaderboardContent.getChildren().add(entryPane);
        }

        System.out.println("Leaderboard updated with " + leaderboardContent.getChildren().size() + " entries");


    }

    private BorderPane createLeaderboardEntryPane(LeaderboardEntry entry, int rank) {
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #CCE3DE; -fx-background-radius: 25; -fx-padding: 15;");

        VBox leftBox = new VBox(5);
        Label nicknameLabel = new Label(entry.getUser().getNickname());
        nicknameLabel.setFont(Font.font("Arial Rounded MT Bold", 26));
        nicknameLabel.setStyle("-fx-text-fill: #6B9080;");

        Label usernameLabel = new Label(entry.getUser().getUsername());
        usernameLabel.setFont(Font.font("Arial Rounded MT Bold", 17));
        usernameLabel.setStyle("-fx-text-fill: #6B8F7F80;");

        leftBox.getChildren().addAll(nicknameLabel, usernameLabel);

        Label pointsLabel = new Label(entry.getTotalReward() + " points");
        pointsLabel.setFont(Font.font("Arial Rounded MT Bold", 18));
        pointsLabel.setStyle("-fx-text-fill: #6B9080;");

        pane.setLeft(leftBox);
        pane.setRight(pointsLabel);

        return pane;
    }

    private int calculateTotalReward(List<Chore> chores) {
        int total = 0;
        for (Chore chore : chores) {
            total += chore.getReward();
        }
        return total;
    }

    // Method to get a specific user's rank and total reward
    public Map<String, Integer> getUserStats(String username) {
        for (int i = 0; i < leaderboard.size(); i++) {
            LeaderboardEntry entry = leaderboard.get(i);
            if (entry.getUser().getUsername().equals(username)) {
                Map<String, Integer> stats = new HashMap<>();
                stats.put("rank", i + 1);
                stats.put("totalReward", entry.getTotalReward());
                return stats;
            }
        }
        return null; // User not found
    }
}