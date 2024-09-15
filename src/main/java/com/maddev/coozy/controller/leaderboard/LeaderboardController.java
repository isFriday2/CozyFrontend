package com.maddev.coozy.controller.leaderboard;

import com.maddev.coozy.model.Leaderboard;
import com.maddev.coozy.model.LeaderboardEntry;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class LeaderboardController {
    private Leaderboard model;

    @FXML
    private VBox entriesBox;
    @FXML
    private Label dateLabel;

    public LeaderboardController() {
        model = new Leaderboard();
    }

    public void init() {
        dateLabel.setText(LocalDate.now().toString());
        List<LeaderboardEntry> entries = model.getEntries();
        for (LeaderboardEntry entry : entries) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/maddev/coozy/leaderboard-entry.fxml"));
            try {
                AnchorPane anchorPane = fxmlLoader.load();
                LeaderboardEntryController controller = fxmlLoader.getController();
                controller.setEntry(entry);
                controller.setData();
                entriesBox.getChildren().add(anchorPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onDailyButtonClicked() {
        updateLeaderboard("daily");
    }

    @FXML
    public void onWeeklyButtonClicked() {
        updateLeaderboard("weekly");
    }

    @FXML
    public void onMonthlyButtonClicked() {
        updateLeaderboard("monthly");
    }

    private void updateLeaderboard(String period) {
        // Update model based on the selected period
        List<LeaderboardEntry> filteredEntries = model.getEntriesByPeriod(period);
        // Refresh the leaderboard entries
        entriesBox.getChildren().clear();
        for (LeaderboardEntry entry : filteredEntries) {
            // Similar to init(), create and add entry views
            // You might want to extract this into a separate method to avoid code duplication
        }
    }

    @FXML
    public void onViewAllClicked() {
        // Implement view all functionality
    }
}