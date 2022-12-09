package com.dfms.dairy_farm_management_system.models;

import com.dfms.dairy_farm_management_system.connection.DBConfig;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.disconnect;
import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;

public class HealthStatus implements Model {
    private int id;
    private String animal_id;
    private int weight;
    private int breathing;
    private String health_score;
    private Date control_date;
    private String notes;
    private Timestamp created_at;
    private Timestamp updated_at;

    public HealthStatus() {
        created_at = Timestamp.valueOf(LocalDateTime.now());
        updated_at = Timestamp.valueOf(LocalDateTime.now());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnimal_id() {
        return animal_id;
    }

    public void setAnimal_id(String animal_id) {
        this.animal_id = animal_id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getBreathing() {
        return breathing;
    }

    public void setBreathing(int breathing) {
        this.breathing = breathing;
    }

    public String getHealth_score() {
        return health_score;
    }

    public void setHealth_score(String health_score) {
        this.health_score = health_score;
    }

    public Date getControl_date() {
        return control_date;
    }

    public void setControl_date(Date control_date) {
        this.control_date = control_date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
        String query = "INSERT INTO `health_status` (animal_id, weight, breathing, health_score, control_date, notes, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, animal_id);
            statement.setInt(2, weight);
            statement.setInt(3, breathing);
            statement.setString(4, health_score);
            statement.setDate(5, control_date);
            statement.setString(6, notes);
            statement.setTimestamp(7, created_at);
            statement.setTimestamp(8, updated_at);

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
        String query = "UPDATE `health_status` SET " +
                "animal_id = '" + animal_id + "', " +
                "weight = '" + weight + "', " +
                "breathing = '" + breathing + "', " +
                "health_score = '" + health_score + "', " +
                "control_date = '" + control_date + "', " +
                "notes = '" + notes + "', " +
                "updated_at = '" + updated_at +
                "' WHERE id = " + id;
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

    @Override
    public boolean delete() {
        String query = "DELETE FROM `health_status` WHERE id = " + id;
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
