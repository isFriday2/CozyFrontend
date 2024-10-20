package com.maddev.coozy.controller.chore;

import com.maddev.coozy.model.Chore;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Controller class for displaying individual chore items.
 * Handles the presentation of chore details in the user interface.
 */
public class ChoreItemController {
    @FXML
    private Label choreDueDate;

    @FXML
    private FontAwesomeIconView choreIcon;

    @FXML
    private Label choreScore;

    @FXML
    private Label choreTitle;

    /**
     * Sets the data for the chore item display.
     * Updates the UI elements with the provided chore's details.
     *
     * @param chore The Chore object whose details are to be displayed.
     */
    public void setData(Chore chore) {
        updateChoreIcon(chore);
        updateChoreScore(chore);
        updateChoreTitle(chore);
        updateChoreDueDate(chore);
    }

    /**
     * Updates the chore icon in the UI.
     *
     * @param chore The Chore object containing the icon information.
     */
    private void updateChoreIcon(Chore chore) {
        choreIcon.setGlyphName(chore.getIcon());
    }

    /**
     * Updates the chore score display in the UI.
     *
     * @param chore The Chore object containing the reward information.
     */
    private void updateChoreScore(Chore chore) {
        choreScore.setText("+ " + chore.getReward() + " C");
    }

    /**
     * Updates the chore title in the UI.
     *
     * @param chore The Chore object containing the name information.
     */
    private void updateChoreTitle(Chore chore) {
        choreTitle.setText(chore.getName());
    }

    /**
     * Updates the chore due date display in the UI.
     * Calculates and displays the time remaining until the due date,
     * or shows completion status if the chore is completed.
     *
     * @param chore The Chore object containing the due date and completion status.
     */
    private void updateChoreDueDate(Chore chore) {
        if (chore.isCompleted()) {
            choreDueDate.setText("Chore Completed");
        } else {
            LocalDate dateNow = LocalDate.now();
            long daysDue = DAYS.between(dateNow, chore.getDueDate());
            if (daysDue == 0) {
                choreDueDate.setText("Due today");
            } else if (daysDue < 0) {
                choreDueDate.setText("Over due");
            } else {
                choreDueDate.setText("Due in " + daysDue + " days");
            }
        }
    }
}