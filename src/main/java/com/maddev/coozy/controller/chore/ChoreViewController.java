package com.maddev.coozy.controller.chore;

import com.maddev.coozy.HelloApplication;
import com.maddev.coozy.controller.ResidentController;
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

/**
 * Controller class for the main chore view.
 * Manages the display of the leaderboard, resident list, and chore list.
 */
public class ChoreViewController {
    private ChoreDAO choreDAO;
    private boolean pending=false;
    private boolean testing=false;
    private User user;

    /**
     * Constructs a new ChoreViewController.
     * Initializes the ChoreDAO for database operations.
     */
    public ChoreViewController() {
        choreDAO = new ChoreDAO();
    }

    /**
     * Sets the controller to testing mode.
     * In testing mode, it uses a test database for ChoreDAO.
     */
    // run before init to use the test db
    public void setTesting(){
        testing=true;
        choreDAO=new ChoreDAO(true);
    }

    /**
     * Sets the current user for the controller.
     * @param user The current User object.
     */

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
    @FXML
    private VBox residentDisplay;


    /**
     * Initializes the view by loading the leaderboard, resident list, and chore list.
     * This method should be called after setting the user for the controller.
     */
    // always call this function to load page but after setting a user for the controller
    public void init() {
        System.out.println("Creating leaderboard");
        leaderboardDisplay.getChildren().clear();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/maddev/coozy/leaderboard.fxml"));
        try {
            VBox leaderBoard = fxmlLoader.load();
            LeaderboardController controller = fxmlLoader.getController();
            if(testing) controller.setTesting();
            controller.setUser(user);
            controller.init();
            leaderboardDisplay.getChildren().add(leaderBoard);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Creating resident list");
        residentDisplay.getChildren().clear();
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/maddev/coozy/resident.fxml"));
        try {
            VBox list = fxmlLoader.load();
            ResidentController controller = fxmlLoader.getController();
            if(testing) controller.setTesting();
            controller.setUser(user);
            controller.init();
            residentDisplay.getChildren().add(list);
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

    /**
     * Retrieves chores for the current user.
     * @param pending If true, retrieves only pending chores; otherwise, retrieves all chores.
     * @return A list of Chore objects.
     */
    private List<Chore> getChores(boolean pending) {
        if(!pending) return choreDAO.getAllByUser(user.getId());
        else return choreDAO.getAllPendingByUser(user.getId());
    }
    /**
     * Toggles between viewing all chores and pending chores only.
     */
    @FXML
    public void onViewPending(){
        pending=!pending;
        init();
    }
    /**
     * Handles the add chore button click.
     * Opens the add chore view.
     * @throws IOException If there's an error loading the add chore view.
     */
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

    /**
     * Handles the log out button click.
     * Returns to the login view.
     * @throws IOException If there's an error loading the login view.
     */
    @FXML
    public void onLogOut() throws IOException{
        Stage stage = (Stage) addChore.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
    }
}