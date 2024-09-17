package com.maddev.coozy.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Leaderboard {
    private List<User> users;
    private List<Chore> chores;
    private List<LeaderboardEntry> entries;

    public Leaderboard(List<User> users, List<Chore> chores) {
        this.users = users;
        this.chores = chores;
        calculateLeaderboard();
    }

    private void calculateLeaderboard() {
        Map<Integer, Integer> userPoints = calculateUserPoints();
        entries = userPoints.entrySet().stream()
                .map(entry -> {
                    User user = users.stream()
                            .filter(u -> u.getId() == entry.getKey())
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    return new LeaderboardEntry(user, entry.getValue());
                })
                .sorted((e1, e2) -> Integer.compare(e2.getPoints(), e1.getPoints()))
                .collect(Collectors.toList());

        // Assign ranks
        for (int i = 0; i < entries.size(); i++) {
            entries.get(i).setRank(i + 1);
        }
    }

    private Map<Integer, Integer> calculateUserPoints() {
        return chores.stream()
                .filter(Chore::isCompleted)
                .collect(Collectors.groupingBy(
                        Chore::getUserId,
                        Collectors.summingInt(Chore::getReward)
                ));
    }

    public List<LeaderboardEntry> getEntries() {
        return entries;
    }

    public static class LeaderboardEntry {
        private User user;
        private int points;
        private int rank;

        public LeaderboardEntry(User user, int points) {
            this.user = user;
            this.points = points;
        }

        // Getters and setters
        public User getUser() { return user; }
        public int getPoints() { return points; }
        public int getRank() { return rank; }
        public void setRank(int rank) { this.rank = rank; }
    }
}