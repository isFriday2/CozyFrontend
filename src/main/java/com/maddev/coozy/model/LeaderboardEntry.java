package com.maddev.coozy.model;

/**
 * Represents an entry in a leaderboard.
 * This class associates a user with their total reward points.
 */
public class LeaderboardEntry {
    private User user;
    private int totalReward;

    /**
     * Constructs a new LeaderboardEntry with the specified user and total reward.
     *
     * @param user The User associated with this leaderboard entry
     * @param totalReward The total reward points for this user
     */
    public LeaderboardEntry(User user, int totalReward) {
        this.user = user;
        this.totalReward = totalReward;
    }

    /**
     * Gets the user associated with this leaderboard entry.
     *
     * @return The User object
     */
    public User getUser() {
        return user;
    }

    /**
     * Gets the total reward points for this leaderboard entry.
     *
     * @return The total reward points as an integer
     */
    public int getTotalReward() {
        return totalReward;
    }
}