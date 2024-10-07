package com.maddev.coozy.model;

public class LeaderboardEntry {
    private User user;
    private int totalReward;

    public LeaderboardEntry(User user, int totalReward) {
        this.user = user;
        this.totalReward = totalReward;
    }

    public User getUser() {
        return user;
    }

    public int getTotalReward() {
        return totalReward;
    }
}
