package com.maddev.coozy.controller.chore;

import com.maddev.coozy.HelloApplication;
import com.maddev.coozy.controller.leaderboard.LeaderboardController;
import com.maddev.coozy.model.Chore;
import com.maddev.coozy.model.User;
import com.maddev.coozy.model.ChoreDAO;
import com.maddev.coozy.model.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class ChoreViewController {
    private ChoreDAO choreDAO;
    private boolean pending=false;
    private boolean testing=false;

    public ChoreViewController() {
        choreDAO = new ChoreDAO();
    }

    // run before init to use the test db
    public void setTesting(){
        testing=true;
        choreDAO=new ChoreDAO(true);
    }

    private User user;
    public void setUser(User user){
        this.user = user;
    }

    @FXML
    private VBox choresLayout;
    @FXML
    private Button addChore;
    @FXML
    private Label date;
    @FXML
    private VBox leaderboardDisplay;



    // always call this function to load page but after setting a user for the controller
    public void init() {
        System.out.println("Creating leaderboard");
        leaderboardDisplay.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/maddev/coozy/leaderboard.fxml"));
        try {
            VBox leaderBoard = fxmlLoader.load();
            LeaderboardController controller = fxmlLoader.getController();
//            todo set tests
//            if(testing) controller.setTesting();
            controller.setUser(user);
            controller.init();
            leaderboardDisplay.getChildren().add(leaderBoard);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Creating chores list");
        choresLayout.getChildren().clear();
        date.setText(LocalDate.now().toString());
        List<Chore> chores = new ArrayList<>(getChores(pending));
        for (Chore chore : chores) {
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/maddev/coozy/chore-anchor.fxml"));
            try {
                AnchorPane anchorPane = fxmlLoader.load();
                ChoreAnchorController controller = fxmlLoader.getController();
                if(testing) controller.setTesting();
                controller.setChore(chore, this);
                controller.setData();
                choresLayout.getChildren().add(anchorPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private List<Chore> getChores(boolean pending) {
        if(!pending) return choreDAO.getAllByUser(user.getId());
        else return choreDAO.getAllPendingByUser(user.getId());
    }

    @FXML
    public void onViewPending(){
        pending=!pending;
        init();
    }

    @FXML
    public void onAddChore() throws IOException {
        Stage stage = (Stage) addChore.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("add-chore.fxml"));
        Parent root = fxmlLoader.load();
        AddChoreController controller = fxmlLoader.getController();
        if(testing) controller.setTesting();
        controller.setUser(user);
        controller.setChoiceUsernames();
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
    }

    @FXML
    public void onLogOut() throws IOException{
        Stage stage = (Stage) addChore.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
    }
}