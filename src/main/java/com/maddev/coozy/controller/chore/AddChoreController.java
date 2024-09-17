package com.maddev.coozy.controller.chore;

import com.maddev.coozy.HelloApplication;
import com.maddev.coozy.model.Chore;
import com.maddev.coozy.model.ChoreDAO;
import com.maddev.coozy.model.User;
import com.maddev.coozy.model.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class AddChoreController {
    private UserDAO userDAO;
    private ChoreDAO choreDAO;

    public AddChoreController() {
        userDAO= new UserDAO();
        choreDAO= new ChoreDAO();
    }

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    private String icon;

    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private TextField nameTextField;
    @FXML
    private ChoiceBox<String> chooseUsername;
    @FXML
    private TextField pointsTextField;
    @FXML
    private DatePicker dueDatePicker;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;

    @FXML
    private void setToTrash(){
        icon="TRASH";
    }
    @FXML
    private void setToHome(){
        icon="HOME";
    }
    @FXML
    private void setToShoppingCart(){
        icon="SHOPPING_CART";
    }
    @FXML
    private void setToPaw(){
        icon="PAW";
    }
    @FXML
    private void setToCutlery(){
        icon="CUTLERY";
    }
    @FXML
    private void setToShower(){
        icon="SHOWER";
    }

    public void setChoiceUsernames(){
        chooseUsername.setValue("Assign chore");
        List<User> possibleUsers=userDAO.getAllByHome(user.getHome());
        for(User possibleUser: possibleUsers){
            chooseUsername.getItems().add(possibleUser.getUsername());
        }
    }

    @FXML
    private void onAddChore() throws IOException {
        final String username=chooseUsername.getValue();
        User assignedUser=userDAO.getByUsername(username);
        //todo two possible errors, user not found in data base or user not in the same home,

        final int userId=assignedUser.getId();
        final String name = nameTextField.getText();
        final String description = descriptionTextArea.getText();
        final int reward = Integer.parseInt(pointsTextField.getText());
        final String home = assignedUser.getHome();
        final LocalDate dueDate = dueDatePicker.getValue();

        Chore newChore = new Chore(userId, name, description, reward, home, icon, dueDate, false);
        choreDAO.insert(newChore);

        Stage stage = (Stage) addButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home-page.fxml"));
        Parent root = fxmlLoader.load();
        ChoreViewController controller=fxmlLoader.getController();
        controller.setUser(user);
        controller.init();
        Scene scene = new Scene(root,1133, 744);
        stage.setScene(scene);
    }

    @FXML
    private void onCancel() throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home-page.fxml"));
        Parent root = fxmlLoader.load();
        ChoreViewController controller=fxmlLoader.getController();
        controller.setUser(user);
        controller.init();
        Scene scene = new Scene(root,1133, 744);
        stage.setScene(scene);
    }
}
