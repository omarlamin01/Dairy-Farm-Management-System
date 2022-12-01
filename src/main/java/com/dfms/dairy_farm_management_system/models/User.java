package com.dfms.dairy_farm_management_system.models;

import com.dfms.dairy_farm_management_system.connection.DBConfig;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.dfms.dairy_farm_management_system.connection.DBConfig.getConnection;

public class User extends Employee {
    private int id;
    private String password;
    private int role;

    public User() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public boolean save() {
        if (!super.save()) {
            return false;
        } else {
            String insertQuery = "INSERT INTO `users` " +
                    "(`first_name`, `last_name`, `cin`, `email`, `password`, `gender`, `phone`, `salary`, `address`, `role`, `created_at`, `updated_at`) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Connection connection = getConnection();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

                preparedStatement.setString(1, super.getFirstName());
                preparedStatement.setString(2, super.getLastName());
                preparedStatement.setString(3, super.getCin());
                preparedStatement.setString(4, super.getEmail());
                preparedStatement.setString(5, password);
                preparedStatement.setString(6, super.getGender().equalsIgnoreCase("Male") ? "M" : "F");
                preparedStatement.setString(7, super.getPhone());
                preparedStatement.setFloat(8, super.getSalary());
                preparedStatement.setString(9, super.getAdress());
                preparedStatement.setInt(10, role);
                preparedStatement.setTimestamp(11, super.getCreatedAt());
                preparedStatement.setTimestamp(12, super.getUpdatedAt());

                return preparedStatement.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean update() {
        String query = "UPDATE `users` SET" +
                " `first_name` = '" + super.getFirstName() +
                "', `last_name` = '" + super.getLastName() +
                "', `cin` = '" + super.getCin() +
                "', `email` = '" + super.getEmail() +
                "', `password` = '" + password +
                "', `gender` = '" + (super.getGender().equalsIgnoreCase("Male") ? 'M' : 'F') +
                "', `phone` = '" + super.getPhone() +
                "', `salary` = '" + super.getSalary() +
                "', `address` = '" + super.getAdress() +
                "', `updated_at` = '" + Timestamp.valueOf(LocalDateTime.now()) +
                "' WHERE `id` = " + this.id;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            return (preparedStatement.executeUpdate() != 0) && super.update();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete() {
        String deleteQuery = "DELETE FROM `users` WHERE `id` = " + this.id;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
