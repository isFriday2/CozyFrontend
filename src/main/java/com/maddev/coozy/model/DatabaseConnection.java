package com.maddev.coozy.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages database connections using a singleton pattern.
 * This class provides a single point of access to the SQLite database connection.
 */
public class DatabaseConnection {
    private static Connection instance = null;
    private static final String URL = "jdbc:sqlite:database.db";

    /**
     * Private constructor to prevent instantiation.
     * This enforces the singleton pattern.
     */
    private DatabaseConnection() {
        // Private constructor to prevent instantiation
    }

    /**
     * Returns a singleton instance of the database connection.
     * If the connection doesn't exist, it creates a new one.
     *
     * @return The database Connection instance
     */
    public static Connection getInstance() {
        if (instance == null) {
            try {
                // Explicitly load the SQLite JDBC driver
                Class.forName("org.sqlite.JDBC");

                // Create the connection
                instance = DriverManager.getConnection(URL);
                System.out.println("Database connection established successfully.");
            } catch (ClassNotFoundException e) {
                System.err.println("SQLite JDBC driver not found: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("Error establishing database connection: " + e.getMessage());
            }
        }
        return instance;
    }

    /**
     * Closes the database connection if it exists.
     * After closing, the connection instance is set to null.
     */
    public static void closeConnection() {
        if (instance != null) {
            try {
                instance.close();
                instance = null;
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }
}