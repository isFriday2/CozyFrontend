package com.maddev.coozy;

import com.maddev.coozy.controller.chore.ChoreViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.maddev.coozy.model.User;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        User user= new User(1,"martin","martin@gmail", "martinius","1","1234");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/maddev/coozy/home-page.fxml"));
        Parent root = fxmlLoader.load();
        ChoreViewController controller = fxmlLoader.getController();
        controller.setUser(user);
        controller.init();

        Scene scene = new Scene(root, 1133, 744);
        stage.setTitle("Coozy");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}