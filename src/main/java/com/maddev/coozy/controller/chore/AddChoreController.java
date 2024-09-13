package com.maddev.coozy.controller.chore;

import com.maddev.coozy.model.Chore;
import com.maddev.coozy.model.ChoreDAO;
import com.maddev.coozy.model.User;
import com.maddev.coozy.model.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class AddChoreController {
    private UserDAO userDAO;
    private ChoreDAO choreDAO;
    private String icon;


    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField pointsTextField;
    @FXML
    private DatePicker dueDatePicker;


    public AddChoreController() {
         userDAO= new UserDAO();
         choreDAO= new ChoreDAO();
    }

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

    @FXML
    private void onAddChore() {
        final String username=usernameTextField.getText();
        User assignedUser=userDAO.getByUsername(username);
        //todo two possible errors, user not found in data base or user not in the same home,

        final int userId=assignedUser.getId();
        final String name = nameTextField.getText();
        final String description = descriptionTextArea.getText();
        final int reward = Integer.parseInt(pointsTextField.getText());
        final String home = assignedUser.getHome();
        final LocalDate dueDate = dueDatePicker.getValue();


        Chore newChore = new Chore(userId, name, description, reward, home, icon, dueDate);
        choreDAO.insert(newChore);
    }

    @FXML
    private void onCancel() {

    }
}
