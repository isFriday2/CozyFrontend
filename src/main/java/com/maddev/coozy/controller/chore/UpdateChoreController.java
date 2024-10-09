package com.maddev.coozy.controller.chore;

import com.maddev.coozy.AlertUtil;
import com.maddev.coozy.controller.RegisterController;
import com.maddev.coozy.model.Chore;
import com.maddev.coozy.model.ChoreDAO;
import com.maddev.coozy.model.UserDAO;
import com.maddev.coozy.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class UpdateChoreController {
    private ChoreDAO choreDAO = new ChoreDAO();
    private UserDAO userDAO;

    private Chore currentChore;
    private User currentUser;

    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private TextField nameTextField;
    @FXML
    private ChoiceBox<String> Username;
    @FXML
    private TextField pointsTextField;
    @FXML
    private DatePicker dueDatePicker;
    @FXML
    private Button updateButton;
    @FXML
    private Button cancelButton;

    public void setChoiceUsernames(){
        Username.setValue("Assign chore");
        List<User> possibleUsers=userDAO.getAllByHome(currentUser.getHome());
        for(User possibleUser: possibleUsers){
            Username.getItems().add(possibleUser.getUsername());
        }
    }

    @FXML
    private void handleUpdateButton() {
        try {
            String description = descriptionTextArea.getText();
            String title = nameTextField.getText();
            String username = Username.getValue();
            String points = pointsTextField.getText();
            LocalDate date = dueDatePicker.getValue();

            currentChore.setDescription(description);
            currentChore.setName(title);
            // currentUser.setUsername(username);
            currentChore.setReward(Integer.parseInt(points));
            currentChore.setDueDate(date);

            choreDAO.update(currentChore);
            AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Update Successful", "New Chore details have been saved.");


            // Redirect after alert has been closed
            redirectToHome();
        } catch (Exception e) {
            // Handle any other exceptions that may occur
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Update Error", "Could not update chore at this time.");

            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancelButton() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        redirectToHome();
    }

    public void setChore(Chore chore) {
        this.currentChore = chore;
        populateFields();
    }

    private void populateFields() {
        descriptionTextArea.setText(currentChore.getDescription());
        nameTextField.setText(currentChore.getName());
        // Username.setValue(currentUser.getUsername());
        pointsTextField.setText(String.valueOf(currentChore.getReward())); // Convert to String
        dueDatePicker.setValue(currentChore.getDueDate());
    }

    private void redirectToHome() {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
    }


}
