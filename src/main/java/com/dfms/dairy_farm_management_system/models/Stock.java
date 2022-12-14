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

public class Stock implements Model {
    private int id;
    private String name;
    private String type;
    private float quantity;
    private boolean availability;
    private String unit;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Stock() {
    }

    public Stock(int id, String name, String type, float quantity, boolean availability, String unit, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.availability = availability;
        this.unit = unit;
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

    public String getAvailability() {
        return availability ? "Available" : "Not Available";
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    @Override
    public boolean save() {
        String insertQuery = "INSERT INTO `stocks` (name, type, quantity, availability, unit) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, type);
            preparedStatement.setFloat(3, quantity);
            preparedStatement.setInt(4, availability ? 1 : 0);
            preparedStatement.setString(5, unit);
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return false;
    }

    @Override
    public boolean update() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        String updateQuery = "UPDATE `stocks` SET `name` = '" + name +
                "', `type` = '" + type +
                "', `quantity` = '" + quantity +
                "', `availability` = '" + (availability ? 1 : 0) +
                "', `unit` = '" + unit +
                "', `updated_at` = '" + dtf.format(now) +
                "' WHERE `stocks`.`id` = " + id;
        try {
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            return preparedStatement.executeUpdate() != 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return false;
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
        } finally {
            disconnect();
        }
        return false;
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
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
