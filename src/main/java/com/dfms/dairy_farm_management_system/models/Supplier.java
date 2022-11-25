package com.dfms.dairy_farm_management_system.models;

import com.dfms.dairy_farm_management_system.connection.DBConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;

public class Supplier implements Model {
    private int id;
    private String nameSupplier;
    private String typeSupplier;
    private String phoneSupplier;
    private String emailSupplier;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Supplier() {
        this.created_at = Timestamp.valueOf(LocalDateTime.now());
        this.updated_at = Timestamp.valueOf(LocalDateTime.now());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameSupplier() {
        return nameSupplier;
    }

    public void setNameSupplier(String nameSupplier) {
        this.nameSupplier = nameSupplier;
    }

    public String getTypeSupplier() {
        return typeSupplier;
    }

    public void setTypeSupplier(String typeSupplier) {
        this.typeSupplier = typeSupplier;
    }

    public String getPhoneSupplier() {
        return phoneSupplier;
    }

    public void setPhoneSupplier(String phoneSupplier) {
        this.phoneSupplier = phoneSupplier;
    }

    public String getEmailSupplier() {
        return emailSupplier;
    }

    public void setEmailSupplier(String emailSupplier) {
        this.emailSupplier = emailSupplier;
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
        String query = "INSERT INTO `suppliers` (name, type, phone, email, created_at, updated_at) VALUES(?, ?, ?, ?, ?, ?)";
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, nameSupplier);
            statement.setString(2, typeSupplier);
            statement.setString(3, phoneSupplier);
            statement.setString(4, emailSupplier);
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
        updated_at = Timestamp.valueOf(LocalDateTime.now());
        String query = "UPDATE `clients` SET " +
                "`name` =?,"+
                "`type` = ?," +
                "`phone` = ?," +
                "`email` = ?," +
                "`updated_at` =?" +
                "WHERE `id` = " + id;
        Connection connection = DBConfig.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nameSupplier);
            statement.setString(2, typeSupplier);
            statement.setString(3, phoneSupplier);
            statement.setString(4, emailSupplier);
            statement.setTimestamp(5, updated_at);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean delete() {
        String query = "DELETE FROM `suppliers` WHERE `id` = " + id;
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