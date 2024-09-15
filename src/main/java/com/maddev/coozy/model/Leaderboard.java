package com.maddev.coozy.model;

import java.util.ArrayList;
import java.util.List;

public class Leaderboard {
    private List<LeaderboardEntry> entries;

    public Leaderboard() {
        this.entries = new ArrayList<>();
        // In a real application, you would likely load data from a database here
    }

    public List<LeaderboardEntry> getEntries() {
        return entries;
    }

    public void addEntry(LeaderboardEntry entry) {
        entries.add(entry);
    }

    public void removeEntry(LeaderboardEntry entry) {
        entries.remove(entry);
    }

    public List<LeaderboardEntry> getEntriesByPeriod(String period) {
        List<LeaderboardEntry> filteredEntries = new ArrayList<>();
        for (LeaderboardEntry entry : entries) {
            if (entry.getPeriod().equals(period)) {
                filteredEntries.add(entry);
            }
        }
        return filteredEntries;
    }

    // Add more methods as needed, such as sorting entries, updating scores, etc.
}