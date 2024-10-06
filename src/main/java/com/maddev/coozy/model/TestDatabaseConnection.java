package com.maddev.coozy.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDatabaseConnection {
    private static Connection instance = null;
    private static final String URL = "jdbc:sqlite:testdatabase.db";

    private TestDatabaseConnection() {
        // Private constructor to prevent instantiation
    }

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