package com.dfms.dairy_farm_management_system.models;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;

public class Animal implements Model {
    private String id;
    private Date birth_date;
    private Date purchase_date;
    private int routine;
    private int race;
    private String type;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Animal() {
        created_at = Timestamp.valueOf(LocalDateTime.now());
        updated_at = Timestamp.valueOf(LocalDateTime.now());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public Date getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(Date purchase_date) {
        this.purchase_date = purchase_date;
    }

    public int getRoutine() {
        return routine;
    }

    public void setRoutine(int routine) {
        this.routine = routine;
    }

    public int getRace() {
        return race;
    }

    public void setRace(int race) {
        this.race = race;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        String query = "INSERT INTO `animals` (`id`, `birth_date`, `purchase_date`, `routine`, `race`, `type`, `created_at`, `updated_at`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, id);
            statement.setDate(2, birth_date);
            statement.setDate(3, purchase_date);
            statement.setInt(4, routine);
            statement.setInt(5, race);
            statement.setString(6, type);
            statement.setTimestamp(7, created_at);
            statement.setTimestamp(8, updated_at);

            return statement.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update() {
        updated_at = Timestamp.valueOf(LocalDateTime.now());
        String query = "UPDATE `animals` SET " +
                "`birth_date` = '" + birth_date +"', " +
                "`purchase_date` = '" + purchase_date + "', " +
                "`routine` = '" + routine + "', " +
                "`race` = '" + race + "', " +
                "`type` = '" + type + "', " +
                "`updated_at` = '" + updated_at + "' " +
                "WHERE `animals`.`id` = '" + id + "'";
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
        String query = "DELETE FROM `animals` WHERE `animals`.`id` = '" + id + "'";
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
