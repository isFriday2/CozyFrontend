package com.maddev.coozy.controller.chore;

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

@ExtendWith(ApplicationExtension.class)
class ChoreControllersTest {
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

        // insert chores used for testing
        LocalDate dueDate =LocalDate.of(2024,10,10);
        Chore chore1 = new Chore(user1Id,"test1","test1",10,"123","ANCHOR",dueDate,false);
        Chore chore2 = new Chore(user1Id,"test2","test2",20,"123","ANCHOR",dueDate,false);
        choreDAO.insert(chore1);
        choreDAO.insert(chore2);
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

    // checks that the right date is displayed
    @Test
    public void dateTest(FxRobot robot){
        Label date = robot.lookup("#date").queryAs(Label.class);
        assertEquals(LocalDate.now().toString(),date.getText());
    }

    // checks that the amount of chores displayed is correct
    @Test
    public void viewCountTest(FxRobot robot){
        VBox box = robot.lookup("#choresLayout").queryAs(VBox.class);
        assertEquals(2,box.getChildren().size());
    }

    // adds a new chore and checks that it is added
    @Test
    public void addChoreTest(FxRobot robot){
        Button addChore = robot.lookup("#addChore").queryAs(Button.class);
        assertNotNull(addChore);
        robot.clickOn("#addChore");

        TextArea description =robot.lookup("#descriptionTextArea").queryAs(TextArea.class);
        assertNotNull(description);
        robot.clickOn("#descriptionTextArea");
        robot.write("test3");

        TextField nameTextField =robot.lookup("#nameTextField").queryAs(TextField.class);
        assertNotNull(nameTextField);
        robot.clickOn("#nameTextField");
        robot.write("test3");

        TextField pointsTextField =robot.lookup("#pointsTextField").queryAs(TextField.class);
        assertNotNull(pointsTextField);
        robot.clickOn("#pointsTextField");
        robot.write("20");

        ChoiceBox<String> chooseUsername =robot.lookup("#chooseUsername").query();
        assertNotNull(chooseUsername);
        robot.clickOn("#chooseUsername");
        robot.clickOn(LabeledMatchers.hasText("testName"));

        MenuButton iconMenuButton =robot.lookup("#iconMenuButton").query();
        assertNotNull(iconMenuButton);
        robot.clickOn("#iconMenuButton");
        robot.clickOn("#trashIcon");

        DatePicker dueDatePicker =robot.lookup("#dueDatePicker").query();
        assertNotNull(dueDatePicker);
        robot.clickOn("#dueDatePicker");
        robot.write("10/2/2024");

        robot.clickOn("#addButton");
        Label choreTitle = robot.lookup(LabeledMatchers.hasText("test3")).query();
        assertNotNull(choreTitle);
    }

    // marks one chore as completed
    @Test
    public void completedTest(FxRobot robot){
        VBox box = robot.lookup("#choresLayout").queryAs(VBox.class);
        robot.clickOn(box.getChildren().get(0));
        Label choreDueDate = robot.lookup(LabeledMatchers.hasText("Chore Completed")).query();
        assertNotNull(choreDueDate);
        robot.clickOn(box.getChildren().get(0));
    }

    // check the further options of a chore
    @Test
    public void moreOptionsTest(FxRobot robot){
        VBox box = robot.lookup("#choresLayout").queryAs(VBox.class);
        robot.rightClickOn(box.getChildren().get(0));
        Node trashImage = robot.lookup("#deleteIcon").query();
        assertNotNull(trashImage);
        robot.clickOn(box.getChildren().get(0));
    }
}