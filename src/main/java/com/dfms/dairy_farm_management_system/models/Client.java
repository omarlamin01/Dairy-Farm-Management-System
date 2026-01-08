package com.dfms.dairy_farm_management_system.models;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.disconnect;
import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;

import com.dfms.dairy_farm_management_system.connection.DBConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Client implements Model {

    private int id;
    private String nameClient;
    private String typeClient;
    private String phoneClient;
    private String emailClient;
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
        return nameClient;
    }

    public void setName(String name) {
        this.nameClient = name;
    }

    public String getType() {
        return typeClient;
    }

    public void setType(String type) {
        this.typeClient = type;
    }

    public String getEmail() {
        return emailClient;
    }

    public void setEmail(String email) {
        this.emailClient = email;
    }

    public String getPhone() {
        return phoneClient;
    }

    public void setPhone(String phone) {
        this.phoneClient = phone;
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

    @Override
    public boolean save() {
        String query =
            "INSERT INTO `clients` (name, type, phone, email, created_at, updated_at) VALUES(?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, nameClient);
            statement.setString(2, typeClient);
            statement.setString(3, phoneClient);
            statement.setString(4, emailClient);
            statement.setTimestamp(5, created_at);
            statement.setTimestamp(6, updated_at);

            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return false;
    }

    @Override
    public boolean update() {
        updated_at = Timestamp.valueOf(LocalDateTime.now());
        String query =
            "UPDATE `clients` SET " +
            "`name` =?," +
            "`type` = ?," +
            "`phone` = ?," +
            "`email` = ?," +
            "`updated_at` =?" +
            "WHERE `id` = " +
            id;
        try {
            Connection connection = DBConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nameClient);
            statement.setString(2, typeClient);
            statement.setString(3, phoneClient);
            statement.setString(4, emailClient);
            statement.setTimestamp(5, updated_at);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return false;
    }

    @Override
    public boolean delete() {
        String query = "DELETE FROM `clients` WHERE `id` = " + id;
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return false;
    }
}
