package com.maddev.coozy.model;

import java.sql.*;
import java.time.LocalDate;

public class ChoreDAO {
    private Connection connection;

    // Create DB connection instance
    public ChoreDAO() {
        connection = DatabaseConnection.getInstance();
        createTable();
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
                            + "due_date VARCHAR"
//                            + "FOREIGN KEY(userId) REFERENCES Users(id)"
                            + ")"
            );
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    // Insert new chore (with no missing info) into db
    public void insert(Chore chore) {
        try {
            PreparedStatement insertChore = connection.prepareStatement(
                    "INSERT INTO chores (user_id, name, description, reward, home, icon, due_date) VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            insertChore.setInt(1, chore.getUserId());
            insertChore.setString(2, chore.getName());
            insertChore.setString(3, chore.getDescription());
            insertChore.setInt(4, chore.getReward());
            insertChore.setString(5, chore.getHome());
            insertChore.setString(6, chore.getIcon());
            insertChore.setString(7, chore.getDueDate().toString());
            insertChore.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    // Update details of existing chore in db
    public void update(Chore chore) {
        try {
            PreparedStatement updateChore = connection.prepareStatement(
                    "UPDATE chores SET user_id = ?, name = ?, description = ?, reward = ?, home = ?, icon = ?, due_date = ? WHERE id = ?\""
            );
            updateChore.setInt(1, chore.getUserId());
            updateChore.setString(2, chore.getName());
            updateChore.setString(3, chore.getDescription());
            updateChore.setInt(4, chore.getReward());
            updateChore.setString(5, chore.getHome());
            updateChore.setString(6, chore.getIcon());
            updateChore.setString(7, chore.getDueDate().toString());
            updateChore.setInt(8, chore.getId());
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
                        LocalDate.parse(rs.getString("due_date"))
                );
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }
}
