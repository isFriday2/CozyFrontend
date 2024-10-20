package com.maddev.coozy.controller.chore;

import com.maddev.coozy.model.Chore;
import com.maddev.coozy.model.ChoreDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

/**
 * Controller class for managing individual chore item displays.
 * Handles the display, editing, and interaction of chore items in the UI.
 */
public class ChoreAnchorController {
    private ChoreDAO choreDAO;
    private boolean testing = false;
    private boolean edit = false;
    private Chore chore;
    private ChoreViewController parentController;

    @FXML
    AnchorPane choreAnchor;

    /**
     * Constructs a new ChoreAnchorController.
     * Initializes the ChoreDAO for database operations.
     */
    public ChoreAnchorController() {
        choreDAO = new ChoreDAO();
    }

    /**
     * Sets the controller to testing mode.
     * In testing mode, it uses a test database for ChoreDAO.
     */
    public void setTesting() {
        testing = true;
        choreDAO = new ChoreDAO(true);
    }

    /**
     * Sets the chore and parent controller for this anchor controller.
     * @param chore The Chore object to be displayed and managed.
     * @param parentController The parent ChoreViewController.
     */
    public void setChore(Chore chore, ChoreViewController parentController) {
        this.chore = chore;
        this.parentController = parentController;
    }

    /**
     * Sets up the data for the chore item display.
     * Loads the appropriate FXML file based on the chore's state and edit mode.
     */
    public void setData() {
        choreAnchor.getChildren().clear();
        if (!edit) {
            loadChoreItemView();
        } else {
            loadChoreEditView();
        }
        setupMouseClickHandlers();
    }

    /**
     * Loads the appropriate chore item view based on completion status.
     */
    private void loadChoreItemView() {
        String fxmlFile = chore.isCompleted() ? "/com/maddev/coozy/chore-item-completed.fxml" : "/com/maddev/coozy/chore-item.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        try {
            AnchorPane item = fxmlLoader.load();
            ChoreItemController controller = fxmlLoader.getController();
            controller.setData(chore);
            choreAnchor.getChildren().add(item);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the chore edit view.
     */
    private void loadChoreEditView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/maddev/coozy/chore-edit-item.fxml"));
        try {
            AnchorPane item = fxmlLoader.load();
            ChoreEditItemController controller = fxmlLoader.getController();
            if (testing) controller.setTesting();
            controller.setData(chore, parentController);
            choreAnchor.getChildren().add(item);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets up mouse click event handlers for the chore item.
     */
    private void setupMouseClickHandlers() {
        choreAnchor.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                onLeftClick();
            } else if (event.getButton() == MouseButton.SECONDARY) {
                onRightClick();
            }
        });
    }

    /**
     * Handles right-click events on the chore item.
     * Toggles edit mode and refreshes the display.
     */
    private void onRightClick() {
        edit = !edit;
        setData();
    }

    /**
     * Handles left-click events on the chore item.
     * Toggles the completion status of the chore and updates the database.
     */
    private void onLeftClick() {
        chore.setCompleted(!chore.isCompleted());
        choreDAO.update(chore);
        parentController.init();
    }
}