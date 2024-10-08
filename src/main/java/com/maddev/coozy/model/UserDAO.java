package com.maddev.coozy.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    // Define DB connection
    private Connection connection;

    // Create DB connection instance
    public UserDAO() {
        connection = DatabaseConnection.getInstance();
        createTable();
    }

    // Creates DB connection for unit tests
    public UserDAO(boolean test){
        if(test){
            connection = TestDatabaseConnection.getInstance();
            createTable();
        }else{
            connection = DatabaseConnection.getInstance();
            createTable();
        }
    }

    //
    // Functions for CRUD with database
    //

    // Create table in db
    // Will not create table if already exists
    public void createTable() {
        try {
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS Users ("
                            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "username VARCHAR,"
                            + "email VARCHAR NOT NULL, "
                            + "nickname VARCHAR, "
                            + "home VARCHAR, "
                            + "password VARCHAR NOT NULL"
                            + ")"
            );
            System.out.println("table Users created");
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    // Erases all users in the db, use it only with the test db
    public void resetTable(){
        try {
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "DELETE FROM users"
            );
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    // Insert new user into db
    public boolean insert(User user) {
        try {
            PreparedStatement insertUser = connection.prepareStatement(
                    "INSERT INTO Users (username, email, nickname, home, password) VALUES (?, ?, ?, ?, ?)"
            );
            insertUser.setString(1, user.getUsername());
            insertUser.setString(2, user.getEmail());
            insertUser.setString(3, user.getNickname());
            insertUser.setString(4, user.getHome());
            insertUser.setString(5, user.getPassword());
            insertUser.execute();
            return true;
        } catch (SQLException ex) {
            System.err.println(ex);
            return false;
        }
    }

    // Update details of existing user in db
    public void update(User user) {
        try {
            PreparedStatement updateUser = connection.prepareStatement(
                    "UPDATE users SET username = ?, email = ?, nickname = ?, home = ?, password = ? WHERE id = ?"
            );
            updateUser.setString(1, user.getUsername());
            updateUser.setString(2, user.getEmail());
            updateUser.setString(3, user.getNickname());
            updateUser.setString(4, user.getHome());
            updateUser.setString(5, user.getPassword());
            updateUser.setString(6, user.getUsername());
            updateUser.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    // Delete existing user in db
    public void delete(int id) {
        try {
            PreparedStatement deleteUser = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            deleteUser.setInt(1, id);
            deleteUser.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    //
    //DB getters
    //

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            Statement getAll = connection.createStatement();
            ResultSet rs = getAll.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                users.add(
                        new User(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("email"),
                                rs.getString("nickname"),
                                rs.getString("home"),
                                rs.getString("password")
                        )
                );
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return users;
    }

    public List<User> getAllByHome(String home) {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement getByHome = connection.prepareStatement("SELECT * FROM users WHERE home = ?");
            getByHome.setString(1, home);
            ResultSet rs = getByHome.executeQuery();
            while (rs.next()) {
                users.add(
                        new User(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("email"),
                                rs.getString("nickname"),
                                rs.getString("home"),
                                rs.getString("password")
                        )
                );
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return users;
    }

    // Get user by ID
    public User getById(int id) {
        try {
            PreparedStatement getUser = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            getUser.setInt(1, id);
            ResultSet rs = getUser.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("nickname"),
                        rs.getString("home"),
                        rs.getString("password")
                );
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }

    // Get user by username
    public User getByUsername(String username) {
        try {
            PreparedStatement getUser = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            getUser.setString(1, username);
            ResultSet rs = getUser.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("nickname"),
                        rs.getString("home"),
                        rs.getString("password")
                );
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }

    //
    // Password Validation with SHA-256
    //

    // Password Validation - using hashing method for security
    public boolean validatePassword(String username, String inputPassword) {
        User user = getByUsername(username); // Fetch user by username

        if (user != null) {
            // Hash the input password using SHA-256
            String hashedInputPassword = User.hashPassword(inputPassword);

            // Compare the hashed input password with the stored hashed password
            return hashedInputPassword.equals(user.getPassword());
        }

        // Return false if the user is not found
        return false;
    }


    // Terminate connection function
    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
}