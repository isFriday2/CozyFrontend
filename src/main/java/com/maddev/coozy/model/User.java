package com.maddev.coozy;

// import SHA-256 security libraries
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private int id;
    private String username;
    private String email;
    private String nickname;
    private String home;
    private String password;

    // Constructor including all fields
    // Remove and make username PK
    public User(int id, String username, String email, String nickname, String home, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.home = home;
        this.password = hashPassword(password);
    }

    // Constructor without id - for auto incrementing
    public User(String username, String email, String nickname, String home, String password) {
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.home = home;
        this.password = hashPassword(password);
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
        this.password = hashPassword(password);
    }

    // Create SHA-256 hashing function
    // For password data security
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); //Use Inbuilt Java SHA-256
            byte[] hash = md.digest(password.getBytes()); //Save hash bytes in array
            StringBuilder hexString = new StringBuilder();
            // Itterate all bytes and convert
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
