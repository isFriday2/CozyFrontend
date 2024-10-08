package com.maddev.coozy.controller.chore;

import com.maddev.coozy.model.Chore;
import com.maddev.coozy.model.ChoreDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class ChoreAnchorController {
    private ChoreDAO choreDAO;
    private boolean testing=false;
    private boolean edit=false;
    private Chore chore;

    private ChoreViewController parentController;

    public ChoreAnchorController(){
        choreDAO= new ChoreDAO();
    }

    // run before init to use the test db
    public void setTesting(){
        testing=true;
        choreDAO=new ChoreDAO(true);
    }

    public void setChore(Chore chore, ChoreViewController parentController){
        this.chore=chore;
        this.parentController=parentController;
    }

    @FXML
    AnchorPane choreAnchor;

    public void setData(){
        choreAnchor.getChildren().clear();
        if(!edit){
            if(chore.isCompleted()){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/maddev/coozy/chore-item-completed.fxml"));
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
                fxmlLoader.setLocation(getClass().getResource("/com/maddev/coozy/chore-item.fxml"));
                try{
                    AnchorPane item=fxmlLoader.load();
                    ChoreItemController controller = fxmlLoader.getController();
                    controller.setData(chore);
                    choreAnchor.getChildren().add(item);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }else{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/maddev/coozy/chore-edit-item.fxml"));
            try{
                AnchorPane item=fxmlLoader.load();
                ChoreEditItemController controller = fxmlLoader.getController();
                if(testing) controller.setTesting();
                controller.setData(chore, parentController);
                choreAnchor.getChildren().add(item);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        choreAnchor.setOnMouseClicked(event ->
        {
            if (event.getButton() == MouseButton.PRIMARY) {
                onLeftClick();
            } else if (event.getButton() == MouseButton.SECONDARY) {
                onRightClick();
            }
        });
    }

    private void onRightClick(){
        edit=!edit;
        setData();
    }

    private void onLeftClick(){
        chore.setCompleted(!chore.isCompleted());
        choreDAO.update(chore);
        setData();
    }
}
