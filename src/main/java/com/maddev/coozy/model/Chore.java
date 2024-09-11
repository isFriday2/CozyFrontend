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


    // Constructor including all fields
    public Chore(int id, int userId, String name, String description, int reward, String home, String icon, LocalDate dueDate) {
        this.id = id;
        this.userId=userId;
        this.name=name;
        this.description=description;
        this.reward=reward;
        this.home = home;
        this.icon=icon;
        this.dueDate=dueDate;
    }

    // Constructor without id for auto incrementing
    public Chore(int userId, String name, String description, int reward, String home, String icon, LocalDate dueDate) {
        this.userId=userId;
        this.name=name;
        this.description=description;
        this.reward=reward;
        this.home = home;
        this.icon=icon;
        this.dueDate=dueDate;
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
}
