package com.dfms.dairy_farm_management_system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;

public class Role implements Model {
    private int id;
    private String name;
    private Timestamp added_date;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public Role() {
    }

    public Role(int id, String role_name, Timestamp added_date) {
        this.id = id;
        this.name = role_name;
        this.added_date = added_date;
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

    public void setName(String role_name) {
        this.name = role_name;
    }

    public Timestamp getAddedDate() {
        return added_date;
    }

    public void setAddedDate(Timestamp added_date) {
        this.added_date = added_date;
    }

    @Override
    public boolean save() {
        String insertQuery = "INSERT INTO roles (name, added_date) VALUES (?, ?)";
        LocalDateTime now = LocalDateTime.now();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, this.name);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(dtf.format(now)));
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update() {
        String updateQuery = "UPDATE roles SET name = ? WHERE id = ?";
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, this.name);
            preparedStatement.setInt(2, this.id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete() {
        String deleteQuery = "DELETE FROM roles WHERE id = ?";
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, this.id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
