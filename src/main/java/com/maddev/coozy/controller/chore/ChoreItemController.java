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
        choreScore.setText("+ " + chore.getReward() + " C");
        choreTitle.setText(chore.getName());
        if(chore.isCompleted()){
            choreDueDate.setText("Chore Completed");
        }else{
            LocalDate dateNow=LocalDate.now();
            long daysDue=DAYS.between(dateNow, chore.getDueDate());
            if(daysDue==0){
                choreDueDate.setText("Due today");
            }else if(daysDue < 0){
                choreDueDate.setText("Over due");
            }else{
                choreDueDate.setText("Due in "+daysDue+" days");
            }
        }
    }
}
