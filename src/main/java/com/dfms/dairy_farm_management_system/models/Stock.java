package com.dfms.dairy_farm_management_system.models;

import com.dfms.dairy_farm_management_system.connection.DBConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Stock implements Model {
    private int id;
    private String name;
    private String type;
    private float quantity;
    private Availability availability;
    private String unit;
    private Date added_date;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Stock() {
    }

    public Stock(int id, String name, String type, float quantity, Availability availability, String unit, Date added_date, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.availability = availability;
        this.unit = unit;
        this.added_date = added_date;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void setAddedDate(Date added_date) {
        this.added_date = added_date;
    }

    public Timestamp getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", quantity=" + quantity +
                ", availability=" + availability +
                ", unit='" + unit + '\'' +
                ", added_date=" + added_date +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }

    @Override
    public boolean save() {
        String insertQuery = "INSERT INTO `stocks` (name, type, quantity, availability, unit, added_date, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, type);
            preparedStatement.setFloat(3, quantity);
            preparedStatement.setString(4, availability.getAvailability());
            preparedStatement.setString(5, unit);
            preparedStatement.setDate(6, new java.sql.Date(added_date.getTime()));
            preparedStatement.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        String updateQuery = "UPDATE `stocks` SET `name` = '" + name +
                "', `type` = '" + type +
                "', `quantity` = '" + quantity +
                "', `availability` = '" + availability.getAvailability() +
                "', `unit` = '" + unit +
                "', `added_date` = '" + added_date +
                "', `updated_at` = '" + dtf.format(now) +
                "' WHERE `stocks`.`id` = " + id;
        try {
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete() {
        String deleteQuery = "DELETE FROM `stocks` WHERE `stocks`.`id` = '" + id + "'";
        try {
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
