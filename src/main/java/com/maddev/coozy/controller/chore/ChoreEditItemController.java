package com.maddev.coozy.controller.chore;

import com.maddev.coozy.model.Chore;
import com.maddev.coozy.model.ChoreDAO;
import com.maddev.coozy.controller.chore.ChoreViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class ChoreEditItemController {


    // Create DB DAO connections
    private ChoreDAO choreDAO = new ChoreDAO();

    private Chore currentChore;
    private boolean testing=false;

    // run before init to use the test db
    public void setTesting(){
        testing=true;
        choreDAO=new ChoreDAO(true);
    }
    // Set the current chore for editing
    public void setChore(Chore chore) {
        this.currentChore = chore;
        this.choreTitle.setText(chore.getName());
    }

    @FXML
    private Label choreDueDate;

    @FXML
    private Label choreTitle;

    public void setData(Chore chore){
        choreTitle.setText(chore.getName());
        if(chore.isCompleted()){
            choreDueDate.setText("Chore Completed");
        }else{
            LocalDate dateNow=LocalDate.now();
            long daysDue=DAYS.between(dateNow, chore.getDueDate());
            choreDueDate.setText("Due in "+daysDue+" days");
        }
    }

    @FXML
    private void handleDeleteIcon() {
        boolean confirmed = confirmDelete();
        if (confirmed) {
            try {
                // Call delete method from ChoreDAO
                choreDAO.delete(currentChore.getId());
                System.out.println("Chore deleted successfully");

            } catch (Exception e) {
                System.err.println("Failed to delete chore: " + e.getMessage());
                // Show an alert if the delete fails
                showAlert("Error", "Failed to delete the chore. Please try again.");
            }
        }
    }

    // Boolean confirmation to confirm user's deletion request
    // Implement below Alert function
    private boolean confirmDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to delete this chore?");
        alert.setContentText("This action cannot be undone.");
        return alert.showAndWait().orElse(null) == ButtonType.OK;
    }

    // Function to create Alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
