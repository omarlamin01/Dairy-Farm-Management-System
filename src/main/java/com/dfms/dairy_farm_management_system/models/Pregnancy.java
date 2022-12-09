package com.dfms.dairy_farm_management_system.models;

import com.dfms.dairy_farm_management_system.connection.DBConfig;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.disconnect;

public class Pregnancy implements Model {
    private int id;
    private String cow_id;
    private Date start_date;
    private Date delivery_date;
    private String pregnancy_status;
    private String notes;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Pregnancy() {
        created_at = Timestamp.valueOf(LocalDateTime.now());
        updated_at = Timestamp.valueOf(LocalDateTime.now());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCow_id() {
        return cow_id;
    }

    public void setCow_id(String cow_id) {
        this.cow_id = cow_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(Date delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getPregnancy_status() {
        return pregnancy_status;
    }

    public void setPregnancy_status(String pregnancy_status) {
        this.pregnancy_status = pregnancy_status;
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
        String query = "INSERT INTO `pregnancies` (`cow_id`, `start_date`, `delivery_date`, `pregnancy_status`, `notes`, `created_at`, `updated_at`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, cow_id);
            preparedStatement.setDate(2, start_date);
            preparedStatement.setDate(3, null);
            preparedStatement.setString(4, "Ongoing");
            preparedStatement.setString(5, notes);
            preparedStatement.setTimestamp(6, created_at);
            preparedStatement.setTimestamp(7, updated_at);

            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            disconnect();
        }
    }

    @Override
    public boolean update() {
        this.updated_at = Timestamp.valueOf(LocalDateTime.now());
        String query = "UPDATE `pregnancies` SET " +
                "`cow_id` = '" + cow_id + "'," +
                " `start_date` = " + start_date + "," +
                " `delivery_date` = " + delivery_date + "," +
                " `pregnancy_status` = '" + pregnancy_status + "'," +
                " `notes` = '" + notes + "'," +
                " `updated_at` = " + updated_at +
                " WHERE id = " + id;
        try {
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
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
        this.updated_at = Timestamp.valueOf(LocalDateTime.now());
        String query = "DELETE FROM `pregnancies` WHERE id = " + id;
        try {
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return false;
    }
}
