package com.maddev.coozy.controller.chore;

import com.maddev.coozy.model.Chore;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class ChoreItemController {
    @FXML
    private Label choreDueDate;

    @FXML
    private FontAwesomeIconView choreIcon;

    @FXML
    private Label choreScore;

    @FXML
    private Label choreTitle;

    public void setData(Chore chore){
        choreIcon.setGlyphName(chore.getIcon());
        choreScore.setText("+ " + Integer.toString(chore.getReward()) + " C");
        choreTitle.setText(chore.getName());
        LocalDate dateNow=LocalDate.now();
        long daysDue=DAYS.between(dateNow, chore.getDueDate());
        choreDueDate.setText("Due in "+Long.toString(daysDue)+" days");
    }
}
