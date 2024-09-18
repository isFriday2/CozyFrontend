package com.maddev.coozy.controller.resident;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HouseController {
    @FXML
    private TextField houseNameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField joinHouseNameField;

    @FXML
    private Label statusLabel;

    private String createdHouseName;

    @FXML
    private void createHouse() {
        String houseName = houseNameField.getText();
        String address = addressField.getText();
        if (houseName.isEmpty() || address.isEmpty()) {
            statusLabel.setText("Please enter both house name and address.");
            return;
        }

        createdHouseName = houseName;
        statusLabel.setText("House '" + houseName + "' created at " + address);
    }

    @FXML
    private void joinHouse() {
        String houseToJoin = joinHouseNameField.getText();
        if (houseToJoin.isEmpty()) {
            statusLabel.setText("Please enter the house name to join.");
            return;
        }

        if (createdHouseName != null && createdHouseName.equals(houseToJoin)) {
            statusLabel.setText("Successfully joined house: " + houseToJoin);
        } else {
            statusLabel.setText("House not found: " + houseToJoin);
        }
    }
}
