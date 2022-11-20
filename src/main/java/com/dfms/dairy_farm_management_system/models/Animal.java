package com.dfms.dairy_farm_management_system.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
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
        String query = "INSERT INTO `animals` (`birth_date`, `purchase_date`, `routine`, `race`, `type`, `created_at`, `updated_at`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setDate(1, birth_date);
            statement.setDate(2, purchase_date);
            statement.setInt(3, routine);
            statement.setInt(4, race);
            statement.setString(5, type);
            statement.setTimestamp(6, created_at);
            statement.setTimestamp(7, updated_at);

            return statement.executeUpdate() != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }
}
