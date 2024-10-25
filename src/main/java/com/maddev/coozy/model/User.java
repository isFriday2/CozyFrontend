package com.maddev.coozy.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Represents a user in the system.
 * This class encapsulates user information and provides methods for managing user data.
 */
public class User {
    private int id;
    private String username;
    private String email;
    private String nickname;
    private String home;
    private String password;

    /**
     * Constructs a new User with all fields specified.
     * @param id The user's unique identifier
     * @param username The user's username
     * @param email The user's email address
     * @param nickname The user's nickname
     * @param home The user's home identifier
     * @param password The user's password (should be hashed before storage)
     */
    public User(int id, String username, String email, String nickname, String home, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.home = home;
        this.password = password;
    }

    /**
     * Constructs a new User without specifying the id.
     * This constructor is useful when the id is auto-incremented by the database.
     * @param username The user's username
     * @param email The user's email address
     * @param nickname The user's nickname
     * @param home The user's home identifier
     * @param password The user's password (should be hashed before storage)
     */
    public User(String username, String email, String nickname, String home, String password) {
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.home = home;
        this.password = password;
    }

    /**
     * Gets the user's id.
     * @return The user's unique identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the user's username.
     * @return The user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the user's email address.
     * @return The user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the user's nickname.
     * @return The user's nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Gets the user's home identifier.
     * @return The user's home identifier
     */
    public String getHome() {
        return home;
    }

    /**
     * Gets the user's password (hashed).
     * @return The user's hashed password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's username.
     * @param username The new username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the user's email address.
     * @param email The new email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the user's nickname.
     * @param nickname The new nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Sets the user's home identifier.
     * @param home The new home identifier to set
     */
    public void setHome(String home) {
        this.home = home;
    }

    /**
     * Sets the user's password.
     * This method hashes the provided password before storing it.
     * @param password The new password to set (will be hashed)
     */
    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    /**
     * Hashes a password using SHA-256 algorithm.
     * @param password The password to hash
     * @return The hashed password as a hexadecimal string
     * @throws RuntimeException if the SHA-256 algorithm is not available
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
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