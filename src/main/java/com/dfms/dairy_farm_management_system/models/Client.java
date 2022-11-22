package com.dfms.dairy_farm_management_system.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;

public class Client implements Model {
    private int id;
    private String name;
    private String type;
    private String phone;
    private String email;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Client() {
        this.created_at = Timestamp.valueOf(LocalDateTime.now());
        this.updated_at = Timestamp.valueOf(LocalDateTime.now());
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean save() {
        String query = "INSERT INTO `clients` (name, type, phone, email, created_at, updated_at) VALUES(?, ?, ?, ?, ?, ?)";
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, name);
            statement.setString(2, type);
            statement.setString(3, phone);
            statement.setString(4, email);
            statement.setTimestamp(5, created_at);
            statement.setTimestamp(6, updated_at);

            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update() {
        String query = "UPDATE `clients` SET " +
                "`name` = '" + name + "', " +
                "`type` = '" + type + "', " +
                "`phone` = '" + phone + "', " +
                "`email` = '" + email + "', " +
                "`updated_at` = '" + Timestamp.valueOf(LocalDateTime.now()) + "' " +
                "WHERE `id` = " + id;
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete() {
        String query = "DELETE FROM `clients` WHERE `id` = " + id;
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
