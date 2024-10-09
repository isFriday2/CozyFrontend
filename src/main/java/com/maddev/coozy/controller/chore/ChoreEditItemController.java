package com.maddev.coozy.controller.chore;

import com.maddev.coozy.AlertUtil;
import com.maddev.coozy.model.Chore;
import com.maddev.coozy.model.ChoreDAO;
import com.maddev.coozy.controller.chore.ChoreViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class ChoreEditItemController {


    // Create DB DAO connections
    private ChoreDAO choreDAO = new ChoreDAO();

    private Chore currentChore;

    private ChoreViewController parentController;

    private boolean testing=false;

    // run before init to use the test db
    public void setTesting(){
        testing=true;
        choreDAO=new ChoreDAO(true);
    }

    @FXML
    private Label choreDueDate;

    @FXML
    private Label choreTitle;

    public void setData(Chore chore, ChoreViewController parentController){
        this.parentController=parentController;
        currentChore=chore;
        choreTitle.setText(chore.getName());
//        setChore(chore);
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
                // Update table
                parentController.init();

            } catch (Exception e) {
                System.err.println("Failed to delete chore: " + e.getMessage());
                // Show an alert if the delete fails
                AlertUtil.showAlert(Alert.AlertType.ERROR, "Failed to Delete Chore", "Failed to delete chore. Please try again later.");

            }
        }
    }

    @FXML
    private void handleInfoIcon() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/maddev/coozy/update-chore.fxml"));
            Parent root = loader.load();

            UpdateChoreController updateChoreController = loader.getController();
            updateChoreController.setChore(currentChore);

            Stage updateStage = new Stage();
            updateStage.setTitle("Update Chore");
            updateStage.setScene(new Scene(root, 600, 400)); // Set the size of the window
            updateStage.show();

        } catch (IOException e) {
            e.printStackTrace(); // This will print the stack trace to help diagnose the problem
            AlertUtil.showAlert(Alert.AlertType.ERROR, "Load Error", "Could not load update form at this time.");

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
}
