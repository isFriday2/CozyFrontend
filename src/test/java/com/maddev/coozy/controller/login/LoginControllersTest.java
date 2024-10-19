package com.maddev.coozy.controller.chore;

import com.maddev.coozy.HelloApplication;
import com.maddev.coozy.controller.LoginController;
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

@ExtendWith(ApplicationExtension.class)
class LoginControllersTest {

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
        User user1=new User("testName", "test@email.com", "testAccount", "123", User.hashPassword("TestPass"));
        userDAO.insert(user1);
        User testUser=userDAO.getByUsername("testName");
        int user1Id=userDAO.getByUsername("testName").getId();
    }

    @Start
    public void start (Stage stage) throws Exception {
        setTestDB();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/maddev/coozy/login.fxml"));
        Parent root = fxmlLoader.load();
        LoginController controller = fxmlLoader.getController();
        controller.setTesting();
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
        stage.toFront();
    }

    // logs in with the existing user and logs out
    @Test
    public void loginTest(FxRobot robot){
        robot.clickOn("#usernameField");
        robot.write("testName");
        robot.clickOn("#passwordField");
        robot.write("TestPass");
        robot.clickOn("#loginButton");

        Hyperlink logout = robot.lookup("#logoutLink").queryAs(Hyperlink.class);
        assertNotNull(logout);
        robot.clickOn(logout);
        Button login = robot.lookup("#loginButton").queryAs(Button.class);
        assertNotNull(login);
    }

    // logs in with the existing user
    @Test
    public void registerTest(FxRobot robot){
        // creates the new user
        robot.clickOn("#registerLink");
        robot.clickOn("#usernameField");
        robot.write("testName2");
        robot.clickOn("#nicknameField");
        robot.write("testName2");
        robot.clickOn("#emailField");
        robot.write("test2@email.com");
        robot.clickOn("#homeField");
        robot.write("123");
        robot.clickOn("#passwordField");
        robot.write("testPass2");
        robot.clickOn("#registerButton");
        robot.clickOn("OK");

        // logs in as the new user
        robot.clickOn("#usernameField");
        robot.write("testName2");
        robot.clickOn("#passwordField");
        robot.write("testPass2");
        robot.clickOn("#loginButton");

        Hyperlink logout = robot.lookup("#logoutLink").queryAs(Hyperlink.class);
        assertNotNull(logout);
    }

}