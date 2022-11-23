package com.dfms.dairy_farm_management_system.models;

import java.sql.*;
import java.time.LocalDateTime;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.disconnect;
import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;
import static com.dfms.dairy_farm_management_system.connection.Session.getCurrentUser;

public class Vaccination implements Model {
    private int id;
    private String animal_id;
    private int responsible_id;
    private String responsible_name;
    private int vaccine_id;
    private String vaccine_name;
    private Date vaccination_date;
    private Timestamp updated_at;
    private Timestamp created_at;

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

    public int getResponsible_id() {
        return responsible_id;
    }

    public void setResponsible_id(int responsible_id) {
        this.responsible_id = responsible_id;
    }

    public String getResponsible_name() {
        String full_name = null;
        String query = "SELECT first_name, last_name FROM users WHERE id = " + getResponsible_id();
        Connection connection = getConnection();
        try {
            Statement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                full_name = resultSet.getString(1) + " " + resultSet.getString(2);
                setResponsible_name(full_name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return responsible_name;
    }

    public void setResponsible_name(String responsible_name) {
        this.responsible_name = responsible_name;
    }

    public int getVaccine_id() {
        return vaccine_id;
    }

    public void setVaccine_id(int vaccine_id) {
        this.vaccine_id = vaccine_id;
    }

    public String getVaccine_name() {
        String name = null;
        String query = "SELECT name FROM stocks WHERE id = " + getVaccine_id();
        Connection connection = getConnection();
        try {
            Statement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                name = resultSet.getString(1);
                setVaccine_name(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return vaccine_name;
    }

    public void setVaccine_name(String vaccine_name) {
        this.vaccine_name = vaccine_name;
    }

    public Date getVaccination_date() {
        return vaccination_date;
    }

    public void setVaccination_date(Date vaccination_date) {
        this.vaccination_date = vaccination_date;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    @Override
    public boolean save() {
        updated_at = Timestamp.valueOf(LocalDateTime.now());
        created_at = Timestamp.valueOf(LocalDateTime.now());
        String query = "INSERT INTO `vaccination` (animal_id, responsible_id, vaccine_id, vaccination_date, updated_at, created_at) VALUES(?, ?, ?, ?, ?, ?)";
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, animal_id);
            statement.setInt(2, responsible_id);
            statement.setInt(3, vaccine_id);
            statement.setDate(4, vaccination_date);
            statement.setTimestamp(5, updated_at);
            statement.setTimestamp(6, created_at);

            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update() {
        updated_at = Timestamp.valueOf(LocalDateTime.now());
        String query = "UPDATE `vaccination` SET " +
                "animal_id = ?, responsible_id = ?, vaccine_id = ?, vaccination_date = ?, updated_at = ?" +
                " WHERE id = " + id;
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, animal_id);
            statement.setInt(2, responsible_id);
            statement.setInt(3, vaccine_id);
            statement.setDate(4, vaccination_date);
            statement.setTimestamp(5, updated_at);

            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete() {
        String query = "DELETE FROM `vaccination` WHERE id = " + id;
        Connection connection = getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, animal_id);
            statement.setInt(2, responsible_id);
            statement.setInt(3, vaccine_id);
            statement.setDate(4, vaccination_date);
            statement.setTimestamp(5, updated_at);

            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
