package com.maddev.coozy.controller.leaderboard;

import com.maddev.coozy.controller.chore.ChoreViewController;
import com.maddev.coozy.HelloApplication;
import com.maddev.coozy.model.Chore;
import com.maddev.coozy.model.ChoreDAO;
import com.maddev.coozy.model.User;
import com.maddev.coozy.model.UserDAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testfx.util.NodeQueryUtils.hasText;

@ExtendWith(ApplicationExtension.class)
class LeaderboardTest {
    private User testUser;

    // function to set the test-database with the data needed for testing
    private void setTestDB(){
        UserDAO userDAO = new UserDAO(true);
        ChoreDAO choreDAO = new ChoreDAO(true);

        // creates tables if they don't exist
        userDAO.createTable();
        choreDAO.createTable();

        // reset tables
        choreDAO.resetTable();
        userDAO.resetTable();

        // insert users used for testing
        User user1=new User("testName", "test@email.com", "testAccount", "123", "TestPass");
        userDAO.insert(user1);
        testUser=userDAO.getByUsername("testName");
        int user1Id=userDAO.getByUsername("testName").getId();

        User user2=new User("testName2", "test2@email.com", "test2Account", "123", "TestPass");
        userDAO.insert(user2);
        int user2Id=userDAO.getByUsername("testName2").getId();

        // insert chores used for testing
        LocalDate dueDate =LocalDate.of(2024,10,10);
        Chore chore1 = new Chore(user1Id,"test1","test1",15,"123","ANCHOR",dueDate,true);
        Chore chore2 = new Chore(user2Id,"test2","test2",20,"123","ANCHOR",dueDate,true);
        Chore chore3 = new Chore(user1Id,"test3","test3",15,"123","ANCHOR",dueDate,false);
        choreDAO.insert(chore1);
        choreDAO.insert(chore2);
        choreDAO.insert(chore3);
    }

    @Start
    public void start (Stage stage) throws Exception {
        setTestDB();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/maddev/coozy/home-page.fxml"));
        Parent root = fxmlLoader.load();
        ChoreViewController controller=fxmlLoader.getController();
        controller.setTesting();
        controller.setUser(testUser);
        controller.init();

        Scene scene = new Scene(root, 1133, 744);
        stage.setTitle("Home page");
        stage.setScene(scene);
        stage.show();
        stage.toFront();
    }

    // checks that the leaderboard has two users and with the right scores
    @Test
    public void usersCountTest(FxRobot robot){
        VBox container = robot.lookup("#leaderboardContent").queryAs(VBox.class);
        assertEquals(2,container.getChildren().size());
        Node node = robot.lookup("15 points").query();
        assertNotNull(node);
        node = robot.lookup("20 points").query();
        assertNotNull(node);
    }

    // mark task as completed and make sure that the leaderboard is updated
    @Test
    public void updateLeaderboard(FxRobot robot){
        VBox box = robot.lookup("#choresLayout").queryAs(VBox.class);
        robot.clickOn(box.getChildren().get(1));
        VBox container = robot.lookup("#leaderboardContent").queryAs(VBox.class);
        assertEquals(2,container.getChildren().size());
        Node node = robot.lookup("30 points").query();
        assertNotNull(node);
        node = robot.lookup("20 points").query();
        assertNotNull(node);
    }
}