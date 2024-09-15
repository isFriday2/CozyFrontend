package com.maddev.coozy.model;

public class LeaderboardEntry {
    private String name;
    private String role;
    private int score;
    private String imagePath;
    private String period;  // New field

    public LeaderboardEntry(String name, String role, int score, String imagePath, String period) {
        this.name = name;
        this.role = role;
        this.score = score;
        this.imagePath = imagePath;
        this.period = period;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public int getScore() {
        return score;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getPeriod() {  // New getter method
        return period;
    }

    // Add setters if needed
}