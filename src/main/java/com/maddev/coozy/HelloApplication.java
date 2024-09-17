package com.maddev.coozy;

import com.maddev.coozy.controller.chore.ChoreViewController;
import com.maddev.coozy.model.DatabaseConnection;
import com.maddev.coozy.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Test database connection
        testDatabaseConnection();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/maddev/coozy/home-page.fxml"));
        Parent root = fxmlLoader.load();
        ChoreViewController controller = fxmlLoader.getController();

        User user = new User(1, "martin", "martin@gmail", "martinius", "1", "1234");

        controller.setUser(user);
        controller.init();

        Scene scene = new Scene(root, 1133, 744);
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