package com.maddev.coozy;

import com.maddev.coozy.model.DatabaseConnection;
import com.maddev.coozy.model.UserDAO;

import java.sql.Connection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    // Innitiate DB Connection - Will create DB table if null
    Connection connection = DatabaseConnection.getInstance();


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Coozy");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

        // Create the table
        UserDAO userDAO = new UserDAO();
        userDAO.createTable();

        userDAO.close();

    }
}
