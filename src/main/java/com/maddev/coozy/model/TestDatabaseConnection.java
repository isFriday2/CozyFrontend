package com.maddev.coozy.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages test database connections using a singleton pattern.
 * This class provides a single point of access to the SQLite test database connection.
 * It is specifically designed for use in test environments.
 */
public class TestDatabaseConnection {
    private static Connection instance = null;
    private static final String URL = "jdbc:sqlite:testdatabase.db";

    /**
     * Private constructor to prevent instantiation.
     * This enforces the singleton pattern.
     */
    private TestDatabaseConnection() {
        // Private constructor to prevent instantiation
    }

    /**
     * Returns a singleton instance of the test database connection.
     * If the connection doesn't exist, it creates a new one.
     *
     * @return The test database Connection instance
     */
    public static Connection getInstance() {
        if (instance == null) {
            try {
                // Explicitly load the SQLite JDBC driver
                Class.forName("org.sqlite.JDBC");

                // Create the connection
                instance = DriverManager.getConnection(URL);
                System.out.println("Test Database connection established successfully.");
            } catch (ClassNotFoundException e) {
                System.err.println("SQLite JDBC driver not found: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("Error establishing database connection: " + e.getMessage());
            }
        }
        return instance;
    }

    /**
     * Closes the test database connection if it exists.
     * After closing, the connection instance is set to null.
     */
    public static void closeConnection() {
        if (instance != null) {
            try {
                instance.close();
                instance = null;
                System.out.println("Test Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing Test database connection: " + e.getMessage());
            }
        }
    }
}