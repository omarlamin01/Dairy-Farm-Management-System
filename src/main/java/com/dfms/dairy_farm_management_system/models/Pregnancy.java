package com.dfms.dairy_farm_management_system.models;

import com.dfms.dairy_farm_management_system.connection.DBConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class Pregnancy implements Model {
    private int id;
    private int cow_id;
    private LocalDate start_date;
    private Date end_date;
    private String type;
    private String status;
    private String notes;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCow_id(int cow_id) {
        this.cow_id = cow_id;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public int getCow_id() {
        return cow_id;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean save() {
        String query = "INSERT INTO `pregnancy` (`cow_id`, `start_date`, `delivery_date`, `pregnancy_type`, `pregnancy_status`) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, String.valueOf(this.cow_id));
            preparedStatement.setString(2, String.valueOf(this.start_date));
            preparedStatement.setDate(3, (java.sql.Date) this.end_date);
            if (this.type.equalsIgnoreCase("Natural Service")) {
                preparedStatement.setString(4, "Natural Service");
            } else {
                preparedStatement.setString(4, "By Collecting Semen");
            }
            preparedStatement.setString(5, "pending");

            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
