package com.maddev.coozy.model;

import java.time.LocalDate;

/**
 * Represents a chore in the system.
 * This class encapsulates all the information related to a single chore,
 * including its identifier, associated user, description, reward, and status.
 */
public class Chore {
    private int id;
    private int userId;
    private String name;
    private String description;
    private int reward;
    private String home;
    private String icon;
    private LocalDate dueDate;
    private boolean completed;

    /**
     * Constructs a new Chore with all fields specified.
     * @param id The identifier and primary key of chore
     * @param userId The identifier of the user associated with this chore
     * @param name The name/title of the chore
     * @param description A brief description of the chore
     * @param reward The reward (in points) for completing the chore
     * @param home The identifier of the user's home
     * @param icon The name of the image associated with the chore
     * @param dueDate The due date of the chore
     * @param completed The completion status of the chore
     */
    public Chore(int id, int userId, String name, String description, int reward,
                 String home, String icon, LocalDate dueDate, boolean completed) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.home = home;
        this.icon = icon;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    /**
     * Constructs a new Chore without specifying the id.
     * This constructor is useful when the id is auto-incremented by the database.
     * @param userId The identifier of the user associated with this chore
     * @param name The name/title of the chore
     * @param description A brief description of the chore
     * @param reward The reward (in points) for completing the chore
     * @param home The identifier of the user's home
     * @param icon The name of the image associated with the chore
     * @param dueDate The due date of the chore
     * @param completed The completion status of the chore
     */
    public Chore(int userId, String name, String description, int reward,
                 String home, String icon, LocalDate dueDate, boolean completed) {
        this(0, userId, name, description, reward, home, icon, dueDate, completed);
    }

    /**
     * Gets the chore's identifier.
     * @return The chore's id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the associated user's identifier.
     * @return The user's id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Gets the name of the chore.
     * @return The chore's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the chore.
     * @return The chore's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the reward for completing the chore.
     * @return The reward value
     */
    public int getReward() {
        return reward;
    }

    /**
     * Gets the home identifier associated with the chore.
     * @return The home identifier
     */
    public String getHome() {
        return home;
    }

    /**
     * Gets the icon associated with the chore.
     * @return The icon name
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Gets the due date of the chore.
     * @return The due date
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Checks if the chore is completed.
     * @return true if the chore is completed, false otherwise
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Sets the chore's identifier.
     * @param id The new id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the associated user's identifier.
     * @param userId The new user id to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Sets the name of the chore.
     * @param name The new name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the description of the chore.
     * @param description The new description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the reward for completing the chore.
     * @param reward The new reward value to set
     */
    public void setReward(int reward) {
        this.reward = reward;
    }

    /**
     * Sets the home identifier associated with the chore.
     * @param home The new home identifier to set
     */
    public void setHome(String home) {
        this.home = home;
    }

    /**
     * Sets the icon associated with the chore.
     * @param icon The new icon name to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * Sets the due date of the chore.
     * @param dueDate The new due date to set
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Sets the completion status of the chore.
     * @param completed The new completion status to set
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}