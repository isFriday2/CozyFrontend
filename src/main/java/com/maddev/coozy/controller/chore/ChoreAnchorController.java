package com.maddev.coozy.controller.chore;

import com.maddev.coozy.model.Chore;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class ChoreAnchorController {

    private boolean edit=false;
    private Chore chore;


    public void setChore(Chore chore){
        this.chore=chore;
    }

    @FXML
    AnchorPane choreAnchor;

    public void setData(){
        choreAnchor.getChildren().clear();
        if(!edit){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/maddev/coozy/chore-item.fxml"));
            try{
                AnchorPane item=fxmlLoader.load();
                ChoreItemController controller = fxmlLoader.getController();
                controller.setData(chore);
                choreAnchor.getChildren().add(item);
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/maddev/coozy/chore-edit-item.fxml"));
            try{
                AnchorPane item=fxmlLoader.load();
                ChoreEditItemController controller = fxmlLoader.getController();
                controller.setData(chore);
                choreAnchor.getChildren().add(item);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onMouseDragged(){
        edit=!edit;
        setData();
    }
}
