package com.maddev.coozy.model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for Chore entities.
 * This class handles all database operations related to Chores,
 * including CRUD operations and various retrieval methods.
 */
public class ChoreDAO {
    private Connection connection;

    /**
     * Constructs a ChoreDAO and establishes a database connection.
     * It also ensures that the chores table exists in the database.
     */
    public ChoreDAO() {
        connection = DatabaseConnection.getInstance();
        createTable();
    }

    /**
     * Constructs a ChoreDAO with an option for testing purposes.
     * @param test If true, uses a test database connection; otherwise, uses the standard connection.
     */
    public ChoreDAO(boolean test){
        if(test){
            connection = TestDatabaseConnection.getInstance();
        } else {
            connection = DatabaseConnection.getInstance();
        }
        createTable();
    }

    /**
     * Creates the chores table in the database if it doesn't already exist.
     */
    public void createTable() {
        try {
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS chores ("
                            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "user_id INTEGER, "
                            + "name VARCHAR NOT NULL, "
                            + "description VARCHAR, "
                            + "reward INTEGER NOT NULL,"
                            + "home VARCHAR NOT NULL,"
                            + "icon VARCHAR NOT NULL,"
                            + "due_date VARCHAR,"
                            + "completed INT"
                            + ")"
            );
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    /**
     * Resets the chores table by deleting all entries.
     * This method should only be used with the test database.
     */
    public void resetTable(){
        try {
            Statement createTable = connection.createStatement();
            createTable.execute("DELETE FROM chores");
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    /**
     * Inserts a new chore into the database.
     * @param chore The Chore object to be inserted.
     */
    public void insert(Chore chore) {
        try {
            PreparedStatement insertChore = connection.prepareStatement(
                    "INSERT INTO chores (user_id, name, description, reward, home, icon, due_date, completed) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            );
            insertChore.setInt(1, chore.getUserId());
            insertChore.setString(2, chore.getName());
            insertChore.setString(3, chore.getDescription());
            insertChore.setInt(4, chore.getReward());
            insertChore.setString(5, chore.getHome());
            insertChore.setString(6, chore.getIcon());
            insertChore.setString(7, chore.getDueDate().toString());
            insertChore.setInt(8, chore.isCompleted() ? 1 : 0);
            insertChore.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    /**
     * Updates an existing chore in the database.
     * @param chore The Chore object with updated information.
     */
    public void update(Chore chore) {
        try {
            PreparedStatement updateChore = connection.prepareStatement(
                    "UPDATE chores SET user_id = ?, name = ?, description = ?, reward = ?, home = ?, icon = ?, due_date = ?, completed = ? WHERE id = ?"
            );
            updateChore.setInt(1, chore.getUserId());
            updateChore.setString(2, chore.getName());
            updateChore.setString(3, chore.getDescription());
            updateChore.setInt(4, chore.getReward());
            updateChore.setString(5, chore.getHome());
            updateChore.setString(6, chore.getIcon());
            updateChore.setString(7, chore.getDueDate().toString());
            updateChore.setInt(8, chore.isCompleted() ? 1 : 0);
            updateChore.setInt(9, chore.getId());
            updateChore.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    /**
     * Deletes a chore from the database.
     * @param id The ID of the chore to be deleted.
     */
    public void delete(int id) {
        try {
            PreparedStatement deleteChore = connection.prepareStatement("DELETE FROM chores WHERE id = ?");
            deleteChore.setInt(1, id);
            deleteChore.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    /**
     * Retrieves a chore from the database by its ID.
     * @param id The ID of the chore to retrieve.
     * @return The Chore object if found, null otherwise.
     */
    public Chore getById(int id) {
        try {
            PreparedStatement getChore = connection.prepareStatement("SELECT * FROM chores WHERE id = ?");
            getChore.setInt(1, id);
            ResultSet rs = getChore.executeQuery();
            if (rs.next()) {
                return new Chore(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("reward"),
                        rs.getString("home"),
                        rs.getString("icon"),
                        LocalDate.parse(rs.getString("due_date")),
                        rs.getInt("completed")==1
                );
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }

    /**
     * Retrieves all chores belonging to a specific user.
     * @param id The ID of the user.
     * @return A List of Chore objects belonging to the specified user.
     */
    public List<Chore> getAllByUser(int id) {
        List<Chore> chores= new ArrayList<>();
        try {
            PreparedStatement getChores = connection.prepareStatement("SELECT * FROM chores WHERE user_id = ?");
            getChores.setInt(1, id);
            ResultSet rs = getChores.executeQuery();
            while (rs.next()) {
                chores.add(
                        new Chore(
                                rs.getInt("id"),
                                rs.getInt("user_id"),
                                rs.getString("name"),
                                rs.getString("description"),
                                rs.getInt("reward"),
                                rs.getString("home"),
                                rs.getString("icon"),
                                LocalDate.parse(rs.getString("due_date")),
                                rs.getInt("completed")==1
                        )
                );
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return chores;
    }

    /**
     * Retrieves all pending (uncompleted) chores for a specific user.
     * @param id The ID of the user.
     * @return A List of pending Chore objects for the specified user.
     */
    public List<Chore> getAllPendingByUser(int id) {
        List<Chore> chores= new ArrayList<>();
        try {
            PreparedStatement getChores = connection.prepareStatement("SELECT * FROM chores WHERE user_id = ? AND completed = 0");
            getChores.setInt(1, id);
            ResultSet rs = getChores.executeQuery();
            while (rs.next()) {
                chores.add(
                        new Chore(
                                rs.getInt("id"),
                                rs.getInt("user_id"),
                                rs.getString("name"),
                                rs.getString("description"),
                                rs.getInt("reward"),
                                rs.getString("home"),
                                rs.getString("icon"),
                                LocalDate.parse(rs.getString("due_date")),
                                rs.getInt("completed")==1
                        )
                );
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return chores;
    }

    /**
     * Retrieves all completed chores for a specific user.
     * @param id The ID of the user.
     * @return A List of completed Chore objects for the specified user.
     */
    public List<Chore> getAllCompletedByUser(int id) {
        List<Chore> chores= new ArrayList<>();
        try {
            PreparedStatement getChores = connection.prepareStatement("SELECT * FROM chores WHERE user_id = ? AND completed = 1");
            getChores.setInt(1, id);
            ResultSet rs = getChores.executeQuery();
            while (rs.next()) {
                chores.add(
                        new Chore(
                                rs.getInt("id"),
                                rs.getInt("user_id"),
                                rs.getString("name"),
                                rs.getString("description"),
                                rs.getInt("reward"),
                                rs.getString("home"),
                                rs.getString("icon"),
                                LocalDate.parse(rs.getString("due_date")),
                                rs.getInt("completed")==1
                        )
                );
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return chores;
    }
}