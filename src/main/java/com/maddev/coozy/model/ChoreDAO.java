package com.maddev.coozy.model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ChoreDAO {
    private Connection connection;

    // Create DB connection instance
    public ChoreDAO() {
        connection = DatabaseConnection.getInstance();
        createTable();
    }

    // Creates DB connection for unit tests
    public ChoreDAO(boolean test){
        if(test){
            connection = TestDatabaseConnection.getInstance();
            createTable();
        }else{
            connection = DatabaseConnection.getInstance();
            createTable();
        }
    }

    // Create table in db
    // Will not create table if already exists
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
//                            + "FOREIGN KEY(userId) REFERENCES Users(id)"
                            + ")"
            );
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    // Erases all chores in the db, use it only with the test db
    public void resetTable(){
        try {
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "DELETE FROM chores"
            );
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    // Insert new chore (with no missing info) into db
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

    // Update details of existing chore in db
    public void update(Chore chore) {
        try {
            PreparedStatement updateChore = connection.prepareStatement(
                    "UPDATE chores SET user_id = ?, name = ?, description = ?, reward = ?, home = ?, icon = ?," +
                            " due_date = ?, completed = ? WHERE id = ?"
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

    // Delete existing chore in db
    public void delete(int id) {
        try {
            PreparedStatement deleteChore = connection.prepareStatement("DELETE FROM chores WHERE id = ?");
            deleteChore.setInt(1, id);
            deleteChore.execute();

        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    // db getters

    // get chore by chore id
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

    // get all the chores belonging to one user by user id
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
