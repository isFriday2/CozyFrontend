package com.maddev.coozy.controller;

import com.maddev.coozy.model.Chore;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.DAYS;

public class ChoreItemController implements Initializable {
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
        System.out.println(dateNow.toString());
        System.out.println(chore.getDueDate().toString());
        long daysDue=DAYS.between(dateNow, chore.getDueDate());
        choreDueDate.setText("Due in "+Long.toString(daysDue)+" days");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
