package com.maddev.coozy.controller.chore;

import com.maddev.coozy.model.Chore;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class ChoreViewController implements Initializable {
    @FXML
    private VBox choresLayout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Chore> chores = new ArrayList<>(mockChores());
        for(Chore chore: chores){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/maddev/coozy/chore-anchor.fxml"));
            try{
                AnchorPane anchorPane=fxmlLoader.load();
                ChoreAnchorController controller = fxmlLoader.getController();
                controller.setChore(chore);
                controller.setData();
                choresLayout.getChildren().add(anchorPane);

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private List<Chore> mockChores(){
        List<Chore> ls = new ArrayList<>();
        LocalDate date = LocalDate.of(2024, 9, 20);
        Chore chore = new Chore(1, 1, "Clean bathroom", "Clean both bathrooms, use jabon rey", 30, "1", "BOOK",date);
        Chore chore2 = new Chore(1, 1, "Hola estoy ensayando que pasa si el titulo es muy largo", "Clean both bathrooms, use jabon rey", 30, "1", "PLUS",date);
        Chore chore3 = new Chore(1, 1, "Clean bathroom", "Clean both bathrooms, use jabon rey", 30, "1", "SHOWER",date);
        ls.add(chore);
        ls.add(chore2);
        ls.add(chore3);
        return ls;
    }
}
