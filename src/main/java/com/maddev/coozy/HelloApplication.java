package com.maddev.coozy;

import com.maddev.coozy.model.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Test database connection
        testDatabaseConnection();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("add-chore.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Coozy");
        stage.setScene(scene);
        stage.show();
    }

    private void testDatabaseConnection() {
        try {
            Connection conn = DatabaseConnection.getInstance();
            if (conn != null) {
                System.out.println("Database connection successful!");
            } else {
                System.out.println("Failed to establish database connection.");
            }
        } catch (Exception e) {
            System.err.println("Error testing database connection:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}