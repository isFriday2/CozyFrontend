package com.maddev.coozy;

// import jBCrypt password hashing library
// Installed through Maven - required in modeule-info.java
import org.mindrot.jbcrypt.BCrypt;

public class User {
    private int id;
    private String username;
    private String email;
    private String nickname;
    private String home;
    private String password;

    // Constructor including all fields
    public User(int id, String username, String email, String nickname, String home, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.home = home;
        this.password = password;
    }

    // Constructor without id - for auto incrementing
    public User(String username, String email, String nickname, String home, String password) {
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.home = home;
        this.password = password;
    }

    // Create getter functions
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getHome() {
        return home;
    }

    public String getPassword() {
        return password;
    }

    // Create setter functions
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
