package com.dfms.dairy_farm_management_system.models;

import com.dfms.dairy_farm_management_system.connection.DBConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Date;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.disconnect;
import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;

public class MilkCollection implements Model {
    private int id;
    private String cow_id;
    private float quantity;
    private String period;
    private Timestamp created_at;
    private Timestamp updated_at;

    public MilkCollection() {
        this.created_at = Timestamp.valueOf(LocalDateTime.now());
        this.updated_at = Timestamp.valueOf(LocalDateTime.now());

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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setCow_id(String id_cow) {
        this.cow_id = id_cow;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }


    public int getId() {
        return id;
    }

    public String getCow_id() {
        return cow_id;
    }

    public float getQuantity() {
        return quantity;
    }


    @Override
    public boolean save() {
        String insertQuery = "INSERT INTO milk_collections (period,quantity,cow_id,created_at, updated_at) VALUES (?,?,?,?,?)";
        try {
            Connection connection = DBConfig.getConnection();
            try(PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, period);
                preparedStatement.setFloat(2, quantity);
                preparedStatement.setString(3, cow_id);
                preparedStatement.setTimestamp(4, created_at);
                preparedStatement.setTimestamp(5, updated_at);
                return preparedStatement.executeUpdate() != 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            disconnect();
        }
    }

    @Override
    public boolean update() {


        String updateQuery = "UPDATE `milk_collections` SET " +
                "`cow_id` = ?," +
                "`period` =?," +
                "`quantity` = ?," +
                "`updated_at` = ? WHERE `milk_collections`.`id` = " + this.id;
        try {
            Connection connection = DBConfig.getConnection();
            try(PreparedStatement statement= connection.prepareStatement(updateQuery)) {
                statement.setString(1, cow_id);
                statement.setString(2, period);
                statement.setFloat(3, quantity);
                statement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                return statement.executeUpdate() != 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            disconnect();
        }
    }

    @Override
    public boolean delete() {
        String deleteQuery = "DELETE FROM `milk_collections` WHERE `milk_collections`.`id` = " + this.id;
        try {
            Connection connection = DBConfig.getConnection();
            try(PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                return preparedStatement.executeUpdate() != 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            disconnect();
        }
    }

}
