package com.maddev.coozy.controller.chore;

import com.maddev.coozy.model.Chore;
import com.maddev.coozy.model.ChoreDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Controller class for editing and deleting chore items.
 * Handles the display of chore details and provides functionality to delete chores.
 */
public class ChoreEditItemController {

    private ChoreDAO choreDAO = new ChoreDAO();
    private Chore currentChore;
    private ChoreViewController parentController;
    private boolean testing = false;

    @FXML
    private Label choreDueDate;

    @FXML
    private Label choreTitle;

    /**
     * Sets the controller to testing mode.
     * In testing mode, it uses a test database for ChoreDAO.
     */
    public void setTesting() {
        testing = true;
        choreDAO = new ChoreDAO(true);
    }

    /**
     * Sets the data for the chore edit item.
     * @param chore The Chore object to be displayed and edited.
     * @param parentController The parent ChoreViewController.
     */
    public void setData(Chore chore, ChoreViewController parentController) {
        this.parentController = parentController;
        currentChore = chore;
        choreTitle.setText(chore.getName());
        updateDueDateLabel(chore);
    }

    /**
     * Updates the due date label based on the chore's completion status and due date.
     * @param chore The Chore object whose due date is to be displayed.
     */
    private void updateDueDateLabel(Chore chore) {
        if (chore.isCompleted()) {
            choreDueDate.setText("Chore Completed");
        } else {
            LocalDate dateNow = LocalDate.now();
            long daysDue = DAYS.between(dateNow, chore.getDueDate());
            choreDueDate.setText("Due in " + daysDue + " days");
        }
    }

    /**
     * Handles the delete icon click event.
     * Confirms deletion with the user and deletes the chore if confirmed.
     */
    @FXML
    private void handleDeleteIcon() {
        if (confirmDelete()) {
            try {
                choreDAO.delete(currentChore.getId());
                System.out.println("Chore deleted successfully");
                parentController.init();
            } catch (Exception e) {
                System.err.println("Failed to delete chore: " + e.getMessage());
                showAlert("Error", "Failed to delete the chore. Please try again.");
            }
        }
    }

    /**
     * Displays a confirmation dialog for chore deletion.
     * @return true if the user confirms deletion, false otherwise.
     */
    private boolean confirmDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to delete this chore?");
        alert.setContentText("This action cannot be undone.");
        return alert.showAndWait().orElse(null) == ButtonType.OK;
    }

    /**
     * Displays an alert dialog with the given title and message.
     * @param title The title of the alert dialog.
     * @param message The message to be displayed in the alert dialog.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}