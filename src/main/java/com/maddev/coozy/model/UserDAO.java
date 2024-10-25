package com.maddev.coozy.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for User entities.
 * This class handles all database operations related to Users,
 * including CRUD operations and various retrieval methods.
 */
public class UserDAO {
    private Connection connection;

    /**
     * Constructs a UserDAO and establishes a database connection.
     * It also ensures that the Users table exists in the database.
     */
    public UserDAO() {
        connection = DatabaseConnection.getInstance();
        createTable();
    }

    /**
     * Constructs a UserDAO with an option for testing purposes.
     * @param test If true, uses a test database connection; otherwise, uses the standard connection.
     */
    public UserDAO(boolean test) {
        if (test) {
            connection = TestDatabaseConnection.getInstance();
        } else {
            connection = DatabaseConnection.getInstance();
        }
        createTable();
    }

    /**
     * Creates the Users table in the database if it doesn't already exist.
     */
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

    /**
     * Resets the Users table by deleting all entries.
     * This method should only be used with the test database.
     */
    public void resetTable() {
        try {
            Statement createTable = connection.createStatement();
            createTable.execute("DELETE FROM users");
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    /**
     * Inserts a new user into the database.
     * @param user The User object to be inserted.
     * @return true if the insertion was successful, false otherwise.
     */
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

    /**
     * Updates an existing user in the database.
     * @param user The User object with updated information.
     */
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
            updateUser.setInt(6, user.getId());
            updateUser.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    /**
     * Deletes a user from the database.
     * @param id The ID of the user to be deleted.
     */
    public void delete(int id) {
        try {
            PreparedStatement deleteUser = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            deleteUser.setInt(1, id);
            deleteUser.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    /**
     * Retrieves all users from the database.
     * @return A List of all User objects in the database.
     */
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            Statement getAll = connection.createStatement();
            ResultSet rs = getAll.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("nickname"),
                        rs.getString("home"),
                        rs.getString("password")
                ));
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return users;
    }

    /**
     * Retrieves all users from a specific home.
     * @param home The home identifier to filter users.
     * @return A List of User objects belonging to the specified home.
     */
    public List<User> getAllByHome(String home) {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement getByHome = connection.prepareStatement("SELECT * FROM users WHERE home = ?");
            getByHome.setString(1, home);
            ResultSet rs = getByHome.executeQuery();
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("nickname"),
                        rs.getString("home"),
                        rs.getString("password")
                ));
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return users;
    }

    /**
     * Retrieves a user by their ID.
     * @param id The ID of the user to retrieve.
     * @return The User object if found, null otherwise.
     */
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

    /**
     * Retrieves a user by their username.
     * @param username The username of the user to retrieve.
     * @return The User object if found, null otherwise.
     */
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

    /**
     * Validates a user's password.
     * @param username The username of the user.
     * @param inputPassword The password to validate.
     * @return true if the password is valid, false otherwise.
     */
    public boolean validatePassword(String username, String inputPassword) {
        User user = getByUsername(username);
        if (user != null) {
            String hashedInputPassword = User.hashPassword(inputPassword);
            return hashedInputPassword.equals(user.getPassword());
        }
        return false;
    }

    /**
     * Closes the database connection.
     */
    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
}