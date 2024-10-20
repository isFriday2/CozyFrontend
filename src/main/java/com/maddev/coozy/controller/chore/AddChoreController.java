package com.maddev.coozy.controller.chore;

import com.maddev.coozy.HelloApplication;
import com.maddev.coozy.model.Chore;
import com.maddev.coozy.model.ChoreDAO;
import com.maddev.coozy.model.User;
import com.maddev.coozy.model.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Controller class for adding new chores.
 * Handles the user interface and database operations for creating new chores.
 */
public class AddChoreController {
    private UserDAO userDAO;
    private ChoreDAO choreDAO;
    private boolean testing = false;
    private User user;
    private String icon;

    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private TextField nameTextField;
    @FXML
    private ChoiceBox<String> chooseUsername;
    @FXML
    private TextField pointsTextField;
    @FXML
    private DatePicker dueDatePicker;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;

    /**
     * Constructs a new AddChoreController.
     * Initializes the UserDAO and ChoreDAO for database operations.
     */
    public AddChoreController() {
        userDAO = new UserDAO();
        choreDAO = new ChoreDAO();
    }

    /**
     * Sets the controller to testing mode.
     * In testing mode, it uses test databases for UserDAO and ChoreDAO.
     */
    public void setTesting() {
        testing = true;
        choreDAO = new ChoreDAO(true);
        userDAO = new UserDAO(true);
    }

    /**
     * Sets the current user for the controller.
     * @param user The current User object.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Sets the chore icon to "TRASH".
     */
    @FXML
    private void setToTrash() {
        icon = "TRASH";
    }

    /**
     * Sets the chore icon to "HOME".
     */
    @FXML
    private void setToHome() {
        icon = "HOME";
    }

    /**
     * Sets the chore icon to "SHOPPING_CART".
     */
    @FXML
    private void setToShoppingCart() {
        icon = "SHOPPING_CART";
    }

    /**
     * Sets the chore icon to "PAW".
     */
    @FXML
    private void setToPaw() {
        icon = "PAW";
    }

    /**
     * Sets the chore icon to "CUTLERY".
     */
    @FXML
    private void setToCutlery() {
        icon = "CUTLERY";
    }

    /**
     * Sets the chore icon to "SHOWER".
     */
    @FXML
    private void setToShower() {
        icon = "SHOWER";
    }

    /**
     * Populates the username choice box with users from the same home.
     */
    public void setChoiceUsernames() {
        chooseUsername.setValue("Assign chore");
        List<User> possibleUsers = userDAO.getAllByHome(user.getHome());
        for (User possibleUser : possibleUsers) {
            chooseUsername.getItems().add(possibleUser.getUsername());
        }
    }

    /**
     * Handles the add chore button click.
     * Creates a new chore and navigates back to the home page.
     * @throws IOException If there's an error loading the home page.
     */
    @FXML
    private void onAddChore() throws IOException {
        final String username = chooseUsername.getValue();
        User assignedUser = userDAO.getByUsername(username);

        final int userId = assignedUser.getId();
        final String name = nameTextField.getText();
        final String description = descriptionTextArea.getText();
        final int reward = Integer.parseInt(pointsTextField.getText());
        final String home = assignedUser.getHome();
        final LocalDate dueDate = dueDatePicker.getValue();

        Chore newChore = new Chore(userId, name, description, reward, home, icon, dueDate, false);
        choreDAO.insert(newChore);

        navigateToHomePage();
    }

    /**
     * Handles the cancel button click.
     * Navigates back to the home page without adding a chore.
     * @throws IOException If there's an error loading the home page.
     */
    @FXML
    private void onCancel() throws IOException {
        navigateToHomePage();
    }

    /**
     * Navigates to the home page.
     * @throws IOException If there's an error loading the home page.
     */
    private void navigateToHomePage() throws IOException {
        Stage stage = (Stage) addButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home-page.fxml"));
        Parent root = fxmlLoader.load();
        ChoreViewController controller = fxmlLoader.getController();
        if (testing) controller.setTesting();
        controller.setUser(user);
        controller.init();
        Scene scene = new Scene(root, 1133, 744);
        stage.setScene(scene);
    }
}