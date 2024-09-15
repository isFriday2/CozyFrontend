package com.maddev.coozy.controller.leaderboard;

import com.maddev.coozy.model.LeaderboardEntry;  // Make sure to import the correct model class

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LeaderboardEntryController {
    @FXML private Label nameLabel;
    @FXML private Label roleLabel;
    @FXML private Label scoreLabel;
    @FXML private ImageView profileImageView;

    private LeaderboardEntry entry;

    public void setEntry(LeaderboardEntry entry) {
        this.entry = entry;
    }

    public void setData() {
        nameLabel.setText(entry.getName());
        roleLabel.setText(entry.getRole());
        scoreLabel.setText(String.valueOf(entry.getScore()));
        profileImageView.setImage(new Image("/images/" + entry.getImagePath()));

        // Set the color of the score based on whether it's increasing or decreasing
        scoreLabel.setStyle("-fx-text-fill: " + (entry.getScore() > 0 ? "#4CAF50" : "#FF0000") + ";");
    }
}