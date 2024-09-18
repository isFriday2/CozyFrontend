package com.maddev.coozy.model;

// import jBCrypt password hashing library
// Installed through Maven - required in modeule-info.java

import java.time.LocalDate;

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
     * Constructs a new Contact with the specified first name, last name, email, and phone number.
     * @param id The identifier and primary key of chore
     * @param userId The identifier and primary key of user
     * @param name The name/ title of the chore
     * @param description The brief description of what's the chore
     * @param reward The reward in integer of on a chores completion
     * @param home The identifier of user's home
     * @param icon The name of the image on the chores entry
     * @param  dueDate The due date of the chore
     * @param completed The status of the chore's completion
     */


    // Constructor including all fields
    public Chore(int id, int userId, String name, String description, int reward,
                 String home, String icon, LocalDate dueDate, boolean completed) {
        this.id = id;
        this.userId=userId;
        this.name=name;
        this.description=description;
        this.reward=reward;
        this.home = home;
        this.icon=icon;
        this.dueDate=dueDate;
        this.completed=completed;
    }

    // Constructor without id for auto incrementing
    public Chore(int userId, String name, String description, int reward,
                 String home, String icon, LocalDate dueDate, boolean completed) {
        this.userId=userId;
        this.name=name;
        this.description=description;
        this.reward=reward;
        this.home = home;
        this.icon=icon;
        this.dueDate=dueDate;
        this.completed=completed;
    }

    // Create getter functions
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public int getReward(){
        return reward;
    }

    public String getHome(){
        return home;
    }

    public String getIcon(){
        return icon;
    }

    public LocalDate getDueDate(){
        return dueDate;
    }

    public boolean isCompleted(){
        return completed;
    }

    // Create setter functions
    public void setId(int id) {
        this.id=id;
    }

    public void setUserId(int userId) {
        this.userId=userId;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public void setReward(int reward){
        this.reward=reward;
    }

    public void setHome(String home){
        this.home=home;
    }

    public void setIcon(String icon){
        this.icon=icon;
    }

    public void setDueDate(LocalDate dueDate){
        this.dueDate=dueDate;
    }

    public void setCompleted(boolean completed){
        this.completed=completed;
    }
}
