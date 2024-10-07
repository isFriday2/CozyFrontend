package com.maddev.coozy.controller.leaderboard;

import com.maddev.coozy.model.User;
import com.maddev.coozy.model.Chore;
import com.maddev.coozy.model.UserDAO;
import com.maddev.coozy.model.ChoreDAO;
import com.maddev.coozy.model.LeaderboardEntry;
import javafx.fxml.FXML;
import java.util.*;


public class LeaderboardController {

    private UserDAO userDAO;
    private ChoreDAO choreDAO;
    private List<LeaderboardEntry> leaderboard;

    public LeaderboardController() {
        this.userDAO = new UserDAO();
        this.choreDAO = new ChoreDAO();
        this.leaderboard = new ArrayList<>();
    }

    @FXML
    public void initialize() {
        List<User> users = userDAO.getAll();

        for (User user : users) {
            List<Chore> completedChores = choreDAO.getAllCompletedByUser(user.getId());
            int totalReward = calculateTotalReward(completedChores);
            leaderboard.add(new LeaderboardEntry(user, totalReward));
        }

        // Sort the leaderboard by total reward in descending order
        leaderboard.sort((a, b) -> Integer.compare(b.getTotalReward(), a.getTotalReward()));

        this.displayLeaderboard();

    }

    public void displayLeaderboard() {
        System.out.println("Leaderboard:");
        System.out.println("Rank\tUser\t\tTotal Reward");
        System.out.println("------------------------------------");
        for (int i = 0; i < leaderboard.size(); i++) {
            LeaderboardEntry entry = leaderboard.get(i);
            System.out.printf("%d\t%-15s\t%d%n", i + 1, entry.getUser().getUsername(), entry.getTotalReward());
        }
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