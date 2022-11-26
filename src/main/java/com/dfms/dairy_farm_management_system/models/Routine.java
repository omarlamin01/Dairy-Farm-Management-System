package com.dfms.dairy_farm_management_system.models;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;

public class Routine implements Model {
    private int id;
    private String name;
    private String note;
    private Timestamp created_at;
    private Timestamp updated_at;

    public static int getLastId() {
        String query = "SELECT id FROM routines ORDER BY created_at DESC LIMIT 1";
        try {
            ResultSet resultSet = getConnection().prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public ArrayList<RoutineDetails> getDetails() {
        ArrayList<RoutineDetails> details = new ArrayList<>();
        String query = "SELECT * FROM `routine_has_feeds` WHERE `routine_id` = " + id;
        try {
            ResultSet resultSet = getConnection().prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                RoutineDetails routineDetails = new RoutineDetails();

                routineDetails.setId(resultSet.getInt("id"));
                routineDetails.setStock_id(resultSet.getInt("stock_id"));
                routineDetails.setRoutine_id(resultSet.getInt("routine_id"));
                routineDetails.setQuantity(resultSet.getFloat("quantity"));
                routineDetails.setFeeding_time(resultSet.getString("feeding_time"));
                routineDetails.setCreated_at(resultSet.getTimestamp("created_at"));
                routineDetails.setUpdated_at(resultSet.getTimestamp("updated_at"));

                details.add(routineDetails);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return details;
    }

    @Override
    public boolean save() {
        created_at = Timestamp.valueOf(LocalDateTime.now());
        updated_at = Timestamp.valueOf(LocalDateTime.now());
        String query = "INSERT INTO `routines` (name, note, created_at, updated_at) VALUES (?, ?, ?, ?)";
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, name);
            statement.setString(2, note);
            statement.setTimestamp(3, created_at);
            statement.setTimestamp(4, updated_at);

            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update() {
        updated_at = Timestamp.valueOf(LocalDateTime.now());
        String query = "UPDATE `routines` " +
                "SET name = ?, note = ?, updated_at = ?" +
                " WHERE id = " + id;
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, name);
            statement.setString(2, note);
            statement.setTimestamp(3, updated_at);

            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete() {
        String query = "DELETE FROM `routines` WHERE id = " + id;
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
