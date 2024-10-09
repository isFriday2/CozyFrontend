package com.maddev.coozy;

import javafx.scene.control.Alert;

public class AlertUtil {
    // Static method to create and show an alert with a specified AlertType
    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
