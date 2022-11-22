package com.dfms.dairy_farm_management_system.models;

import com.dfms.dairy_farm_management_system.connection.DBConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class User extends Employee {
    private int id_user;
    private String password;
    private int roleId;


    public User(int id, String firstName, String lastName, String gender, String cin, String email, String password, String phone, String address, float salary, Date recruitmentDate, String contractType, Date updated_at, Date created_at) {
        super(firstName, lastName, gender, cin, email, phone, address, salary, recruitmentDate, contractType, updated_at, created_at);
        this.id_user = id;
        this.password = password;
    }

    public User() {
        super();
    }

    public int getId() {
        return this.id_user;
    }

    public void setId(int id) {
        this.id_user = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean save() {
        boolean empIsSaved = super.save();
        if (!empIsSaved) {
            return false;
        }
        String insertQuery = "INSERT INTO `users` (`role_id`, `password`, `first_name`, `last_name`, `gender`, `cin`, `phone`, `salary`, `email`, `address`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setString(1, String.valueOf(roleId));
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, super.getFirstName());
            preparedStatement.setString(5, super.getLastName());

            if (super.getGender().equalsIgnoreCase("Male")) {
                preparedStatement.setString(6, "M");
            } else {
                preparedStatement.setString(6, "F");
            }
            preparedStatement.setString(7, super.getCin());
            preparedStatement.setString(8, super.getPhone());
            preparedStatement.setString(9, String.valueOf(super.getSalary()));
            preparedStatement.setString(10, super.getEmail());
            preparedStatement.setString(11, super.getAdress());

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

        String updateQuery = "UPDATE `users` SET `password` = '" + password +
                "', `role_id` = '" + roleId +
                "', `updated_at` = '" + dtf.format(now) +
                "', `first_name` = '" + super.getFirstName() +
                "', `last_name` = '" + super.getLastName() +
                "', `gender` = '" + (super.getGender().equalsIgnoreCase("Male") ? 'M' : 'F') +
                "', `cin` = '" + super.getCin() +
                "', `phone` = '" + super.getPhone() +
                "', `salary` = '" + super.getSalary() +
                "', `email` = '" + super.getEmail() +
                "', `address` = '" + super.getAdress() +
                "' WHERE `users`.`id` = " + this.id_user;
        try {
            Connection connection = DBConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            return (preparedStatement.executeUpdate() != 0) && super.update();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete() {
        String deleteQuery = "DELETE FROM `users` WHERE `users`.`id` = " + this.id_user;
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
