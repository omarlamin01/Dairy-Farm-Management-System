package com.dfms.dairy_farm_management_system.models;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Role implements Model {

    private int id;
    private String name;

    public Role() {}

    public Role(int id, String role_name) {
        this.id = id;
        this.name = role_name;
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

    @Override
    public boolean save() {
        String insertQuery = "INSERT INTO roles (name) VALUES (?)";
        LocalDateTime now = LocalDateTime.now();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, this.name);
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
