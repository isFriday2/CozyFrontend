package com.maddev.coozy.controller.chore;

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
            showAlert(Alert.AlertType.INFORMATION, "Update Successful!", "New chore details have now been saved");

            // Redirect after alert has been closed
            redirectToHome();
        } catch (Exception e) {
            // Handle any other exceptions that may occur
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while updating the chore.");
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

    public void showAlert(javafx.scene.control.Alert.AlertType alertType, String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();  // This makes it modal and waits for user interaction
    }

    private void redirectToHome() {
        try {
            // Load login.fxml
            Parent root = FXMLLoader.load(getClass().getResource("/com/maddev/coozy/home-page.fxml"));

            // Create a new stage for the login screen
            Stage homeStage = new Stage();
            homeStage.setTitle("Home Page");
            homeStage.setScene(new Scene(root, 600, 400)); // Set the size if needed
            homeStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load login screen.");
        }
    }


}
