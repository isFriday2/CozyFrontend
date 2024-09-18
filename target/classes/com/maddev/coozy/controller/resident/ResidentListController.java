//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.maddev.coozy.controller.resident;

import com.maddev.coozy.model.Leaderboard;
import com.maddev.coozy.model.LeaderboardEntry;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class ResidentListController {
    @FXML
    private TableView<Resident> residentTable;
    @FXML
    private TableColumn<Resident, Integer> idColumn;
    @FXML
    private TableColumn<Resident, String> nameColumn;
    @FXML
    private TextField nameField;
    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    private ObservableList<Resident> residentList = FXCollections.observableArrayList();
    private Connection connection;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        connectToDatabase();
        loadResidentsFromDatabase();

        addButton.setOnAction(e -> addResident());
        editButton.setOnAction(e -> editResident());
        deleteButton.setOnAction(e -> deleteResident());
    }

    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:residents.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadResidentsFromDatabase() {
        residentList.clear();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM residents");
            while (rs.next()) {
                residentList.add(new Resident(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        residentTable.setItems(residentList);
    }

    private void addResident() {
        String name = nameField.getText();

        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO residents (name) VALUES (?)");
            stmt.setString(1, name);
            stmt.executeUpdate();
            loadResidentsFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editResident() {
        Resident selectedResident = residentTable.getSelectionModel().getSelectedItem();
        if (selectedResident == null) return;

        String name = nameField.getText();
        try {
            PreparedStatement stmt = connection.prepareStatement("UPDATE residents SET name = ? WHERE id = ?");
            stmt.setString(1, name);
            stmt.setInt(4, selectedResident.getId());
            stmt.executeUpdate();
            loadResidentsFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteResident() {
        Resident selectedResident = residentTable.getSelectionModel().getSelectedItem();
        if (selectedResident == null) return;

        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM residents WHERE id = ?");
            stmt.setInt(1, selectedResident.getId());
            stmt.executeUpdate();
            loadResidentsFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
